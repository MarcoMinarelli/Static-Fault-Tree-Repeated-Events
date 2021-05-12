package staticfaulttree;

import static java.lang.Math.pow;
import java.util.Iterator;
import java.util.List;

/**
 *  Class that represent a K/N Gate, that is, a gate that let's the failure 
 *  expand if K out of N inputs are failed.
 * @author Minarelli
 */
public final class KoutNGate extends Gate{
    public int k;
    
    public KoutNGate(int k){
        k = this.k;
        this.gateType = Gate.KoutN;
    }

    /**
     * Constructor 
     * @param c List of nodes that are OR inputs.
     */
    public KoutNGate(List<Node> c, int k){
        k = this.k;
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
        this.gateType = Gate.KoutN;
    }

    /**
     * Methods tha returns the probability that this gate returns a failure at the given time t
     * @param t Time
     * @return  Probability of failure at time t. 
     * @author Barbuzzi
     */
    @Override
    public double getProbabilityFault(double t) {
       double rSys = 0; //Reliability of system
       double rCdf = 1; //Reliability of single component
       int n = children.size();
       int nFatt = 1; //var di appoggio per n!
       for(int i=1; i<=n; i++){
           nFatt = nFatt * i;
       }
       
       for(int i = k; i<=n; i++){
           int inFatt = 1;
           for(int j=1; j<=(n-i); j++){
               inFatt = inFatt * j;
           }
           int iFatt = 1;
           for(int m=1; m<=i; m++){
               iFatt = iFatt*m;
           }
           
           int bin = nFatt/((inFatt)*(iFatt));
           rCdf = children.get(i).getProbabilityFault(t);
           
           rSys = rSys + bin*pow((1-rCdf),i)*pow(rCdf,(n-i));   
       }
       
       return (1 - rSys);
    }
    
}
