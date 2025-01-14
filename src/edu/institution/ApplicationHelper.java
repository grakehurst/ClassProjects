/*
 Copyright (C) 2020. Doug Estep -- All Rights Reserved.
 Copyright Registration Number: TXU002159309.

 This file is part of the Tag My Code application.

 This application is protected under copyright laws and cannot be used, distributed, or copied without prior written
 consent from Doug Estep.  Unauthorized distribution or use is strictly prohibited and punishable by domestic and
 international law.

 Proprietary and confidential.
 */
package edu.institution;

import java.util.*;
import edu.institution.asn2.LinkedInUser;


/**
 * Contains static helper methods to aid with the command line user interface.
 */
public class ApplicationHelper {

	
	//create a hashMap for all of the skill sets and how many users have that skill set
	
	
	/* EXPLANATION: 
	 * 
	 * A hashMap can hold a key and value that relates to the value.
	 * So, the key will be the different skill sets that are each unique
	 * and the value that relates to the key will be a number that
	 * shows how many users have that particular skill set.
	 * 
	 * This will make it easy to find a certain skill set later,
	 * so the increment and decrement skill set methods will be easier.
	 * 
	 * It is also made static so that it does not just reference one instance,
	 * but rather it is a global variable
	 * 
	 * */
	private static HashMap<String, Integer> skillsets = new HashMap<String, Integer>();
	
	
	/**
	 * Displays the supplied message to the console.
	 * 
	 * @param message the message.
	 */
	public static void showMessage(String message) {
		System.out.println("\n" + message);
	}
	
	
	/* this method increments the amount of one particular skill set
	 * that is in the skill set hashMap
	 * if the skill set is not in the hash map, it will be added to the hash map
	 * */
	public static void incrementSkillsetCount(String skillset) {
		
		//make an Integer variable to keep track of all the instances of this skill
		Integer usersWithSkillset = 0;
		
		//look for the skill in the hash map
		if(skillsets.containsKey(skillset)) {
			//if it's there, add one to the value to show that one more user has this skill set
			
			//get the integer value
			usersWithSkillset = skillsets.get(skillset);
			
			//add one to the Integer and put it into the hash map
			skillsets.put(skillset, usersWithSkillset + 1); 
			
		}
		//if it does not already exist, in the skill set hashMap
		else {
			
			//add it to the hash map with a Integer of 1 for one user
			usersWithSkillset = 1;
			skillsets.put(skillset, usersWithSkillset);
		}
	}
	
	
	/* this method decrements the number of users associated with a supplied skill set 
	 * if the number of users with the passed skill set is changed to 0, the skill set will be removed 
	 * if the skill set is not in the hash map, an error message is displayed */
	public static void decrementSkillsetCount(String skillset) {
		
		//make an Integer variable to keep track of all the instances of this skill
		Integer usersWithSkillset = 0;
		
		//look for the skill in the hash map
		if(skillsets.containsKey(skillset)) {
			//if it's there, subtract one from the value to show that one less user has this skill set
			
			//get the integer value
			usersWithSkillset = skillsets.get(skillset);
			
			//subtract one to the Integer and put it into the hash map
			usersWithSkillset--;
			skillsets.put(skillset, usersWithSkillset); 
			
			//if the amount of users with that skill set is 0, remove it from the hash map
			if(usersWithSkillset < 1) {
				
				//remove the skill set
				skillsets.remove(skillset);
			}
		}
		//otherwise, display an error message
		else
			System.out.println("This skillset does not exist.");
			
	}
	
	
	/* this method returns the number of users who have the passed skill set 
	 * returns -1 if the skill set does not exist */
	public static int retrieveSkillsetCount(String skillset) {
		
		//make an Integer variable to keep track of all the instances of this skill
		int usersWithSkillset = 0;
		
		if(skillsets.containsKey(skillset)) {
			//set the usersWithSkillset to the value attached to the key
			usersWithSkillset = skillsets.get(skillset);
		}
		//if it does not exist, set the value to -1
		else
			usersWithSkillset = -1;
		
		//return the number of users with that skill set
		return usersWithSkillset;
	}
	
	
	/* this method takes all the LinkedInUsers's skill sets and 
	 * adds them to the hash map at the beginning of the program */
	public static void initSkillsetUsages(List<LinkedInUser> users) {
		
		//loop through each of the users in the list
		for (int i = 0; i < users.size(); i++) {
			
			//create a tree set variable to add all the skill sets to
			//the set will not have duplicates, so there is no need to check later on
			TreeSet<String> keySet = new TreeSet<String>();
			
			//make sure the user has skill sets; if not don't do anything
			if(users.get(i).getSkillsets() != null) {
				
				//get the next user's skill set list, add it to the keySet
				keySet.addAll(users.get(i).getSkillsets());
				
				//get the size of the key set
				//this is used instead of the keyset.size 
				//because that size will change
				int size = keySet.size();
				
				//loop through each skill set from this user
				for (int j = 0; j < size; j++) {
			
					//make sure the set is not empty; if it is, do nothing with it
					if(keySet.size() > 0) {
					
						//get the next string in the key set
						//this will also remove the first element
						String key = keySet.pollFirst();
						
						//if the key is already in the hash map, add one to the value
						if(skillsets.containsKey(key)) {
							//get the value associated with the key
							int usersWithSkillset = skillsets.get(key);
							
							//add one to the value
							usersWithSkillset++;
							
							//put it back in the set
							skillsets.put(key, usersWithSkillset);
						}
						//otherwise, add a new key to the hash map with a value of one user
						else
							skillsets.put(key, 1);
					}
					
				}
			}
		}
	}

}





































