package tests;

import CDF.ExponentialDistributionCDF;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import staticfaulttree.ANDGate;
import staticfaulttree.BasicEvent;
import staticfaulttree.Node;

/**
 *
 * @author Minarelli
 */
public class StaticFaultTreeTests {

    static Node aBasicEvent;
    static Node aGate;
    static Node aNode;

    @BeforeClass
    public static void setUpClass() {
        aBasicEvent = new BasicEvent(new ExponentialDistributionCDF(0.25), 10, "Test");
        aGate = new ANDGate(new ArrayList<>());
        aNode = new BasicEvent(new ExponentialDistributionCDF(0.5), 2, "Test1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void TransparencyOverSafety() {
        aGate.addChild(aNode);
        aBasicEvent.addChild(aNode);
    }

    @Test
    public void EvitableException() {
        if (!aBasicEvent.isBasicEvent()) {
            aBasicEvent.addChild(aNode);
        }
        if (!aGate.isBasicEvent()) {
            aGate.addChild(aNode);
        }
    }
}
