package edu.institution.actions.asn10;

import edu.institution.asn2.LinkedInUser;

//this class creates a object called a RecentAction
//it has the type of action and the user name of
//the user that is important for the action 
//(like the added connection or a new user)

//it is an enum class to easily set the different types of actions
//similar to how assignment 9 was set up
public enum RecentAction {

	/* ACTION TYPES */
	
	//value for the add connection action
	ADD_CONNECTION("Add Connection"),
	
	//value for the remove connection action
	REMOVE_CONNECTION("Remove Connection"),
	
	//value for the add skill set action
	ADD_SKILLSET("Add Skillset"),
	
	//value for the remove skill set action
	REMOVE_SKILLSET("Remove Skillset"),
	
	//value for the sign up a new user action
	SIGN_UP_USER("Sign Up User"),
	
	//value for the delete user action
	DELETE_USER("Delete User");
	
	//string value to show the type of action
	private String actionType;
	
	//string value to show which user the action affected
	private String importantUsername;
	
	//string value to hold the user's skill set (add/remove skill set actions)
	String skillset;
	
	//LinkedInUser object to store a user when deleting/adding user
	LinkedInUser importantUser = null;
	
	
	//constructor to set the type of action the user just performed
	RecentAction(String actionType){
		this.actionType = actionType;
	}
	
	//this will return the string associated with the action type
	public String displayAction() {
		return this.actionType;
	}

	
	//getter and setter for the important user name String
	public String getUserName() {
		return importantUsername;
	}

	public void setUserName(String importantUsername) {
		this.importantUsername = importantUsername;
	}

	
	
	//getter and setter for skill set string
	public String getSkillset() {
		return skillset;
	}

	public void setSkillset(String skillset) {
		this.skillset = skillset;
	}

	
	
	//getter and setter for the important user object
	public LinkedInUser getImportantUser() {
		return importantUser;
	}

	public void setImportantUser(LinkedInUser importantUser) {
		this.importantUser = importantUser;
	}
	
	
	
	
}
