
package staticfaulttree;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Class that represent a logical AND Gate, with N inputs. 
 * @author Minarelli
 */
public final class ANDGate extends Gate{
    
    
    /**
     * Constructor 
     * @param c List of nodes that are AND inputs.
     */
    public ANDGate(List<Node> c){
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
        this.gateType = Gate.AND;
    }
    
    public ANDGate(){
        this.gateType = Gate.AND;
    }
    
    

    /**
     * Methods tha returns the probability that this gate returns a failure at the given time t
     * @param t Time
     * @return  Probability of failure at time t. 
     */
    @Override
    public double getProbabilityFault(double t) {
        double ret = 1;
        for(Iterator<Node> i = children.iterator(); i.hasNext(); ){
            ret *= i.next().getProbabilityFault(t);
        }
        return ret;
    }
}
