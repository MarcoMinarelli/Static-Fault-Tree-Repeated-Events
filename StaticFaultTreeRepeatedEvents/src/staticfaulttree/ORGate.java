/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticfaulttree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author marco
 */
public final class ORGate implements Node{
    
    private List<Node> children = new ArrayList<>();
    private List<Node> parents = new ArrayList<>();

    public ORGate(){};
    public ORGate(List<Node> c){
        Node n;
        for(Iterator<Node> i = c.iterator(); i.hasNext(); ){
            n= i.next();
            addChild(n);
            n.addParent(this);
        }
    }

    @Override
    public double getProbabilityFault() {
        double ret = 0;
        for(Iterator<Node> i = children.iterator(); i.hasNext(); ){
            ret += i.next().getProbabilityFault();
        }
        return ret;
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
