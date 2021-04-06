
package staticfaulttree;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a Gate. <br/> This class is used as a common class
 *  that provides common functionality to all the classes.
 * @author Minarelli
 */
public abstract class  Gate implements Node{

    protected List<Node> children = new ArrayList<>();
    protected List<Node> parents = new ArrayList<>();
    
    public static int AND = 1;
    public static int OR = 0;
    public static int KoutN = 2; 
    
    protected int gateType;   
    
    
 /**
     * Method that adds an input ("child") to this Gate.
     * @param node The node to be added at the Gate
     */
    @Override
    public void addChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.add(node);
        node.addParent(this);
    }

    /**
     * Method that removes an input ("child") to this Gate.
     * @param node The node to be remove at the Gate
     */
    @Override
    public void removeChild(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        children.remove(node);
        node.removeParent(this);
    }

    /**
     * Method that adds an output ("parent") to this Gate.
     * @param node The node that is a parent of this gate
     */
    @Override
    public void addParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.add(node);
        
    }

    /**
     * Method that removes an output ("parent") to this Gate.
     * @param node The node that will be removed as parent of this gate
     */
    @Override
    public void removeParent(Node node) {
        if(node == null){
            throw new NullPointerException("Null Node passed to method");
        }
        parents.remove(node);
    }

    /**
     * Method used to know if this Node is a Basic Event (that is, if one can
     * call addChild and removeChild without get an Exception)
     * @return True if this Node is a Basic Event, false otherwise.
     */
    @Override
    public boolean isBasicEvent() {
        return false;
    }
    
    public int getType(){
        return gateType;
    }
    
}
