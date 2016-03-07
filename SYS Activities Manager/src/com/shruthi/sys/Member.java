package com.shruthi.sys;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Shruthi
 *
 */
public class Member extends Person implements Serializable{
	
	private String userName;
	private String password;
	private Designation designation;
	
	
	/**Constructor using fields
	 * @param userName user name
	 * @param password user password
	 * @param designation user designation 
	 */
	public Member(String userName, String password, Designation designation) {
		this.userName= userName;
		this.password = sha256(password);
		this.designation = designation;
		
	}
	
	/**
	 * Takes a string, hashes it using SHA-256 and then returns a hex string.
	 * @param base
	 * @return hex string
	 */
	private static String sha256(String base) {
	        try{
	            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //$NON-NLS-1$
	            byte[] hash = digest.digest(base.getBytes("UTF-8")); //$NON-NLS-1$
	            StringBuffer hexString = new StringBuffer();

	            for (int i = 0; i < hash.length; i++) {
	                String hex = Integer.toHexString(0xff & hash[i]);
	                if(hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }

	        return hexString.toString();
	    } catch(Exception exception){
	    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
			Logger logger = Logger.getLogger(Member.class);
			logger.error(exception.getMessage(), exception);
	       throw new RuntimeException(exception);
	    }
	}
	
	/**
	 * Use this method to register a member and returns true if successful. Adds the current object to an ArrayList and serializes it.
	 * @return true if valid
	 */
	@SuppressWarnings("unchecked")
	public boolean register() throws Exception {
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Member.class);
		
		ArrayList<Member> members;
		members = (ArrayList<Member>)Utility.deserialize(Messages.getString("MEMBER.4")); //$NON-NLS-1$
	
		if(members == null) {
			members = new ArrayList<Member>();
		
		}
		
		try {
			if(this.checkUserNameExists(members)== false) {
				members.add(this); 
				Utility.serialize(members, "members.ser"); //$NON-NLS-1$
					
			}
			
			if ( members.isEmpty() )
				throw new Exception(Messages.getString("MEMBER.InvalidReg")); //$NON-NLS-1$
			
			logger.info(Messages.getString("MEMBER.SuccessReg")); //$NON-NLS-1$
			
		} catch (Exception exception) {
			
			logger.error(exception.getMessage(), exception);
			throw exception;
		
		}
		
		return true;
				
	}
	
	
	/**
	 * Getting the authenticate
	 * @param userName
	 * @param password
	 * @return true if valid
	 */
	public static boolean authenticate(String userName, String password) {
		
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Member.class);
		
		ArrayList<Member> members;
		password = sha256(password);
		members = (ArrayList<Member>)Utility.deserialize("members.ser"); //$NON-NLS-1$
		
		if(members == null) {
			logger.warn(Messages.getString("MEMBER.FileEmpty")); //$NON-NLS-1$
			return false;
		}
		
		for(Member member:members) {
			if(member.userName.equals(userName) && member.password.equals(password)) {
				logger.info(Messages.getString("MEMBER.AuthenticationSuccess")); //$NON-NLS-1$
				return true;
			}
		}
		logger.warn(Messages.getString("MEMBER.AuthenticationFailure")); //$NON-NLS-1$
		return false;
	}
	
	
	/**
	 * Checks if the user name already exists; returns true if it does
	 * @param members
	 * @return true if valid
	 */
	private boolean checkUserNameExists(ArrayList<Member> members) throws Exception {
		
		for(Member member:members) {
			
			if(this.userName.equals(member.userName)){
			
				return true;
			
			}
			
		}
		return false;
	}
	
	
	
	/**Search the particular Member Name from the List
	 * @param query Member Name
	 * @return Member details
	 */
	public static ArrayList<Member> search(String query) {
		ArrayList<Member> members;
		ArrayList<Member> result = new ArrayList<Member>();
		members = (ArrayList<Member>)Utility.deserialize("members.ser"); //$NON-NLS-1$
		for(Member member: members) {
		
			if(member.userName.contains(query) || member.getName().contains(query)) {
				
				result.add(member);
				
			}
			
		}
		return result;
	}
	
}
