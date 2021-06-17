
package minimalcutset;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import staticfaulttree.BasicEvent;

/**
 * Class that represent a Minimal Cut Set (MCS).
 * @author Minarelli
 */
public class MinimalCutSet {
    List<BasicEvent> cs;


    public MinimalCutSet() {
        cs = new ArrayList<>();
    }

    public List<BasicEvent> getCutSet() {
        return cs;
    }
    
    
    /**
     * Method that allow to add a node to the current MCS.
     * A BasicEvent may aooear only once in a MCS, e.g: given a BasicEvent A, the 
     * MCS AA is illegal (while the MCS AB is not).
     * @param be The BasicEvent that will be added to this MCS
     * @throws NullPointerException If the BasicEvent passed is null
     * @throws IllegalArgumentException If an already added 
     * BasicEvent is passed.
     */
    public void addNode(BasicEvent be){
        if(be == null){
            throw new NullPointerException("Null Node passed");
        }
        if(cs.contains(be)){
            throw new IllegalArgumentException("A Minimal Cut set cannot contains the same Basic Event twice");
        }
        cs.add(be);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MinimalCutSet other = (MinimalCutSet) obj;
        return cs.equals(other.cs);
        
    }
    
    
   
}
