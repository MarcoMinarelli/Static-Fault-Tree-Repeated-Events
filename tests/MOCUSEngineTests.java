package tests;

import CDF.ExponentialDistributionCDF;
import java.util.List;
import minimalcutset.MOCUSEngine;
import minimalcutset.MinimalCutSet;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import staticfaulttree.ANDGate;
import staticfaulttree.BasicEvent;
import staticfaulttree.Gate;
import staticfaulttree.Node;
import staticfaulttree.ORGate;
import staticfaulttree.StaticFaultTreeBuilder;
/**
 *
 * @author Minarelli
 */
public class MOCUSEngineTests {
    
    static Node top;

    @BeforeClass
    public static void setUpClass() {
        top = new StaticFaultTreeBuilder(Gate.GateTypes.AND, "Top")
                .addORGate("B1")
                .addORGate("B2")
                
                .setCurrent("B1")
                .addANDGate("C1")
                .addANDGate("C2")
                .addANDGate("C3")
                .addANDGate("C4")
                
                .setCurrent("B2")
                .addANDGate("C5")
                .addANDGate("C6")
                .addANDGate("C7")
                
                .setCurrent("C1")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D1")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D2")
                
                .setCurrent("C2")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D1")
                .addORGate("E2")
                .setCurrent("E2")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D3")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D4")
                
                .setCurrent("C3")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D5")
                .addORGate("E3")
                .setCurrent("E3")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D2")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D6")
                
                .setCurrent("C4")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D1")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D7")
                
                .setCurrent("C5")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D5")
                .addORGate("E5")
                .setCurrent("E5")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D2")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D6")
                
                .setCurrent("C6")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D8")
                .addORGate("E6")
                .setCurrent("E6")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D2")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D4")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D6")
                
                .setCurrent("C7")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D8")
                .addORGate("E7")
                .setCurrent("E7")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D4")
                .addBasicEvent(new ExponentialDistributionCDF(0.5), 10, "D9")
                
                .build(); 
    }
    
    @Test
    public void rightResult(){
        List<MinimalCutSet> mcs = MOCUSEngine.getInstance().getMinimalCutSet(top);
        assertEquals(8, mcs.size());
        assertEquals(mcs.get(0).getCutSet().size(), 2);
        assertEquals(mcs.get(1).getCutSet().size(), 2);
        assertEquals(mcs.get(2).getCutSet().size(), 3);
        assertEquals(mcs.get(3).getCutSet().size(), 3);
        assertEquals(mcs.get(4).getCutSet().size(), 4);
        assertEquals(mcs.get(5).getCutSet().size(), 4);
        assertEquals(mcs.get(6).getCutSet().size(), 4);
        assertEquals(mcs.get(7).getCutSet().size(), 4);
    }

}
