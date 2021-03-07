package staticfaulttree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  1417779fa2d0d5f04d5a4640a6fb7d6b60de15bd
 * @author marco
 */
public final class KoutNGate implements Node{
private List<Node> children = new ArrayList<>();
    private List<Node> parents = new ArrayList<>();
    
    public KoutNGate(){};
    public KoutNGate(List<Node> c){
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
    }

    @Override
    public double getProbabilityFault() {
       //TODO
       return 0;
    }

    @Override
    public void addChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.add(node);
        node.addParent(this);
    }

    @Override
    public void removeChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.remove(node);
        node.removeParent(this);
    }

    @Override
    public void addParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.add(node);
        
    }

    @Override
    public void removeParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.remove(node);
    }

    @Override
    public boolean isBasicEvent() {
        return false;
    }
    
}
