package staticfaulttree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represent a logical OR gate with N inputs.
 * @author marco
 */
public final class ORGate extends Gate{
    
    private List<Node> children = new ArrayList<>();
    private List<Node> parents = new ArrayList<>();

    /**
     * Constructor 
     * @param c List of nodes that are OR inputs.
     */
    public ORGate(List<Node> c, int gateType){
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
        this.gateType = Gate.OR;
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
}
