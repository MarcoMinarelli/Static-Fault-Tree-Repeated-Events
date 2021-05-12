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
public class ReliabilityOptimizer{

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

    private float computeFailureProbability(List<BasicEvent> l, float t) {
        float ret = 1;
        for (BasicEvent be : l) {
            ret *= be.getProbabilityFault(t);
        }
        return ret;
    }

    private double[] computeMCSMaintenanceCost(List<MinimalCutSet> mcs) {
        double[] cost = new double[mcs.size()]; //cost of maintenance for every MCS
        // Computing the maintenance cost of al Minimal Cut Set
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
    
    private void addBasicEvent(List<BasicEvent> bes, BasicEvent be){
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
}
