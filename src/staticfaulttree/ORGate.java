package staticfaulttree;

import java.util.Iterator;
import java.util.List;

/**
 * Class that represent a logical OR gate with N inputs.
 *
 * @author Minarelli
 */
public final class ORGate extends Gate {

    private int id;

    /**
     * Constructor
     *
     * @param c List of nodes that are OR inputs.
     */
    public ORGate(List<Node> c) {
        Node n;
        for (Iterator<Node> i = c.iterator(); i.hasNext();) {
            n = i.next();
            addChild(n);
        }
        this.gateType = GateTypes.OR;
        id = lastId;
        lastId++;
        gateName = "";
    }

    public ORGate() {
        this.gateType = GateTypes.OR;
        id = lastId;
        lastId++;
        gateName = "";
    }

    /**
     * Constructor
     *
     * @param c List of nodes that are OR inputs.
     * @param gateName
     */
    public ORGate(List<Node> c, String gateName) {
        Node n;
        for (Iterator<Node> i = c.iterator(); i.hasNext();) {
            n = i.next();
            addChild(n);
        }
        this.gateType = GateTypes.OR;
        id = lastId;
        lastId++;
        this.gateName = gateName;
    }

    /**
     *
     * @param gateName
     */
    public ORGate(String gateName) {
        this.gateType = GateTypes.OR;
        id = lastId;
        lastId++;
        this.gateName = gateName;
    }

    /**
     * Methods tha returns the probability that this gate returns a failure at
     * the given time t
     *
     * @param t Time
     * @return Probability of failure at time t.
     */
    @Override
    public double getProbabilityFault(double t) {
        double ret = 0;
        for (Iterator<Node> i = children.iterator(); i.hasNext();) {
            ret += i.next().getProbabilityFault(t);
        }
        return ret;
    }

}
