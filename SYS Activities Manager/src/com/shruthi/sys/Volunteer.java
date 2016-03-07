package com.shruthi.sys;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Shruthi
 *
 */
public class Volunteer extends Person implements Serializable{
	
	
	/**Constructor using fields 
	 * @param name Volunteer name
	 * @param number Volunteer number 
	 */
	public Volunteer(String name,String number) {
		this.setName(name);
		this.setContactNumber(number);
	}
	
	
	/**
	 * Adding new Volunteer to List
	 */
	public void addVolunteer() {
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Volunteer.class);
		
		ArrayList<Volunteer> volunteers;
		volunteers = (ArrayList<Volunteer>)Utility.deserialize(Messages.getString("VOLUNTEER.FileName")); //$NON-NLS-1$
		
		if(volunteers == null) {
			volunteers = new ArrayList<Volunteer>();
			logger.warn(Messages.getString("VOLUNTEER.FileEmpty")); //$NON-NLS-1$
		}
		
		volunteers.add(this);
		Utility.serialize(volunteers, Messages.getString("VOLUNTEER.FileName")); //$NON-NLS-1$
		
		logger.info(Messages.getString("VOLUNTEER.SuccessAdded")); //$NON-NLS-1$
	
	}
	
	
	/**
	 * Removing particular Volunteer from the available list
	 */
	public void removeVolunteer() {
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Volunteer.class);
		
		ArrayList<Volunteer> volunteers;
		volunteers = (ArrayList<Volunteer>)Utility.deserialize(Messages.getString("VOLUNTEER.FileName")); //$NON-NLS-1$
		
		if(volunteers == null) {
			volunteers = new ArrayList<Volunteer>();
			logger.warn(Messages.getString("VOLUNTEER.FileEmpty")); //$NON-NLS-1$
		}
		
		volunteers.remove(this);
		Utility.serialize(volunteers, Messages.getString("VOLUNTEER.FileName")); //$NON-NLS-1$
		
		logger.info(Messages.getString("VOLUNTEER.SuccessRemoved")); //$NON-NLS-1$
	}
	
	/**
	 * Searching the particular Volunteer from the available list
	 */
	public ArrayList<Volunteer> search(String query) {
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Volunteer.class);
		
		ArrayList<Volunteer> volunteers;
		ArrayList<Volunteer> result = new ArrayList<Volunteer>();
		volunteers = (ArrayList<Volunteer>)Utility.deserialize(Messages.getString("VOLUNTEER.FileName")); //$NON-NLS-1$
		
		for(Volunteer volunteer: volunteers) {
		
			if(volunteer.getName().contains(query) ) {
				
				result.add(volunteer);
				logger.info(Messages.getString("VOLUNTEER.Found")); //$NON-NLS-1$
			}
			
		}
		if ( result.isEmpty() )
			logger.warn(Messages.getString("VOLUNTEER.NotFound")); //$NON-NLS-1$
		
		return result;
	}
}
