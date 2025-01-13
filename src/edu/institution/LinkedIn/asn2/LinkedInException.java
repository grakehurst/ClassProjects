package edu.institution.asn2;

//this class created a new exception, called LinkedIn Exception, 
//that overrides the Exception class
public class LinkedInException extends Exception {
	private static final long serialVersionUID = 1L;
	
	
	//default constructor
	public LinkedInException() {
		super();
	}
	
	
	//constructor that takes a message
	public LinkedInException(String message) {
		super(message);
	}
	
	
	//constructor that takes a message and a cause for the exception
	public LinkedInException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	//constructor with a certain cause for the exception
	public LinkedInException(Throwable cause) {
		super(cause);
	}
	
	
	//
	protected LinkedInException(String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
