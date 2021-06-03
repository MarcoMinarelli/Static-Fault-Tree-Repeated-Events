package minimalcutset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import staticfaulttree.BasicEvent;
import staticfaulttree.Gate;
import staticfaulttree.Node;

/**
 *
 * @author Minarelli
 */
public final class MOCUSEngine {

    private MOCUSEngine() {
    }

    public static MOCUSEngine getInstance() {
        return MOCUSEngineHolder.INSTANCE;
    }

    private static class MOCUSEngineHolder {

        private static final MOCUSEngine INSTANCE = new MOCUSEngine();
    }

    public List<MinimalCutSet> getMinimalCutSet(Node topEvent) {
        List<MinimalCutSet> ret = new ArrayList<>();

        List<List<Node>> cs = init(topEvent);

        List<Set<Node>> css = list2Set(cs);

        css.sort((Set s1, Set s2) -> {
            int val;
            if (s1.size() == s2.size()) {
                val = 0;
            } else if (s1.size() < s2.size()) {
                val = -1;
            } else {
                val = 1;
            }
            return val;
        });

        for (int i = 0; i < css.size(); i++) {
            for (int j = i + 1; j < css.size(); j++) {
                if (css.get(j).containsAll(css.get(i))) {
                    css.remove(j);
                    j = j - 1;
                }
            }
        }

        for (Set<Node> s : css) {
            MinimalCutSet mcs = new MinimalCutSet();
            for (Node n : s) {
                mcs.addNode((BasicEvent) n);
            }
            ret.add(mcs);
        }

        return ret;
    }

    private List<Set<Node>> list2Set(List<List<Node>> cs) {
        List<Set<Node>> ret = new ArrayList<>();

        for (List<Node> n : cs) {
            ret.add(new HashSet<>(n));
        }

        return ret;
    }

    private List<List<Node>> init(Node topEvent) {
        List<List<Node>> ps = topToInitPath(topEvent);

        while (existExpandableGate(ps)) {
            Result r = findElementToExpand(ps);
            ps = CSHelper(r, ps);
        }

        return ps;
    }

    private boolean existExpandableGate(List<List<Node>> list) {
        boolean isPresent = false;
        for (int i = 0; i < list.size() && !isPresent; i++) {
            for (int j = 0; j < list.get(i).size() && !isPresent; j++) {

                if (!list.get(i).get(j).isBasicEvent()) {
                    isPresent = true;
                }
            }
        }

        return isPresent;
    }

    private void rewriteAnd(Node e, List<Node> row, int index) {
        row.remove(index);
        for (Node n : ((Gate) e).getChild()) {
            row.add(n);
        }
        Collections.reverse(row);
    }

    private List<List<Node>> rewriteOr(Node e, List<Node> row, int index) {
        List<List<Node>> newRows = new ArrayList<>();
        List<Node> x = row;
        x.remove(index);
        for (Node n : ((Gate) e).getChild()) {
            List<Node> loc = new ArrayList<>();
            loc.add(n);
            for (Node n1 : x) {
                loc.add(n1);
            }
            newRows.add(loc);
        }
        return newRows;
    }

    private List<List<Node>> CSHelper(Result res, List<List<Node>> paths) {
        List<List<Node>> updatedPaths = paths;
        Node e = paths.get(res.getFirst()).get(res.getSecond());
        List<Node> row = paths.get(res.getFirst());
        if (((Gate) e).getType() == Gate.AND) {
            rewriteAnd(e, row, res.getSecond());
        } else {
            updatedPaths.remove(res.getFirst());
            List<List<Node>> newRows = rewriteOr(e, row, res.getSecond());
            for (List<Node> r : newRows) {
                updatedPaths.add(r);
            }
        }
        return updatedPaths;
    }

    private List<List<Node>> topToInitPath(Node te) {
        List<List<Node>> ret = new ArrayList<>();
        if (((Gate) te).getType() == Gate.AND) {
            ret.add(((Gate) te).getChild());
        } else {
            for (Node n : ((Gate) te).getChild()) {
                List<Node> ln = new ArrayList<>();
                ln.add(n);
                ret.add(ln);
            }
        }
        return ret;
    }

    private Result findElementToExpand(List<List<Node>> paths) {
        Result ret = new Result(0, 0);
        for (int i = 0; i < paths.size(); i++) { // for row in paths
            for (int j = 0; j < paths.get(i).size(); j++) {// for e in row
                if (!paths.get(i).get(j).isBasicEvent()) {
                    ret = new Result(i, j);
                }
            }
        }
        return ret;
    }

}

final class Result {

    private final int first;
    private final int second;

    public Result(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
