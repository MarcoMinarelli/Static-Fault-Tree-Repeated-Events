package optimizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import minimalcutset.MOCUSEngine;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;
import staticfaulttree.Node;
import utils.MCSCombination;

/**
 * Class that implements an algorithm that allows to compute the minimal cost
 * (and the relative maintenance) in order to obtain a desoder reliability at a
 * given time.
 *
 * @author Minarelli
 */
public class ReliabilityOptimizer extends Optimizer {

    private static ReliabilityOptimizer opt = new ReliabilityOptimizer();

    private ReliabilityOptimizer() {
    }

    public static ReliabilityOptimizer getInstance() {
        return opt;
    }
    
    public List<BasicEvent> optimize(Node topEvent, float testTime, float startTime, float minRel){
        return optimize(MOCUSEngine.getInstance().getMinimalCutSet(topEvent), testTime, startTime, minRel);
    }

    public List<BasicEvent> optimize(List<MinimalCutSet> mcs, float testTime, float startTime, float minRel) {

        List<BasicEvent> ret = new ArrayList<>();

        List<List<MinimalCutSet>> comb = MCSCombination.getCombination(mcs);

        for (int i = 0; i < comb.size(); i++) {
            if(1-minRel < computeFailuresProbabilities(mcs, comb.get(i), startTime, testTime)){
                comb.remove(i);
            }
            i--;
        }
        
        double minVal = DoubleStream.of(computeMCSMaintenanceCost(comb.get(0))).sum();
        List<MinimalCutSet> min = comb.get(0);
        double val;
        for(int i=1; i<comb.size(); i++){
            val = DoubleStream.of(computeMCSMaintenanceCost(comb.get(i))).sum();
            if(minVal>val){
                minVal = val;
                min = comb.get(i);
            }
        }
        
        for(MinimalCutSet m:min){
            for(BasicEvent be:m.getCutSet()){
                addBasicEvent(ret, be);
            }
        }
        
        return ret;
    }

    private double computeFailuresProbabilities(List<MinimalCutSet> mcs, List<MinimalCutSet> mcsTest, float startTime, float testTime) {
        double ret = 0;

        for (MinimalCutSet m : mcsTest) {
            List<BasicEvent> mod = new ArrayList<>();
            BasicEvent ap;
            for (BasicEvent be : m.getCutSet()) {
                ap = be.copy();
                ap.setMaintenanceTime(startTime);
                mod.add(ap);
            }
            ret += computeFailureProbability(mod, testTime);
        }

        for (MinimalCutSet m : mcs) {

            BasicEvent ap;
            List<BasicEvent> mod = new ArrayList<>();
            if (!mcsTest.contains(m)) {
                for (BasicEvent be : m.getCutSet()) {
                    for (MinimalCutSet t : mcsTest) {
                        if (t.getCutSet().contains(be)) {
                            ap = be.copy();
                            ap.setMaintenanceTime(startTime);
                            mod.add(ap);
                        } else {
                            mod.add(be);
                        }
                    }
                }
            }
            ret += computeFailureProbability(mod, testTime);

        }

        return ret;
    }
}
