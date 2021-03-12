package staticfaulttree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Class that represent a N/N Gate, that is, a gate that let's the failure 
 *  expand if K out of N inputs are failed.
 * @author Minarelli
 */
public final class KoutNGate extends Gate{
    
    
    public KoutNGate(){};
    
    /**
     * Constructor 
     * @param c List of nodes that are OR inputs.
     */
    public KoutNGate(List<Node> c){
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
       //TODO
       return 0;
    }
    
}
