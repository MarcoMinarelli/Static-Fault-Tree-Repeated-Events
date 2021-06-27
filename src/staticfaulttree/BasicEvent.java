
package staticfaulttree;

import cdf.CDFInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a Basic Event
 * @author Barbuzzi
 */
public class BasicEvent implements Node{
    private final CDFInterface cdf;
    private final float maintenanceCost;
    private final String description;
    private double maintenanceTime = 0;
    private final int id;
    private static int lastId = 0;

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
        id = lastId;
        lastId++;
    }

    /**
     * Costructor of BasicEvent class, that specifies the id 
     * @param cdf CDF of the Basic Event
     * @param maintenanceCost Maintenance cost of the component
     * @param description Description of the component
     * @param id Id of the component
     */
    private BasicEvent(CDFInterface cdf, float maintenanceCost, String description, int id) {
        this.cdf = cdf;
        this.maintenanceCost = maintenanceCost;
        this.description = description;
        this.id = id;
    }
    
  
    @Override
    public double getProbabilityFault(double t) {
        return cdf.getProbability(t-maintenanceTime);
    }

    /**
     *  Method that adds a Node this object, if this object is a Gate. <br/>
     *  Being a BasicEvent, this method throws an Exception
     * @param node 
     * @throws UnsupportedOperationException If this object is a BasicEvent
     */
    @Override
    public void addChild(Node node) {
        throw new UnsupportedOperationException("Child operation on leaf object."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *  Method that removes a Node this object, if this object is a Gate. <br/>
     *  Being a BasicEvent, this method throws an Exception
     * @param node 
     * @throws UnsupportedOperationException If this object is a BasicEvent
     */
    @Override
    public void removeChild(Node node) {
        throw new UnsupportedOperationException("Child operation on leaf object."); //To change body of generated methods, choose Tools | Templates.
    }

 
 
    /**
     * Method that returns true if this node is a basic event
     * @return true
     */
    @Override
    public boolean isBasicEvent() {
        return true;
    }

    /**
     * Method that returns the maintenance cost
     * @return A float representing the maintenance cost
     */
    public float getMaintenanceCost() {
        return maintenanceCost;
    }

    /**
     * Method that returns the description
     * @return A String containing the description
     */
    public String getDescription() {
        return description;
    }

 
    /**
     * Method that returns the Id of this BasicEvent
     * @return An integer representing the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Method that set the maintenance time at the given value. This method 
     * assumes that is not possible to do maintenance over a component neither 
     * before its creation nor before the maintenance Time already setted   
     * @param maintenanceTime The new maintenance time
     * @throws IllegalArgumentException If the given time is below 0 or below 
     * the current maintenance time
     */
    public void setMaintenanceTime(double maintenanceTime) {
        if(maintenanceTime > this.maintenanceTime){
            this.maintenanceTime = maintenanceTime;
        }
        else if(maintenanceTime <= 0){
            throw new IllegalArgumentException("MaintenanceTime before basic event creation");
        }
        else if(maintenanceTime < this.maintenanceTime){
            throw new IllegalArgumentException("MaintenanceTime before last maintenance time");
        }
        
    }
    
    
    @Override
    public boolean equals(Object obj) {
        return id == ((BasicEvent) obj).id; 
    }

    /**
     * Method that returns  copy of this BasicEvent.
     * Note that the BasicEvent returned by this method <b> is not </b> a 
     * reference but a newly created object. 
     * @return A copy of this object
     */
    public BasicEvent copy() {
        BasicEvent ret = new BasicEvent(cdf, maintenanceCost, description, id);
        ret.maintenanceTime = maintenanceTime;
        return ret;
    }

    @Override
    public List<Node> getChild() {
        throw new UnsupportedOperationException("Child operation on leaf object."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return  description;
    }
    
    
    
    
}

