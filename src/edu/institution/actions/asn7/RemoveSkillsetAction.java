package edu.institution.actions.asn7;

import java.util.*;

import edu.institution.ApplicationHelper;
import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInUser;

public class RemoveSkillsetAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//ask the user for the skill set to delete
		System.out.println("Enter a skillset to delete:");
		String skillset = scanner.nextLine();
		
		//make sure the skill set is not null
		if(!(skillset == null)) {
			
			//get the user's skill set
			TreeSet<String> userSkillset = loggedInUser.getSkillsets();
			
			//check if the skill set entered is added to their set
			if(userSkillset.contains(skillset)) {
				
				//remove the skill set
				loggedInUser.removeSkillset(skillset);
				
				//tell the user that it was removed
				System.out.println(skillset + " was removed.");
				
				//decrement the skill set count
				ApplicationHelper.decrementSkillsetCount(skillset);
				
				//add this action to the history stack in UndoAction
				//add the action to the action history in UndoAction
				RecentAction action = RecentAction.REMOVE_SKILLSET; //set the action type to remove skill set
				
				//set the skill set to the added skill
				action.setSkillset(skillset);
				
				//push the action onto the stack in UndoAction
				UndoAction.history.push(action);
				
			}
			//otherwise display an error message
			else
				System.out.println(skillset + " is not in your skillset.");
			
		}
		
		//return true to keep the user logged in
		return true;
	}

}
