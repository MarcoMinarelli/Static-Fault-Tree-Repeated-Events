
package measures;

import java.util.List;
import minimalcutset.MinimalCutSet;

/**
 * Interface  that define the two methods for importance measures.
 * @author Barbuzzi
 */
public interface ImportanceMeasure {
    
    public double[] compute(List<MinimalCutSet> mcs, double time);
    
    public double compute(List<MinimalCutSet> mcs, double time, int index);
    
}
