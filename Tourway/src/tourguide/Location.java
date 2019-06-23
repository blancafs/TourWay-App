package tourguide;

import java.util.logging.Logger;

public class Location {
    private static Logger logger = Logger.getLogger("tourguide");
    
    public double easting;
    public double northing;
    
    public Location(double e, double n){
    	logger.finer("East: " + e + "  North: "  + n);
    	
    	easting = e;
    	northing = n;
    }
    
    public boolean equals(Object o){
		if (!(o instanceof Location)) return false;
        Location oL = (Location) o;
        return easting==(oL.easting) &&
               northing==(oL.northing);
	}
}
