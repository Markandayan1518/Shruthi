package com.shruthi.sys;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Shruthi
 *
 */
public class Student extends Person {
	
	private String idNum;
	private Member teacher;
	
	
	/**Constructor using Fields
	 * @param name Student's name
	 * @param idNum Student ID number
	 * @param teacher Teacher name
	 * @param contact Student contact number
	 */
	public Student(String name, String idNum, Member teacher,String contact) {
		this.setName(name);
		this.setContactNumber(contact);
		this.idNum = idNum;
		this.teacher = teacher;
	}
	
	
	/**
	 * To register the new Student to the available List 
	 */
	public void register() {
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Student.class);
		
		ArrayList<Student> students;
		students = (ArrayList<Student>)Utility.deserialize(Messages.getString("STUDENT.FileName")); //$NON-NLS-1$
		
		if(students == null) {
			students = new ArrayList<Student>();
			logger.info(Messages.getString("STUDENT.FileEmpty")); //$NON-NLS-1$
		}
		students.add(this);
		Utility.serialize(students, Messages.getString("STUDENT.FileName")); //$NON-NLS-1$
		logger.info(Messages.getString("STUDENT.SuccessReg")); //$NON-NLS-1$
	}
	
	
	/**
	 * @return all Students details in the available List
	 */
	public static ArrayList<Student> getAll() {
		ArrayList<Student> students;
		students = (ArrayList<Student>)Utility.deserialize(Messages.getString("STUDENT.FileName")); //$NON-NLS-1$
		return students;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Name" + this.getName() + "   ID: " + this.idNum + "   Teacher" + teacher.getName(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	
	/** Searching the particular Student details
	 * @param query Student's name
	 * @return particular Student detail
	 */
	public static ArrayList<Student> search(String query) {
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Student.class);
		
		ArrayList<Student> students;
		ArrayList<Student> result = new ArrayList<Student>();
		students = (ArrayList<Student>)Utility.deserialize(Messages.getString("STUDENT.FileName")); //$NON-NLS-1$
		
		for(Student student: students) {
			if(student.getName().contains(query) || student.teacher.getName().contains(query)) {
				result.add(student);
				logger.info(Messages.getString("STUDENT.Found")); //$NON-NLS-1$
			}
		}
		
		if( result.isEmpty() )
			try {
				throw new Exception(Messages.getString("STUDENT.NotFound")); //$NON-NLS-1$
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		return result;
	}
}
