package com.shruthi.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.shruthi.sys.resources.AppConfig;

/**
 * @author Shruthi
 *
 */
public class Event implements Serializable {
	
	private String userName;
	String name;
	Date startDate;
	Date endDate;
	ArrayList<Activity> activities;
	ArrayList<Person> participants;
	private boolean approved;
	
	public class Activity implements Serializable {
		String name;
		int numStudents;
		Date date;
		String desc;
		
	}
	
	/** This is parameter constructor for Class : Event 
	 * @param user	user name
	 * @param eventName	event name 
	 * @param startDate starting date of the event
	 * @param endDate	ending date of the event
	 */
	public Event(String user, String eventName, Date startDate, Date endDate) {
		this.endDate = endDate;
		this.startDate = startDate;
		this.userName = user;
		this.name = eventName;
		activities = new ArrayList<Activity>();
		participants = new ArrayList<Person>();
	}
	
	/**To add the Activity event
	 * @param name event name
	 * @param desc	desciption 
	 * @param date  starting of the event
	 * @param numStudents number of the student will come to event
	 */
	public void addActivity(String name, String desc, Date date, int numStudents) {
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(Event.class);
		   
		Activity activity = new Activity();
		activity.name = name;
		activity.date = date;
		activity.desc = desc;
		activity.numStudents = numStudents;
		activities.add(activity);
		logger.info(AppConfig.PROPERTIES.getProperty("EVENT.addActivitySUCCESS"));
	}
	
	
	/**To add the Participant details to the event
	 * @param person person details
	 */
	public void addParticipants(Person person) {
		participants.add(person);
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(Event.class);
		logger.info(AppConfig.PROPERTIES.getProperty("EVENT.addParticipantSUCCESS"));
		   
	}
	
	
	/**Getting the approve for the event
	 * @param e event
	 */
	public static  void approve(Event e) {
		e.approved = true;
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(Event.class);
		logger.info(AppConfig.PROPERTIES.getProperty("EVENT.Approve"));
	}
	
	
	/**Checking the approve for the event
	 * @return status of an event
	 */
	public boolean checkApproval() {
		return this.approved;
	}
	
	
	/**
	 * Saving all the details of event in the file ( event.ser )
	 */
	public void save() {
		ArrayList<Event> events;
		events = (ArrayList<Event>)Utility.deserialize("events.ser");
		
		if(events == null) {
			events = new ArrayList<Event>();
		}
		
		boolean flag = true;
		for(Event e :events) {         
            if(!(this.startDate.before(e.startDate)&&this.endDate.before(e.startDate)||this.startDate.after(e.endDate)&&this.endDate.after(e.endDate)))
            flag=false;
        }
		
        if(flag) {
        	events.add(this);
        }
		
        Utility.serialize(events, "events.ser");
        
        DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(Event.class);
		logger.info(AppConfig.PROPERTIES.getProperty("EVENT.Saving"));
	
	}
	
	
	
	 /**Find Out the Pending events to get approval 
	 * @return Pending events
	 */
	public static ArrayList<Event> getPending() {
		   DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		   Logger logger = Logger.getLogger(Event.class);
		   
	        ArrayList<Event> event = new ArrayList<Event>();
	        event = (ArrayList<Event>)Utility.deserialize("Events.ser");
	        ArrayList<Event> temp = new ArrayList<Event>();
	       try {
	    	   for(Event tr : event){
	    		   if(!tr.checkApproval())
	                   temp.add(tr);
	           }
	       }
	       catch ( NullPointerException e) {
	    	  
			   logger.error(e.getMessage(), e);
	       }
	      
	        return temp; 
	    }
}