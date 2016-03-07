package com.shruthi.sys;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.shruthi.sys.resources.AppConfig;

/**
 * @author Shruthi
 */

public class Funds implements Serializable {
    private double fund;
    
    /**
     * Default Constructor
     */
    Funds() {
        this.fund=0.0;
    }
   
    
    /** To set Fund
     * @param amt Fund amount
     */
    public void setFund(double amt) {
        this.fund=amt;
        
    }
    
    
    /**Deducting amount from the available Fund amount
     * @param Funddeduct Fund deduct amount 
     * @return false when fund goes to negative value after the deduct
     */
    public boolean deduct(double Funddeduct) {   
    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(this.getClass());
    	
		this.fund -= Funddeduct;
        
        if( this.fund < 0 ) { 
        	System.out.println("Cannot perform transaction Funds empty");
        	logger.warn(AppConfig.PROPERTIES.getProperty("FUNDS.DeductFAILURE"));
            this.fund+=Funddeduct;
            return false;
        }
        logger.info(AppConfig.PROPERTIES.getProperty("FUNDS.DeductSUCCESS"));
        return true;
    }
    
    /**Adding  amount to the available Fund amount
     * @param newfund new fund amount
     */
    public void add(double newfund) {
    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml");
		Logger logger = Logger.getLogger(this.getClass());
        this.fund += newfund;
        logger.info("Successfully Adding  amount to the available Fund amount ");
    }
}
