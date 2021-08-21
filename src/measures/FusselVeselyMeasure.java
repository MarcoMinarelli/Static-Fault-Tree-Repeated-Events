package measures;

import java.util.ArrayList;
import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;
import utils.TopEventProbabilityCalculator;

/**
 *
 * @author Barbuzzi
 */
public final class FusselVeselyMeasure implements ImportanceMeasure{

    private static final FusselVeselyMeasure fvm = new FusselVeselyMeasure();

    private FusselVeselyMeasure() {
    }

    ;

    public static FusselVeselyMeasure getInstance() {
        return fvm;
    }

    public List<ImportanceMeasureValue<BasicEvent>> compute(List<MinimalCutSet> mcs, double time, List<BasicEvent> bes) {
        List<ImportanceMeasureValue<BasicEvent>> ret = new ArrayList<>();

        double topEvent = TopEventProbabilityCalculator.getInstance().computeProb(mcs, time);

        for (BasicEvent be : bes) {
            double num = 0;
            for (MinimalCutSet m : mcs) {
                if (m.getCutSet().contains(be)) {
                    num += computeNumerator(m, time);
                }
            }
            ret.add(new ImportanceMeasureValue<>(be, num / topEvent));
        }
        return ret;
    }

    public double compute(List<MinimalCutSet> mcs, double time, int index, List<BasicEvent> bes) {
        double topEvent = TopEventProbabilityCalculator.getInstance().computeProb(mcs, time);

        double prob = 1;
        for (BasicEvent b : mcs.get(index).getCutSet()) {
            prob *= b.getProbabilityFault(time);
        }
        
        double num = 0;
        for (MinimalCutSet m : mcs) {
                if (m.getCutSet().contains(bes.get(index))) {
                    num += computeNumerator(m, time);
                }
            }
        
        

        return num / topEvent;
    }

    private double computeNumerator(MinimalCutSet m, double time) {
        double prob = 1;

        for (BasicEvent b : m.getCutSet()) {
            prob *= b.getProbabilityFault(time);
        }
        return prob;
    }

}
