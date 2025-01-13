package edu.institution.actions.asn7;

import java.util.*;

import edu.institution.ApplicationHelper;
import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;


//this method displays the current user's skill sets
//along with how many users also have the particular skill set
public class ListSkillsetAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//get this user's list of skill sets
		TreeSet<String> skillset = loggedInUser.getSkillsets();
		
		//make sure the list is not null
		if(skillset.size() > 0) {
			
			System.out.println("Here are your skillsets");
			
			//create a list and pass all the strings from the set into it
			//this will make it possible to step through each skill set in the loop
			List<String> skillsetList = new ArrayList<>();
			skillsetList.addAll(skillset);
			
			//display the skill sets and the number of people that also have that skill set
			for(int i = 0; i < skillset.size(); i++) {
				//get the next string in the list
				String nextSkillset = skillsetList.get(i);
				
				//display the skill set and the number of users that have that skill set
				System.out.println(nextSkillset + " (" + ApplicationHelper.retrieveSkillsetCount(nextSkillset) + " users)");
				
			}
		}
		//if it is null, tell the user that they have no skill sets
		else
			System.out.println("You have not entered any skillsets.");

		//return true to keep the user logged in
		return true;
	}

}
