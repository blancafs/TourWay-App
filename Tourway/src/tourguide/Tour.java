package tourguide;

import java.util.logging.Logger;
import java.util.ArrayList;


public class Tour {
	private static Logger logger = Logger.getLogger("tourguide");
	
	String id;
	String title;
	Annotation annotation;
	
	ArrayList<Waypoint> waypointsList;
	ArrayList<Leg> legsList;
	
	public Tour(String i, String t, Annotation a){
		logger.finer("Creating " + t + "...");
		
		id = i;
		title = t;
		annotation = a;
	}
	
	public boolean equals(Object o){
		if (!(o instanceof Tour)) return false;
        Tour oT = (Tour) o;
        return id.equals(oT.id) &&
               title.equals(oT.title) &&
               annotation.equals(oT.annotation);
	}
	

}
