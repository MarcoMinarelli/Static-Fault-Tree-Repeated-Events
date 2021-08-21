package tests;

import cdf.ExponentialDistributionCDF;
import java.util.List;
import minimalcutset.MOCUSEngine;
import minimalcutset.MinimalCutSet;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import staticfaulttree.BasicEvent;
import staticfaulttree.Gate;
import staticfaulttree.Node;
import staticfaulttree.StaticFaultTreeBuilder;
import utils.GraphSearcher;

/**
 *
 * @author Minarelli
 */
public class MOCUSEngineTests {

    static Node top;
    static List<MinimalCutSet> mcs;

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
        mcs = MOCUSEngine.getInstance().getMinimalCutSet(top);
    }

    @Test
    public void rightMCSNumber() {

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
    
   @Test
   public void rightValues(){
       
        BasicEvent D1 = (BasicEvent) GraphSearcher.getNodeByName("D1", top).getNode();
        BasicEvent D2 = (BasicEvent) GraphSearcher.getNodeByName("D2", top).getNode();
        BasicEvent D3 = (BasicEvent) GraphSearcher.getNodeByName("D3", top).getNode();
        BasicEvent D4 = (BasicEvent) GraphSearcher.getNodeByName("D4", top).getNode();
        BasicEvent D5 = (BasicEvent) GraphSearcher.getNodeByName("D5", top).getNode();
        BasicEvent D6 = (BasicEvent) GraphSearcher.getNodeByName("D6", top).getNode();
        BasicEvent D7 = (BasicEvent) GraphSearcher.getNodeByName("D7", top).getNode();
        BasicEvent D8 = (BasicEvent) GraphSearcher.getNodeByName("D8", top).getNode();
        BasicEvent D9 = (BasicEvent) GraphSearcher.getNodeByName("D9", top).getNode();

        MinimalCutSet m = new MinimalCutSet();
        m.addNode(D2);
        m.addNode(D5);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D5);
        m.addNode(D6);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D2);
        m.addNode(D8);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D4);
        m.addNode(D8);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D6);
        m.addNode(D7);
        m.addNode(D8);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D7);
        m.addNode(D8);
        m.addNode(D9);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D3);
        m.addNode(D6);
        m.addNode(D8);
        assertEquals(mcs.contains(m), true);

        m = new MinimalCutSet();
        m.addNode(D1);
        m.addNode(D3);
        m.addNode(D8);
        m.addNode(D9);
        assertEquals(mcs.contains(m), true);
   }
}
