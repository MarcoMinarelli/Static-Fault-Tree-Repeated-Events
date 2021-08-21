
package measures;

/**
 * Interface  that define the two methods for importance measures.
 * @author Barbuzzi
 */
public class ImportanceMeasureValue<T> {
    
    private T treeElement;
    private double value;

    public ImportanceMeasureValue(T treeElement, double value) {
        this.treeElement = treeElement;
        this.value = value;
    }

    public T getTreeElement() {
        return treeElement;
    }

    public double getValue() {
        return value;
    }
}