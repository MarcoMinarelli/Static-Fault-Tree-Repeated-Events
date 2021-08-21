package measures;

import java.util.List;
import minimalcutset.MinimalCutSet;
import staticfaulttree.BasicEvent;

/**
 * Interface that define the two methods for importance measures.
 *
 * @author Barbuzzi
 */
public interface ImportanceMeasure {

     public List<ImportanceMeasureValue<BasicEvent>> compute(List<MinimalCutSet> mcs, double time, List<BasicEvent> bes);
     
     public double compute(List<MinimalCutSet> mcs, double time, int index, List<BasicEvent> bes);
}
