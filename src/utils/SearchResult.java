
package utils;

import staticfaulttree.Node;

/**
 * Class that repredent the result of a Search in the Tree.
 *
 * @author Minarelli
 */
public final class SearchResult {

    private final boolean present;
    private final Node node;

    public SearchResult(boolean isPresent, Node node) {
        this.present = isPresent;
        this.node = node;
    }

    /**
     * Methods that indicates the presence of the desired Node in the tree
     *
     * @return A Boolean indicating if the Node is present in the tree
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Methods that returns the desired Node.
     *
     * @return A Boolean indicating if the Node is present in the tree
     */
    public Node getNode() {
        return node;
    }
}