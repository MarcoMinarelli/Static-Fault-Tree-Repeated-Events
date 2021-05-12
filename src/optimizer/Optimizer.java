
package optimizer;

import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;

/**
 * Base class for optimizers. 
 * @author Minarelli
 */
public abstract class Optimizer {
    
    /**
     * Methos that computes the probability that the given basic events will fail at time t
     * @param list The list of BasicEvent
     * @param time The time
     * @return probability that the Basic Events will be failed at given time
     */
    protected double computeFailureProbability(List<BasicEvent> list, float time) {
        float ret = 1;
        for (BasicEvent be : list) {
            ret *= be.getProbabilityFault(time);
        }
        return ret;
    }
    
    /**
     * Method that adds the given BasicEvent to the list, if not present; if present, nothing will be done.
     * @param bes The list of BasicEvent
     * @param be The BasicEvent to add
     */
    protected void addBasicEvent(List<BasicEvent> bes, BasicEvent be){
        boolean isPresent = false; 
        //for(BasicEvent b:bes){
        for(int i=0; i<bes.size()&& !isPresent; i++){
            if(bes.get(i).equals(be)){
                isPresent = true;
            }            
        }
        
        if(!isPresent){
            bes.add(be);
        }
    }
    
    /**
     * Method that return, for each MCS, its maintenance cost (give as sum of mainteance cost 
     * of each BasicEvent).
     * @param mcs The lsit of MCS
     * @return An array containing, at the i-th position, the maintenance cost of the i-th 
     * MCS.
     */
    protected double[] computeMCSMaintenanceCost(List<MinimalCutSet> mcs) {
        double[] cost = new double[mcs.size()]; //cost of maintenance for every MCS
        // Computing the maintenance cost of all Minimal Cut Set
        for (int i = 0; i < mcs.size(); i++) {
            MinimalCutSet cs = mcs.get(i);
            long costCs = 0;
            for (BasicEvent be : cs.getCutSet()) {
                costCs += be.getMaintenanceCost() * 100;
            }
            cost[i] = costCs;
        }
        return cost;
    }
    
}
