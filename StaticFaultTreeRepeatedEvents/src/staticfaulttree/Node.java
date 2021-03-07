
package staticfaulttree;

/**
 * Interface that represent the Node in the static fault tree. 
 * @author Minarelli
 */
public interface Node {
    double getProbabilityFault(double t);
    void addChild(Node node);
    void removeChild(Node node);
    void addParent(Node node);
    void removeParent(Node node);
    boolean isBasicEvent();
}
