package com.shruthi.sys;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Shruthi
 *
 */
public class Utility {
	
	
	/**Open the particular file using file name and get the information form that file
	 * @param name file name
	 * @return file informations
	 */
	public static Object deserialize(String name)  {
		
		try {
			FileInputStream fileIn = new FileInputStream(name);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object object = in.readObject();
			in.close();
			fileIn.close();
			return object; 
		}
		catch(Exception i){
			i.printStackTrace();
	        return null;
	        
	        
	    }
	}
	
	/**Open the particular file using file name and put the information form that file
	 * @param name file name
	 */
	public static void serialize(Object object, String name) {
	
		try {
			
	         FileOutputStream fileOut = new FileOutputStream(name);
	           ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(object);
	         out.close();
	         fileOut.close();
	   
	      }catch(Exception i)
	      {
	          i.printStackTrace();
	      }
	}
}
