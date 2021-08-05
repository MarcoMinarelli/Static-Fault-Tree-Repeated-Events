
package staticfaulttree;

import java.util.List;

/**
 * Interface that represent the Node in the static fault tree. 
 * @author Minarelli
 */
public interface Node {
    
      /**
     * Method that returns the probability fault of the BasicEvent at given time t
     * @param t Time 
     * @return Probability of fault at time t
     */
    double getProbabilityFault(double t);
    
    /**
     * Method that adds an input ("child") to this Node, if this node is a Gate.
     * @param node The node to be added at the Gate
     * @throws 
     */
    void addChild(Node node);
    
    /**
     * Method that removes an input ("child") to this Node, if this node is a Gate.
     * @param node The node to be added at the Gate
     */
    void removeChild(Node node);
    
    List<Node> getChildren();
     /**
     * Method used to know if this Node is a Basic Event (that is, if one can
     * call addChild and removeChild without get an Exception)
     * @return True if this Node is a Basic Event, false otherwise.
     */
    boolean isBasicEvent();
    
}
