package edu.institution.asn2;

import java.io.Serializable;

public abstract class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	//fields
	private String username; //the account's user name
	private String password; //the password that goes along with that user name
	private String type; //the user's type
	
	//default constructor
	public UserAccount() {
		username = null;
		password = null;	
	}
	
	
	//takes in a user name and password
	public UserAccount(String username, String password) {
		//make sure the user name and password are not null
		if(username != null && password != null) {
			
			//then add the user name and password
			this.username = username;
			this.password = password;
		}
	}
	
	//returns the user name
	public String getUsername() {
		return username;
	}
	
	//check to see if the password is correct (returns true or false)
	public boolean isPasswordCorrect(String password) {
		
		if (password != null) {
			if (this.password.contentEquals(password)) //if the password is right, return true
				return true;
			else //if the password is wrong, return false
				return false;
		}
		else //if the password is null, return false
			return false;
	}
	
	
	//abstract methods for the type getters and setters
	
	//set the LinkedIn user's type
	public abstract void setType(String type); 
	
	//return the LinkedIn user's type
	public abstract String getType();
	
	
	//toString to display the user name and password
	@Override
	public String toString() {
		return "UserAccount [username=" + username + ", password=" + password + "]";
	}

	//hash code for the user name
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	//equals for the user name
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
