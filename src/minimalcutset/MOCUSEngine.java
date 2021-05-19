package minimalcutset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, List<String>> map = toMap(topEvent);

        return ret;
    }

    private void init(/*Map<String, List<String>> map,*/ Node topEvent) {
        //List<String> top = map.get(topEvent.getUniqueId());
       // List<List<String>> ps = topToInitPath(top);
        List<List<Node>> ps = topToInitPathNode(topEvent);
        Result r = findElementToExpand(ps);
    }

    
    
    private void rewriteAnd(Node e, List<Node> row, int index) {
        //TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private List<List<Node>> rewriteOr(Node e, List<Node> row, int index) {
        List<List<Node>> newRows = new ArrayList<>();
        List<Node> x = row;
        x.remove(index);
        for(Node n : ((Gate) e).getChild()){
            List<Node> loc = new ArrayList<>();
            loc.add(n);
            for(Node n1 : x){
                loc.add(n1);
            }
            newRows.add(loc);
        }        
        return newRows;
    }
    
    private List<List<Node>> CSHelper(Result res, List<List<Node>> paths){
        List<List<Node>> updatedPaths = paths;
        Node e = paths.get(res.getFirst()).get(res.getSecond());
        List<Node> row = paths.get(res.getFirst());
        if(((Gate) e).getType() == Gate.AND){
            rewriteAnd(e, row, res.getSecond());
        }else{
            updatedPaths.remove(res.getFirst());
            List<List<Node>> newRows =  rewriteOr(e, row, res.getSecond());
            for(List<Node> r : newRows){
                updatedPaths.add(r);
            }
        }
        return updatedPaths;
    } 
    
    
    
    private List<List<Node>> topToInitPathNode(Node te) {
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

    
    private Result findElementToExpand(List<List<Node>> paths){
        Result ret = new Result(0, 0);
        for(int i = 0; i < paths.size(); i++){ // for row in paths
            for(int j = 0; j < paths.get(i).size(); i++){// for e in row
                if(!paths.get(i).get(j).isBasicEvent()){
                    ret = new Result(i, j);
                }
            }
        }
        return ret;
    }
    
    private List<List<String>> topToInitPath(List<String> te) {
        List<List<String>> path = new ArrayList<>();
        if (Integer.parseInt(te.get(0)) == Gate.AND) {
            path.add(te.subList(2, te.size()));
        } else {
            for (String s : te.subList(2, te.size())) {
                List<String> ls = new ArrayList<>();
                ls.add(s);
                path.add(ls);
            }
        }
        return path;
    }

    /**
     * Method that converts the tree in a Map.
     * <br/> The map has as key the id of the Node and as satellite data the
     * type of te gate and the list of Node children
     *
     * @param topEvent
     * @return
     */
    private Map<String, List<String>> toMap(Node topEvent) {
        HashMap<String, List<String>> map = new HashMap<>();

        if (topEvent != null) {
            preOrderVisit(topEvent, map);
        } else {
            throw new NullPointerException("Null TopEvent passed");
        }
        return map;
    }

    private void preOrderVisit(Node node, HashMap<String, List<String>> map) {
        if (node != null) {
            List<String> l = node2List(node);
            map.put(l.get(0), l.subList(1, l.size()));
            if (!node.isBasicEvent()) {
                for (Node n : node.getChild()) {
                    preOrderVisit(n, map);
                }
            }
        } else {
            throw new NullPointerException("Null Node passed");
        }
    }

    private List<String> node2List(Node n) {
        List<String> ret = new ArrayList<>();
        if (n != null) {
            if (!n.isBasicEvent()) {
                ret.add(n.getUniqueId());
                ret.add(Integer.toString((((Gate) n).getType())));
                for (Node c : n.getChild()) {
                    ret.add(c.getUniqueId());
                }
            }
        } else {
            throw new NullPointerException("Null Node passed");
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