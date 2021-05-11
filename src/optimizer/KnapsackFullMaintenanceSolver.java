/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class KnapsackFullMaintenanceSolver {
    private static KnapsackFullMaintenanceSolver kfms = new KnapsackFullMaintenanceSolver();
    
    KnapsackSolver solver = new KnapsackSolver(KnapsackSolver.SolverType.KNAPSACK_MULTIDIMENSION_BRANCH_AND_BOUND_SOLVER, "Full Maintenance");
    
    private KnapsackFullMaintenanceSolver(){}
    
    public static KnapsackFullMaintenanceSolver getInstance(){
        return kfms;
    }
    
    public List<BasicEvent> optimize(List<MinimalCutSet> mcs, float testTime, float budget) {
        List<BasicEvent> ret = new ArrayList<>();
        
        long [] bud = {(long) budget*100}; //capacità
        long [][] cost = getCost(mcs); //pesi
        
        long relPre = 0;
        for(int i=0; i<mcs.size(); i++){
            relPre += getReliability(mcs.get(i).getCutSet(), testTime);
        }
        
       
        long [] relPost = new long[mcs.size()]; //guadagno
        for(int i=0; i<mcs.size(); i++){
            MinimalCutSet cs = mcs.get(i);
            long rel = 0;
            for (int j = 0; j < mcs.size(); j++) { //for every OTHER MCS
                if (j != i) {
                    rel += getReliability(mcs.get(j).getCutSet(), testTime);
                }
            }
            List<BasicEvent> modified = new ArrayList<>();
            BasicEvent appo;
            for (BasicEvent be : cs.getCutSet()) {
                appo = be.copy();
                appo.setMaintenanceTime(testTime);
                modified.add(appo);
            }
            rel += getReliability(modified, testTime);

            relPost[i] = (rel/relPre);
        }
        
        solver.init(relPost, cost, bud);
        for(int i=0; i<relPost.length; i++){
            if(solver.bestSolutionContains(i)){
                for(BasicEvent b:mcs.get(i).getCutSet()){
                    addBe(ret, b);
                }
            }
        }
        
        return ret;
    }
    
    private long[][] getCost(List<MinimalCutSet> mcs){
        long[][] cost = new long[mcs.size()][];
        
        for(int i=0; i<mcs.size(); i++){
            for(BasicEvent b:mcs.get(i).getCutSet())
                cost[i][0] += b.getMaintenanceCost();
        }
        return cost;
    }
    
    private long getReliability(List<BasicEvent> bes, double time){
        float fail = 1;
        
        for(BasicEvent b:bes){
            fail *= b.getProbabilityFault(time);

        }
        return (long) ((1- fail)*100);
    }
    
    private void addBe(List<BasicEvent> bes, BasicEvent be){
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
