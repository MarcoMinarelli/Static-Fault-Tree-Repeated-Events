
package measures;

/**
 * Interface  that define the two methods for importance measures.
 * @author Barbuzzi
 */
public class ImportanceMeasure<T> {
    
    private T treeElement;
    private double value;

    public ImportanceMeasure(T treeElement, double value) {
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