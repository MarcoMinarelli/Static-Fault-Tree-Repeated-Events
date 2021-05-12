package optimizer;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.util.ArrayList;
import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;

/**
 * Class that implements an algorithm that allows to compute the minimal cost (and the
 * relative maintenance) in order to obtain a desoder reliability at a given time.
 * @author Minarelli
 */
public class ReliabilityOptimizer extends Optimizer{

    private static ReliabilityOptimizer opt = new ReliabilityOptimizer();

    private ReliabilityOptimizer() {
    }

    private static ReliabilityOptimizer getInstance() {
        return opt;
    }

    public List<BasicEvent> optimize(List<MinimalCutSet> mcs, float testTime, float startTime, float minimumAffidabiltiy) {
        List<BasicEvent> ret = new ArrayList<>();

        double[][] failuresProb = computeFailureProbabilities(mcs, startTime, testTime);
        double[] cost = computeMCSMaintenanceCost(mcs);

        double desiredFailureProb = (1 - minimumAffidabiltiy);

        MPSolver solver = MPSolver.createSolver("SCIP");

        //Declaring problem variables
        MPVariable[] var = new MPVariable[mcs.size()];
        for (int i = 0; i < mcs.size(); i++) {
            var[i] = solver.makeBoolVar(Integer.toString(i));
        }

        //Declaring problem constraint
        MPConstraint constraint = solver.makeConstraint(0.0, desiredFailureProb);
        for (int i = 0; i < mcs.size(); i++) {
            constraint.setCoefficient(var[i], failuresProb[i][0]);
        }

        //Declaring objective function
        MPObjective objective = solver.objective();
        for (int i = 0; i < mcs.size(); i++) {
            objective.setCoefficient(var[i], cost[i]);
        }
        objective.setMaximization();
        
        MPSolver.ResultStatus result = solver.solve();
        
        if(result == MPSolver.ResultStatus.OPTIMAL){
            for(int i=0; i<mcs.size(); i++){
                if(var[i].solutionValue() == 1){
                    for(BasicEvent b:mcs.get(i).getCutSet()){
                        addBasicEvent(ret, b);                        
                    }
                }
            }
        }
        return ret;
    }


    /**
     *  Method that computes the failure probabilities of all MCS. 
     * <\br> The method computes the failure probabilities at the time testTime, but 
     * i-th MCS, the Failure probability is computed as if the MCS is maintained at time testTime.
     * So, for that MCS the result is: probability that all other MCS fails at time testTime + probability that the i-th MCS 
     * fails at the time testTime given that it was maintaine at time startTime
     * @param mcs The list of all Minimal Cut Set
     * @param startTime Time value at which the MCS are maintained (see above)
     * @param testTime Time value at which the test will be performed
     * @return A matrix (only the first row is not 0) that contains the probaiblity of failure (see above) 
     */
    private double[][] computeFailureProbabilities(List<MinimalCutSet> mcs, float startTime, float testTime) {
        double[][] failuresProb = new double[mcs.size()][]; // system reliability after MCS maintenance
        for (int i = 0; i < mcs.size(); i++) {
            MinimalCutSet cs = mcs.get(i);
            float failureProbability = 0;
            for (int j = 0; j < mcs.size(); j++) { //for every OTHER MCS
                if (j != i) {
                    failureProbability += computeFailureProbability(mcs.get(j).getCutSet(), testTime);
                }
            }
            List<BasicEvent> mod = new ArrayList<>();
            BasicEvent ap;
            for (BasicEvent be : cs.getCutSet()) {
                ap = be.copy();
                ap.setMaintenanceTime(startTime);
                mod.add(ap);
            }
            failureProbability += computeFailureProbability(mod, testTime);

            failuresProb[i][0] = failureProbability * 100;
        }
        return failuresProb;
    }
}
