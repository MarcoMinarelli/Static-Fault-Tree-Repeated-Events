package staticfaulttree;

import java.util.ArrayList;
import java.util.List;
import utils.GraphSearcher;
import utils.SearchResult;

/**
 * Class that represent a Gate. <br/> This class is used as a common class that
 * provides common functionality to all the classes.
 *
 * @author Minarelli
 */
public abstract class Gate implements Node {

    protected List<Node> children = new ArrayList<>();

    protected String gateName;

    public enum GateTypes {
        AND, OR, KoutN
    };

    protected GateTypes gateType;

    protected static int lastId = 0;

    /**
     * Method that adds an input ("child") to this Gate.
     *
     * @param node The node to be added at the Gate
     */
    @Override
    public void addChild(Node node) {
        if (node != null) {
            children.add(node);
        } else {
            throw new NullPointerException("Null Node passed to method");
        }
    }

    /**
     * Method that removes an input ("child") to this Gate.
     *
     * @param node The node to be remove at the Gate
     */
    @Override
    public void removeChild(Node node) {
        if (node != null) {
            children.remove(node);
        } else {
            throw new NullPointerException("Null Node passed to method");
        }

    }

    @Override
    public boolean isBasicEvent() {
        return false;
    }

    /**
     * Method that returns the type (AND, OR, K/N) of this Gate=.
     *
     * @return An integer representing the type of this gate
     */
    public GateTypes getType() {
        return gateType;
    }

    public String getGateName() {
        return gateName;
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return gateName;
    }

    public Node copy() {
        Node ret = null;
        if (this.gateType == GateTypes.AND) {
            ret = new ANDGate(gateName);
        } else if (this.gateType == GateTypes.OR) {
            ret = new ORGate(gateName);
        }
        for (Node n : children) {
            if (n.isBasicEvent()) {
                SearchResult sr = GraphSearcher.getNodeByName(((BasicEvent) n).getDescription(), this);
                if (sr.isPresent()) {
                    ret.addChild(sr.getNode());
                } else {
                    ret.addChild(((BasicEvent) n).copy());
                }
            } else {
                SearchResult sr = GraphSearcher.getNodeByName(((Gate) n).getGateName(), this);
                if (sr.isPresent()) {
                    ret.addChild(sr.getNode());
                } else {
                    ret.addChild(((Gate) n).copy());
                }
                
            }
        }

        return ret;
    }

}
