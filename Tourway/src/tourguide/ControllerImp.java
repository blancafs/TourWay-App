/**
 * 
 */
package tourguide;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author pbj
 */
public class ControllerImp implements Controller {
    private static Logger logger = Logger.getLogger("tourguide");
    private static final String LS = System.lineSeparator();

    private String startBanner(String messageName) {
        return  LS 
                + "-------------------------------------------------------------" + LS
                + "MESSAGE: " + messageName + LS
                + "-------------------------------------------------------------";
    }  
    double waypointRadius;
    double waypointSeparation;
    Location location;
    Tour curtour;
    ArrayList<Tour> tourList = new ArrayList<Tour>();
   
    Mode mode = Mode.BROWSETOUR;
    
    public ControllerImp(double waypointRadius, double waypointSeparation) {
    	this.waypointRadius=waypointRadius;
    	this.waypointSeparation=waypointSeparation;
    }

    //--------------------------
    // Create tour mode
    //--------------------------

    // Some examples are shown below of use of logger calls.  The rest of the methods below that correspond 
    // to input messages could do with similar calls.
    
    @Override
    public Status startNewTour(String id, String title, Annotation annotation) {
        logger.fine(startBanner("startNewTour"));
        if(mode==Mode.FOLLOWTOUR){    //cannot start a creating a tour if in FOLLOWTOUR mode
        	logger.warning("Cannot create tour while following one");
        	return new Status.Error("Cannot create tour while following one");
        } else {
        curtour.id=id;
        curtour.title=title;
        curtour.annotation=annotation;
        }
        // Check that the operation was completed successfully
        if(curtour.annotation.equals(annotation) && curtour.id==id && curtour.title==title){
        	mode=Mode.CREATETOUR;
        	return Status.OK;
        } else { return new Status.Error("The tour was not created successfully.");}
    }

    @Override
    public Status addWaypoint(Annotation annotation) {
        logger.fine(startBanner("addWaypoint"));
        if(mode!=Mode.CREATETOUR){    // Can only add waypoint if Controller is in CREATETOUR mode
        	logger.warning("Waypoint was not created successfully");
        	return new Status.Error("Mode is not CreateTour");
        }
        
        Waypoint waypoint = new Waypoint(annotation);
        waypoint.location = this.location;
        
        if(waypoint.isAllowedIn(curtour, this.waypointSeparation)){
        	curtour.waypointsList.add(waypoint);
        	logger.fine("Waypoint added to the tour");
        	return Status.OK;
        } else {
        	logger.warning("Waypoint too close");
        	return new Status.Error("Waypoint not added");
        }
    }

    @Override
    public Status addLeg(Annotation annotation) {
        logger.fine(startBanner("addLeg"));
        return new Status.Error("unimplemented");
    }

    @Override
    public Status endNewTour() {
        logger.fine(startBanner("endNewTour"));
        if(mode!=Mode.CREATETOUR){    // Can only finalize tour if Controller is in CREATETOUR mode
        	logger.warning("No current tour selected");
        	return new Status.Error("Mode is not CreateTour");
        }
        
        int lBefore = tourList.size();    // Stores the amount of tours the controller owns before curtour is added
        tourList.add(curtour);
        
        if(lBefore+1==tourList.size()){    // Checks that curtour was added successfully in the list
        	logger.fine("Tour was finalized successfully");
        	
        	return Status.OK;
        }else{
        	return new Status.Error("unimplemented");
        }
    }

    //--------------------------
    // Browse tours mode
    //--------------------------

    @Override
    public Status showTourDetails(String tourID) {
        return new Status.Error("unimplemented");
    }
  
    @Override
    public Status showToursOverview() {
        return new Status.Error("unimplemented");
    }

    //--------------------------
    // Follow tour mode
    //--------------------------
    
    @Override
    public Status followTour(String id) {
        return new Status.Error("unimplemented");
    }

    @Override
    public Status endSelectedTour() {
        return new Status.Error("unimplemented");
    }

    //--------------------------
    // Multi-mode methods
    //--------------------------
    @Override
    public void setLocation(double easting, double northing) {
    	this.location.easting=easting;
    	this.location.northing=northing;
    	
    	logger.fine("Controller location was updated successfully");
    }

    @Override
    public List<Chunk> getOutput() {
        return new ArrayList<Chunk>();
    }
    
    public enum Mode {
    	CREATETOUR,
    	BROWSETOUR,
    	FOLLOWTOUR
    }


}
