package staticfaulttree;

import cdf.CDFInterface;
import java.util.ArrayList;
import java.util.List;
import utils.GraphSearcher;
import utils.SearchResult;
import staticfaulttree.Gate.GateTypes;

/**
 * Class (that implements a Builder pattern) that provides an alternative way
 * fir creating the Static Fault Tree.
 *
 * @author Minarelli
 */
public class StaticFaultTreeBuilder {

    private Node top;
    private Node current;

    /**
     * Constructor of the builder. It starts the tree construction. This
     * constructor puts as root of the tree (a.k.a. Top Event) a gate.
     *
     * @param type Top event type (And/Ot)
     * @param varnName Name of the associate variable at the Top Event
     */
    public StaticFaultTreeBuilder(GateTypes type, String varnName) {
        switch (type) {
            case AND: {
                top = new ANDGate(varnName);
                break;
            }
            case OR: {
                top = new ORGate(varnName);
                break;
            }
        }
        current = top;
    }

    /**
     * Constructor of the builder. It starts the tree construction.This
     * constructor puts as root of the tree (a.k.a.Top Event) a Basic Event
     * (this means that no children can be added).
     *
     * @param cdf The cumulative distribution function of the Basic Event
     * @param maintenanceCost Basic Event maintenance cost
     * @param varName Associate varible name
     */
    public StaticFaultTreeBuilder(CDFInterface cdf, float maintenanceCost, String varName) {
        top = new BasicEvent(cdf, maintenanceCost, varName);
        current = top;
    }

    /**
     * Method that returns the tree.
     *
     * @return The Top event of the builded tree
     */
    public Node build() {
        return top;
    }

    /**
     * Method that allows to add an And Gate to the current gate.
     *
     * @param varName Associate varible name
     * @return An instance of ths builder
     */
    public StaticFaultTreeBuilder addANDGate(String varName) {
        SearchResult sr = GraphSearcher.getNodeByName(varName, top);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new ANDGate(varName));
        }
        return this;
    }

    /**
     * Method that allows to add an Or Gate to the current gate.
     *
     * @param varName Associate varible name
     * @return An instance of this builder
     */
    public StaticFaultTreeBuilder addORGate(String varName) {
        SearchResult sr = GraphSearcher.getNodeByName(varName, top);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new ORGate(varName));
        }
        return this;
    }

    /**
     * Method that allows to add a Basic Event to the current gate.
     *
     * @param cdf The cumulative distribution function of the Basic Event
     * @param maintenanceCost Basic Event maintenance cost
     * @param varName Associate varible name
     * @return An instance of this builder
     */
    public StaticFaultTreeBuilder addBasicEvent(CDFInterface cdf, float maintenanceCost, String varName) {
        SearchResult sr = GraphSearcher.getNodeByName(varName, top);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new BasicEvent(cdf, maintenanceCost, varName));
        }
        return this;
    }

    /**
     * Method that sets the current node of the Tree.
     * Noe that future calls to addXXX or remove will work on this node.
     * @param varName Associate variable name
     * @return An instance of this builder
     */
    public StaticFaultTreeBuilder setCurrent(String varName) {
        SearchResult sr = GraphSearcher.getNodeByName(varName, top);
        if (sr.isPresent()) {
            current = sr.getNode();
        } else {
            throw new IllegalArgumentException("The given variable name is not present in the tree");
        }
        return this;
    } 
    
    /**
     * Method that removes and returns the passed nofe of te tree from the current gate.
     * @param varName Associate variable name
     * @return An instance of this builder
     */
    public List<Node> removeAndReturns(String varName){
        List<Node> ret = new ArrayList<>();
        if(!current.isBasicEvent()){
            for(Node n : current.getChildren()){
                if(n.isBasicEvent() && ((BasicEvent) n).getDescription().equals(varName) ){
                    ret.add(n);
                }else if(((Gate) n).getGateName().equals(varName)){
                    ret.add(n);
                }
            }
        }else{
            throw new IllegalArgumentException("Current node is a BasicEvent, does not have any children");
        }
        return ret;
    }
    
    /**
     * Method that removes and returns the passed nofe of te tree from the current gate.
     * @param varName Associate variable name
     */
    public void removeAndDelete(String varName){
        if(!current.isBasicEvent()){
            for(Node n : current.getChildren()){
                if(n.isBasicEvent() && ((BasicEvent) n).getDescription().equals(varName) ){
                    current.removeChild(n);
                }else if(((Gate) n).getGateName().equals(varName)){
                    current.removeChild(n);
                }
            }
        }else{
            throw new IllegalArgumentException("Current node is a BasicEvent, does not have any children");
        }
    }
    
}
