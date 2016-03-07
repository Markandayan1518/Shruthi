package com.shruthi.sys;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Shruthi
 */

public class Transaction implements Serializable {
	
    private String userName;
    private String reason;
    private double amount;
    private static Funds funds;
    private boolean approved;
    
	/**Constructor using fields
	 * @param userName user name
	 * @param reason reason	
	 * @param amount amount
	 */
	public Transaction(String userName, String reason, double amount) {
    	this.userName = userName;
        this.reason = reason;
        this.amount = amount;
        this.approved = false;
    }
    
	
    /** Modify the available Fund amount
     * @param Funddeduct deduct amount
     */
    private void modifyFunds(double Funddeduct) {
    	
    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Transaction.class);
		
    	boolean done;
        if(this.approved) {
        	done = funds.deduct(Funddeduct);
            if(!done) {
            	logger.warn(Messages.getString("TRANSACTION.NotModify")); //$NON-NLS-1$
                //System.out.println("Transaction cant modify funds disapproving it");
                this.approved = false;
            }
        }
    }
    
    
	/**Getting approve for the Transaction
	 * @param T Transaction details
	 */
	public static void ApproveTransaction(Transaction T) {
		DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Transaction.class);
		
    	T.approved = true;
    	
    	logger.info(Messages.getString("TRANSACTION.Approved")); //$NON-NLS-1$
	}
    
   
    /**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the funds
	 */
	public static Funds getFunds() {
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public static void setFunds(Funds funds) {
		Transaction.funds = funds;
	}

	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	
	/**Checking approval for the Transaction
	 * @return approval status
	 */
	public boolean checkapproval() {
        return this.approved;
    }
    
	
	
    /**To registering for the Transaction
     * @return true if vaild
     */
    public boolean registertransaction() {
    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Transaction.class);
        
    	ArrayList<Transaction> trans = new ArrayList<Transaction>();
        trans = (ArrayList<Transaction>)Utility.deserialize(Messages.getString("TRANSACTION.FileName")); //$NON-NLS-1$
        
        if(trans==null) {
        	trans = new ArrayList<Transaction>();
        	logger.warn(Messages.getString("TRANSACTION.FileEmpty")); //$NON-NLS-1$
        }
        
        try {
        	trans.add(this);
        	Utility.serialize(trans, Messages.getString("TRANSACTION.FileName")); //$NON-NLS-1$
        	logger.info(Messages.getString("TRANSACTION.SuccessReg")); //$NON-NLS-1$
        	return true;
        }
        catch(Exception e) {
        	logger.error(e.getMessage(), e);
            return false;
        }
        
    }
    
    
    
    /**Getting List of Pending Transaction
     * @return  pendingTransactionList
     */
    public static ArrayList<Transaction> getPending() {
    	DOMConfigurator.configure("src/com/shruthi/nirmaan/resources/log4j.xml"); //$NON-NLS-1$
		Logger logger = Logger.getLogger(Transaction.class);
		
    	ArrayList<Transaction> trans = new ArrayList<Transaction>();
        trans = (ArrayList<Transaction>)Utility.deserialize(Messages.getString("TRANSACTION.FileName")); //$NON-NLS-1$
        ArrayList<Transaction> temp = new ArrayList<Transaction>();
        
        try {
        	for(Transaction tr : trans) {   
        		if(!tr.checkapproval()) {
        			temp.add(tr);
        		}
        	}
        } catch ( NullPointerException e) {
        	logger.error(e.getMessage(), e);
        }
        if( temp.isEmpty() )
        	logger.info(Messages.getString("TRANSACTION.NoPending")); //$NON-NLS-1$
        return temp;
    }
}
