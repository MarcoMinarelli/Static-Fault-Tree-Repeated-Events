package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import staticfaulttree.BasicEvent;
import staticfaulttree.Node;

/**
 * Class that permits to search on the SFT.
 *
 * @author Minarelli
 */
public class GraphSearcher {

    private GraphSearcher() {
    }

    /**
     * MEthod that given the TopEvent returns all the BasicEvents of the SFT
     * @param top Node representing the Top Event of the SFT
     * @return Lst of all the BasicEvents in the tree
     */
    public static List<BasicEvent> getBasicEvent(Node top) {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();
        ArrayList<BasicEvent> ret = new ArrayList<>();

        visited.add(top);
        queue.add(top);

        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Node n = queue.poll();
            if (n.isBasicEvent()) {
                visited.add(n);
                ret.add((BasicEvent) n);
            } else {
                for (Node nn : n.getChild()) {
                    if (!visited.contains(nn)) {
                        queue.add(nn);
                        visited.add(nn);
                    }
                }
            }
        }

        return ret;
    }
    
     public static SearchResult getNodeByName(String varName, Node top) {
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


   



