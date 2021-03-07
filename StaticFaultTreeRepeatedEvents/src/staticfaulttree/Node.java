
package staticfaulttree;

/**
 * Interface that represent the Node in the static fault tree. 
 * @author marco
 */
public interface Node {
    double getProbabilityFault();
    void addChild(Node node);
    void removeChild(Node node);
    void addParent(Node node);
    void removeParent(Node node);
    boolean isBasicEvent();
}
