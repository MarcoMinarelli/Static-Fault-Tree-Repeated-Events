package staticfaulttree;

import CDF.CDFInterface;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import staticfaulttree.Gate.GateTypes;

/**
 *
 * @author Minarelli
 */
public class StaticFaultTreeBuilder {

    private Node top;
    private Node current;

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

    public Node build() {
        return top;
    }

    public StaticFaultTreeBuilder addANDGate(String varName) {
        SearchResult sr = getNodeByName(varName);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new ANDGate(varName));
        }
        return this;
    }

    public StaticFaultTreeBuilder addORGate(String varName) {
        SearchResult sr = getNodeByName(varName);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new ORGate(varName));
        }
        return this;
    }

    public StaticFaultTreeBuilder addBasicEvent(CDFInterface cdf, float maintenanceCost, String varName) {
        SearchResult sr = getNodeByName(varName);
        if (sr.isPresent()) {
            current.addChild(sr.getNode());
        } else {
            current.addChild(new BasicEvent(cdf, maintenanceCost, varName));
        }
        return this;
    }

    public StaticFaultTreeBuilder setCurrent(String varName) {
        SearchResult sr = getNodeByName(varName);
        if (sr.isPresent()) {
            current = sr.getNode();
        } else {
            throw new IllegalArgumentException("The given variable name is not present in the tree");
        }
        return this;
    }

    private SearchResult getNodeByName(String varName) {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();

        visited.add(top);
        queue.add(top);
        SearchResult res = new SearchResult(false, null);

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Node n = queue.poll();
            if (n.toString().equals(varName)) {
                res = new SearchResult(true, n);
                found = true;
            } else {
                if (n.isBasicEvent()) {
                    visited.add(n);
                } else {
                    for (Node nn : n.getChild()) {
                        if (!visited.contains(nn)) {
                            queue.add(nn);
                            visited.add(nn);
                        }
                    }
                }
            }
        }
        return res;
    }

}

final class SearchResult {

    private final boolean present;
    private final Node node;

    public SearchResult(boolean isPresent, Node node) {
        this.present = isPresent;
        this.node = node;
    }

    public boolean isPresent() {
        return present;
    }

    public Node getNode() {
        return node;
    }
}
