package tourguide;

import java.util.logging.Logger;

public class Waypoint {
	private static Logger logger = Logger.getLogger("tourguide");
	
	public Location location;
	public Annotation annotation;
	
	public Waypoint(Annotation a){
		logger.finer("Waypoint created");
		annotation=a;
	}
	
	public boolean isFurtherToThan(Waypoint w, double minSep){
    	double e = (this.location.easting) - (w.location.easting);
    	double n = (this.location.northing) - (w.location.northing);
    	Displacement dis = new Displacement(e,n);
    	double distance = dis.distance();
    	
    	if(distance<minSep){
    		return false;
    	} else {
    		return true;
    	}
    }
	
	public boolean isAllowedIn(Tour t, double minSep){
		for(Waypoint w : t.waypointsList){
			if(!this.isFurtherToThan(w, minSep)){return false;}
		}
		return true;
	}
	
	public boolean equals(Object o){
		if (!(o instanceof Waypoint)) return false;
        Waypoint oW = (Waypoint) o;
        return location.equals(oW.location) &&
               annotation.equals(oW.annotation);
	}
}