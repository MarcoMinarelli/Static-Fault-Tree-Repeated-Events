
package utils;

import java.util.ArrayList;
import java.util.List;
import minimalcutset.MinimalCutSet;

/**
 *
 * @author Barbuzzi
 */
public class MCSCombination {
    
    private MCSCombination(){}
    
    public static List<List<MinimalCutSet>> getCombination(List<MinimalCutSet> mcs){
        List<List<MinimalCutSet>> ret = new ArrayList<>();
        
        for(int len = 1; len<mcs.size(); len++){
            combinations(mcs, len, 0, new ArrayList<MinimalCutSet>(len), ret);
        }
        
        return ret;
    }
    
    
    private static void combinations(List<MinimalCutSet> mcs, int len, int startPosition, List<MinimalCutSet> result, List<List<MinimalCutSet>> res){
        if (len == 0){
            
            res.add(result);
            return;
        }       
        for (int i = startPosition; i <= mcs.size() -len; i++){
            result.add(result.size() - len, mcs.get(i));
            combinations(mcs, len-1, i+1, result, res);
        }
    }
            
    
    
}
