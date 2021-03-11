
package staticfaulttree;

import CDF.CDFInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicol
 */
public class BasicEvent implements Node{
    private CDFInterface cdf;
    private float maintenanceCost;
    private String description;
    private double maintenanceTime = 0;
    private List<Node> parent = new ArrayList();

    /**
     * Costructor of BasicEvent class
     * @param cdf cumulative distribution function of this basic event
     * @param maintenanceCost cost of maintenanceCost of this basic event
     * @param description description of this basic event
     */
    public BasicEvent(CDFInterface cdf, float maintenanceCost, String description) {
        this.cdf = cdf;
        this.maintenanceCost = maintenanceCost;
        this.description = description;
    }
    
    
    @Override
    public double getProbabilityFault(double t) {
        return cdf.getProbability(t-maintenanceTime);
    }

    @Override
    public void addChild(Node node) {
        throw new UnsupportedOperationException("Child operation on leaf object."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Node node) {
        throw new UnsupportedOperationException("Child operation on leaf object."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addParent(Node node) {
        if(node == null){
            throw new NullPointerException("Not supported yet."); 
        }
        parent.add(node);
    }

    @Override
    public void removeParent(Node node) {
        if(node == null){
            throw new NullPointerException("Not supported yet."); 
        }
        parent.remove(node);
    }

    /**
     * Method that returns true if this node is a basic event
     * @return true
     */
    @Override
    public boolean isBasicEvent() {
        return true;
    }

    public float getMaintenanceCost() {
        return maintenanceCost;
    }

    public String getDescription() {
        return description;
    }

    public List<Node> getParent() {
        return parent;
    }

    public void setMaintenanceTime(double maintenanceTime) {
        if(maintenanceTime <= 0){
            throw new IllegalArgumentException("MaintenanceTime before basic event creation");
        }
        if(maintenanceTime < this.maintenanceTime){
            throw new IllegalArgumentException("MaintenanceTime before last maintenance time");
        }
        this.maintenanceTime = maintenanceTime;
    }
    
    
    
}
