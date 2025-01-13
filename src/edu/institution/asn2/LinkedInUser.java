package edu.institution.asn2;

import java.io.Serializable;
import java.util.*;

//import edu.institution.UserAccount;

public class LinkedInUser extends UserAccount implements Comparable<LinkedInUser>, Serializable {
	private static final long serialVersionUID = 1L;
	
	
	//fields
	private String type;
	private List<LinkedInUser> connections = new ArrayList<>();
	
	//create a set to hold this user's skill sets
	//a tree set orders the items in alphabetical order
	//it also cannot have any duplicates
	private TreeSet<String> skillsets = new TreeSet<String>();
	
	
	//constructor implemented from Comparable
	//this method makes sure the ArrayList sorts the user names alphabetically
	//(ignore capital letters)
	@Override
	public int compareTo(LinkedInUser o) {
		return this.getUsername().compareToIgnoreCase(o.getUsername());
	}
		
	
	//default constructor from the super
	public LinkedInUser(String username, String password) {
		super(username, password);
	}
	
	//set the LinkedIn user's type
	public void setType(String type) { 
		this.type = type;
	}
	
	//return the LinkedIn user's type
	public String getType() {
		return type;
	}
			
		
	/*
	 * adds a connection to the LinkedIn user's connections list
	 * throws an exception is the user is already added to their connections list
	 * */
	public void addConnection(LinkedInUser user) throws LinkedInException {
		
		//test to see if the user is already connected with the added user
		
		//returns a number if the user exists in the connections, negative if it does not
		int index = connections.indexOf(user);
		
		//if else for whether or not the user was found in the array list
		if (index >= 0) { //if the user is already connected
			
			//throw an exception with a message
			throw new LinkedInException("You are already connected with this user.");
				
		} else { //if the user is not already connected
				
			//add the user to the ArrayList of the account's connections
			connections.add(user);
				
			//sort the connections by alphabetical order (ignoring capitalization)
			//happens every time a new connection is added
			Collections.sort(connections);
			
		}	
	}
	
	
	/* 
	 * removed a connection from the user's connection list
	 * throws an exception if the user is not in their connections list
	 * */
	public void removeConnection(LinkedInUser user) throws LinkedInException {
		
		//test to see if the user is already connected with the user being removed
		
		//returns a number if the user exists in the connections, negative if it does not
		int index = connections.indexOf(user);
		
		if (index > -1) { //if the user is connected 
			
			//remove this user from the array list of connections
			connections.remove(index);
			
		} else { //if the user is not connected
			//throw an exception with a message
			throw new LinkedInException("You are NOT connected with this user.");
		}
		
	}
	
	
	/*
	 * this returns a copy of the user's connections list
	 * */
	public List<LinkedInUser> getConnections() {
		
		//create a new array list to copy the connections list into
		List<LinkedInUser> connectionsCopy = new ArrayList<>();
	
		//copy the elements of the connections array into the copy
		connectionsCopy.addAll(connections);
		
		//return the copy of the array list
		return connectionsCopy;
	}
	
	
	
	//return the user's skill sets
	public TreeSet<String> getSkillsets(){
		return skillsets;
	}
	
	
	//pass in a skill set and add it to to TreeSet
	public void addSkillset(String skillset) {
		
		//if the set is null, initialize it
		//if(skillsets == null)
			//skillsets = new TreeSet<>();
		
		//then add the skill set to the set
		skillsets.add(skillset);
	}
	
	
	//remove the passed in skill set if it exists in the TreeSet
	public void removeSkillset(String skillset) {
		
		//if the skill set exists in the TreeSet
		if(skillsets.contains(skillset)) {
			
			//remove it
			skillsets.remove(skillset);
		}
		//otherwise, display a message
		else
			System.out.println(skillset + " is not in your list of skillsets.");
	}
}