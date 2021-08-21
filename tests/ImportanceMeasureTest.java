package tests;

import cdf.ExponentialDistributionCDF;
import java.util.ArrayList;
import java.util.List;
import measures.BirnbaumMeasure;
import measures.ImportanceMeasureValue;
import minimalcutset.MinimalCutSet;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import staticfaulttree.BasicEvent;
import measures.FusselVeselyMeasure;

/**
 *
 * @author Minarelli
 */
public class ImportanceMeasureTest {

    static BasicEvent A;
    static BasicEvent B;
    static BasicEvent C;
    static BasicEvent D;

    static MinimalCutSet mcs1;
    static MinimalCutSet mcs2;
    static MinimalCutSet mcs3;

    static List<MinimalCutSet> list;
    static List<BasicEvent> listbe = new ArrayList<>();

    @BeforeClass
    public static void setUpClass() {
        A = new BasicEvent(new ExponentialDistributionCDF(0.5), 10, "A");
        B = new BasicEvent(new ExponentialDistributionCDF(0.6), 10, "B");
        C = new BasicEvent(new ExponentialDistributionCDF(0.7), 10, "C");
        D = new BasicEvent(new ExponentialDistributionCDF(0.8), 10, "D");

        mcs1 = new MinimalCutSet();
        mcs1.addNode(A);

        mcs2 = new MinimalCutSet();
        mcs2.addNode(B);
        mcs2.addNode(C);

        mcs3 = new MinimalCutSet();
        mcs3.addNode(B);
        mcs3.addNode(D);

        list = new ArrayList<>();

        list.add(mcs1);
        list.add(mcs2);
        list.add(mcs3);

        listbe.add(A);
        listbe.add(B);
        listbe.add(C);

    }

    @Test
    public void BirbaumMeasure() {

        List<ImportanceMeasureValue<BasicEvent>> im = BirnbaumMeasure.getInstance().compute(list, 100, listbe);

        ImportanceMeasureValue<BasicEvent> val = null;

        boolean ret = false;
        for (ImportanceMeasureValue<BasicEvent> b : im) {
            if (b.getTreeElement().equals(A)) {
                ret = true;
                val = b;
            }
        }

        assertEquals(true, ret);
        assertEquals(A.getProbabilityFault(100), val.getValue(), 0.001);

        for (ImportanceMeasureValue<BasicEvent> b : im) {
            if (b.getTreeElement().equals(B)) {
                ret = true;
                val = b;
            }
        }

        assertEquals(true, ret);
        assertEquals(B.getProbabilityFault(100) + D.getProbabilityFault(100), val.getValue(), 0.001);

        for (ImportanceMeasureValue<BasicEvent> b : im) {
            if (b.getTreeElement().equals(C)) {
                ret = true;
                val = b;
            }
        }

        assertEquals(true, ret);
        assertEquals(B.getProbabilityFault(100), val.getValue(), 0.001);
    }

    @Test
    public void FusselVeselyMeasure() {
        List<ImportanceMeasureValue<BasicEvent>> im = FusselVeselyMeasure.getInstance().compute(list, 100, listbe);

        ImportanceMeasureValue<BasicEvent> val = null;

        boolean ret = false;
        for (ImportanceMeasureValue<BasicEvent> b : im) {
            if (b.getTreeElement().equals(C)) {
                ret = true;
                val = b;
            }
        }

        assertEquals(true, ret);
        assertEquals(A.getProbabilityFault(100) / (A.getProbabilityFault(100) + B.getProbabilityFault(100) * C.getProbabilityFault(100)
                + B.getProbabilityFault(100) * D.getProbabilityFault(100)), val.getValue(), 0.001);

        for (ImportanceMeasureValue<BasicEvent> b : im) {
            if (b.getTreeElement().equals(B)) {
                ret = true;
                val = b;
            }
        }

        assertEquals(true, ret);
        assertEquals((B.getProbabilityFault(100) * C.getProbabilityFault(100) + B.getProbabilityFault(100) * D.getProbabilityFault(100)) / (A.getProbabilityFault(100) + B.getProbabilityFault(100) * C.getProbabilityFault(100)
                + B.getProbabilityFault(100) * D.getProbabilityFault(100)), val.getValue(), 0.001);
    }

}
