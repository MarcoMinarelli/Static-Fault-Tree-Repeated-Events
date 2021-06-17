package optimizer;

import com.google.ortools.algorithms.KnapsackSolver;
import java.util.ArrayList;
import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;

/**
 *
 * @author Barbuzzi
 */
public class KnapsackFullMaintenanceSolver extends Optimizer {

    private static KnapsackFullMaintenanceSolver kfms = new KnapsackFullMaintenanceSolver();

    KnapsackSolver solver = new KnapsackSolver(KnapsackSolver.SolverType.KNAPSACK_MULTIDIMENSION_BRANCH_AND_BOUND_SOLVER, "Full Maintenance");

    private KnapsackFullMaintenanceSolver() {
    }

    public static KnapsackFullMaintenanceSolver getInstance() {
        return kfms;
    }

    public List<BasicEvent> optimize(List<MinimalCutSet> mcs, float testTime, float budget) {
        List<BasicEvent> ret = new ArrayList<>();

        long[] bud = {(long) budget * 100}; //capacity
        long[][] cost = getMCSMaintenanceCost(mcs); //weights

        long relPre = 0;
        for (int i = 0; i < mcs.size(); i++) {
            relPre += (1 - computeFailureProbability(mcs.get(i).getCutSet(), testTime));
        }

        long[] gain = new long[mcs.size()]; //gain
        for (int i = 0; i < mcs.size(); i++) {
            long  relPost = (long) (1 - computeFailureProbability(getMaintainedMCS(mcs.get(i), testTime), testTime));
            relPost += computeRemainingCutSetsReliability(mcs, i, testTime);
            gain[i] = (relPost / relPre);
        }

        solver.init(gain, cost, bud);
        for (int i = 0; i < gain.length; i++) {
            if (solver.bestSolutionContains(i)) {
                for (BasicEvent b : mcs.get(i).getCutSet()) {
                    addBasicEvent(ret, b);
                }
            }
        }

        return ret;
    }

    private long computeRemainingCutSetsReliability(List<MinimalCutSet> mcs, int index, float testTime) {
        MinimalCutSet cs = mcs.get(index);
        long rel = 0;
        for (int j = 0; j < mcs.size(); j++) { //for every OTHER MCS
            if (j != index) {

                rel += (1 - computeFailureProbability(mcs.get(j).getCutSet(), testTime));

            }
        }
        return rel;
    }

    private List<BasicEvent> getMaintainedMCS(MinimalCutSet cs, float testTime) {
        List<BasicEvent> modified = new ArrayList<>();
        BasicEvent appo;
        for (BasicEvent be : cs.getCutSet()) {
            appo = be.copy();
            appo.setMaintenanceTime(testTime);
            modified.add(appo);
        }
        return modified;
    }


    private long[][] getMCSMaintenanceCost(List<MinimalCutSet> mcs) {
        long[][] cost = new long[mcs.size()][];
        double[] costDouble = computeMCSMaintenanceCost(mcs);
        for (int i = 0; i < mcs.size(); i++) {
            cost[i][0] = (long) (costDouble[i] * 100);
        }
        return cost;
    }

}
