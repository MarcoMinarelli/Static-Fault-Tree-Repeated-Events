
package measures;

import cdf.ConstantDistribution;
import java.util.ArrayList;
import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;
import utils.TopEventProbabilityCalculator;

/**
 * CLass that permits to compute the Birnbaum Importance Measure
 * @author Minarelli
 */
public final class BirnbaumMeasure implements ImportanceMeasure{
    
    private static final BirnbaumMeasure bm = new BirnbaumMeasure();
    
    private BirnbaumMeasure(){};

    public static BirnbaumMeasure getInstance(){
        return bm;
    }

    public List<ImportanceMeasureValue<BasicEvent>> compute(List<MinimalCutSet> mcs, double time, List<BasicEvent> bes){
        
        List<ImportanceMeasureValue<BasicEvent>> ret = new ArrayList<>();
        for(int i = 0; i < bes.size(); i++){
            ret.add(new ImportanceMeasureValue<>(bes.get(i), compute(mcs, time, i, bes)));
        }
        return ret;
    }
    
    public double compute(List<MinimalCutSet> mcs, double time, int index, List<BasicEvent> bes){
        double ret;
        
        BasicEvent be = bes.get(index);
        
        List<MinimalCutSet> ap1 = new ArrayList<>();
        List<MinimalCutSet> ap0 = new ArrayList<>();
        MinimalCutSet appo;
                
         // 1
        for(MinimalCutSet m : mcs){
            appo = new MinimalCutSet();
            for(BasicEvent b : m.getCutSet()){
                if(b.equals(be)){
                    appo.addNode(new BasicEvent(new ConstantDistribution(1.0), be.getMaintenanceCost(), be.getDescription()));
                }else{
                    appo.addNode(b);
                }
            }
            ap1.add(appo);
        }
        
        // 0
        for(MinimalCutSet m : mcs){
            appo = new MinimalCutSet();
            for(BasicEvent b : m.getCutSet()){
                if(b.equals(be)){
                    appo.addNode(new BasicEvent(new ConstantDistribution(0.0), be.getMaintenanceCost(), be.getDescription()));
                }else{
                    appo.addNode(b);
                }
            }
            ap0.add(appo);
        }
          

        
        ret = TopEventProbabilityCalculator.getInstance().computeProbability(ap1, time)
                - TopEventProbabilityCalculator.getInstance().computeProbability(ap0, time);
        return ret;
    }
}
