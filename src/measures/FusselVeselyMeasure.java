
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
        
        for(int i=0; i<ret.length; i++){
            ret[i] = compute(mcs, time, i);
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
    
    
    
    
    
}
