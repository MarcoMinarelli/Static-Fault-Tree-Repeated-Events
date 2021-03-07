package staticfaulttree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represent a logical OR gate with N inputs.
 * @author marco
 */
public final class ORGate implements Node{
    
    private List<Node> children = new ArrayList<>();
    private List<Node> parents = new ArrayList<>();

    public ORGate(){};
    /**
     * Constructor 
     * @param c List of nodes that are OR inputs.
     */
    public ORGate(List<Node> c){
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
    }

    /**
     * Methods tha returns the probability that this gate returns a failure at the given time t
     * @param t Time
     * @return  Probability of failure at time t. 
     */
    @Override
    public double getProbabilityFault(double t) {
        double ret = 0;
        for(Iterator<Node> i = children.iterator(); i.hasNext(); ){
            ret += i.next().getProbabilityFault(t);
        }
        return ret;
    }

    /**
     * Method that adds an input ("child") to this Gate.
     * @param node The node to be added at the Gate
     */
    @Override
    public void addChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.add(node);
        node.addParent(this);
    }

    /**
     * Method that removes an input ("child") to this Gate.
     * @param node The node to be remove at the Gate
     */
    @Override
    public void removeChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.remove(node);
        node.removeParent(this);
    }

    /**
     * Method that adds an output ("parent") to this Gate.
     * @param node The node that is a parent of this gate
     */
    @Override
    public void addParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.add(node);
        
    }
    
    /**
     * Method that removes an output ("parent") to this Gate.
     * @param node The node that will be removed as parent of this gate
     */
    @Override
    public void removeParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.remove(node);
    }

    /**
     * Method used to know if this Node is a Basic Event (that is, if one can
     * call addChild and removeChild without get an Exception)
     * @return True if this Node is a Basic Event, false otherwise.
     */
    @Override
    public boolean isBasicEvent() {
        return false;
    }
       
}
