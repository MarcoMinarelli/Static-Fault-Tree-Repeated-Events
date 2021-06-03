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
import staticfaulttree.Node;
import staticfaulttree.ORGate;

/**
 *
 * @author Minarelli
 */
public class MOCUSEngineTests {
    
    static Node top;

    @BeforeClass
    public static void setUpClass() {

        Node D1 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D1");
        Node D2 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D2");
        Node D3 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D3");
        Node D4 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D4");
        Node D5 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D5");
        Node D6 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D6");
        Node D7 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D7");
        Node D8 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D8");
        Node D9 = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "D9");

        top = new ANDGate("Top");
        Node B1 = new ORGate("B1");
        Node B2 = new ORGate("B2");

        top.addChild(B1);
        top.addChild(B2);

        Node C1 = new ANDGate("C1");
        Node C2 = new ANDGate("C2");
        Node C3 = new ANDGate("C3");
        Node C4 = new ANDGate("C4");

        C1.addChild(D1);
        C1.addChild(D2);

        C2.addChild(D1);
        Node E2 = new ORGate("E2");
        E2.addChild(D3);
        E2.addChild(D4);
        C2.addChild(E2);

        C3.addChild(D5);
        Node E3 = new ORGate("E3");
        E3.addChild(D2);
        E3.addChild(D6);
        C3.addChild(E3);
        
        C4.addChild(D1);
        C4.addChild(D7);

        B1.addChild(C1);
        B1.addChild(C2);
        B1.addChild(C3);
        B1.addChild(C4);

        Node C5 = new ANDGate("C5");
        Node C6 = new ANDGate("C6");
        Node C7 = new ANDGate("C7");

        C5.addChild(D5);
        Node E5 = new ORGate("E5");
        E5.addChild(D2);
        E5.addChild(D6);
        C5.addChild(E5);

        C6.addChild(D8);
        Node E6 = new ORGate("E6");
        E6.addChild(D2);
        E6.addChild(D4);
        E6.addChild(D6);
        C6.addChild(E6);

        C7.addChild(D8);
        Node E7 = new ORGate("E7");
        E7.addChild(D4);
        E7.addChild(D9);
        C7.addChild(E7);

        B2.addChild(C5);
        B2.addChild(C6);
        B2.addChild(C7);        
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
