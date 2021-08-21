
package minimalcutset;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import staticfaulttree.BasicEvent;

/**
 * Class that represent a Minimal Cut Set (MCS).
 * @author Minarelli
 */
public final class MinimalCutSet {
    Set<BasicEvent> cs;


    public MinimalCutSet() {
        cs = new HashSet<>();
    }

    /**
     * Methods that returns the BasicEvent componing this MinimalCutSet.
     * Note that the Basic Event are returned in alphabetcally order, based on their description.
     * @return List of BasicEvemt that compose this MinimalCutSet
     */
    public List<BasicEvent> getCutSet() {
        return cs.stream().sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription())).collect(Collectors.toList());
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
        if(!cs.add(be)){
            throw new IllegalArgumentException("A Minimal Cut set cannot contains the same Basic Event twice");
        }
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.cs);
        return hash;
    }

    @Override
    public String toString() {
        return "MinimalCutSet{" + "cs=" + cs + '}';
    }
}