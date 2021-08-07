
package utils;

import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;

/**
 *
 * @author Barbuzzi
 */
public class TopEventProbabilityCalculator {
    private static TopEventProbabilityCalculator tepc = new TopEventProbabilityCalculator();
    
    private TopEventProbabilityCalculator(){}
    
    public static TopEventProbabilityCalculator getInstance(){
        return tepc;
    }
    
    public double computeProb(List<MinimalCutSet> mcs, double time){
        double probTot = 0; 
        
        for(MinimalCutSet m:mcs){
            double probMcs = 1;
            for(BasicEvent b:m.getCutSet()){
                probMcs *= b.getProbabilityFault(time);                
            }
            probTot += probMcs;
        }
        
      /*  if(probTot > 1){
            probTot = 1;
        }*/
        return probTot;
    }
    
}
