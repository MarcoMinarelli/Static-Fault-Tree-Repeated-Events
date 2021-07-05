
package measures;

import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;
import utils.TopEventProbabilityCalculator;

/**
 *
 * @author Barbuzzi
 */
public class FusselVeselyMeasure implements ImportanceMeasure{

    @Override
    public double[] compute(List<MinimalCutSet> mcs, double time) {
        double[] ret = new double[mcs.size()]; 
        
        double topEvent = TopEventProbabilityCalculator.getInstance().computeProb(mcs, time);
        
        for(int i=0; i<ret.length; i++){
            ret[i] = computeNumerator(mcs.get(i), time) / topEvent;
        }
        return ret;
    }

    @Override
    public double compute(List<MinimalCutSet> mcs, double time, int index) {
        double topEvent = TopEventProbabilityCalculator.getInstance().computeProb(mcs, time);
        
        double prob = 1;
        for(BasicEvent b:mcs.get(index).getCutSet()){
             prob *= b.getProbabilityFault(time);
        }
        
        return prob/topEvent;
    }
    
    private double computeNumerator(MinimalCutSet m, double time){
        double prob = 1;
        for(BasicEvent b:m.getCutSet()){
             prob *= b.getProbabilityFault(time);
        }
        return prob;
    }
    
    
    
}
