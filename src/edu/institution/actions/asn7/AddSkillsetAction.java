package edu.institution.actions.asn7;

import java.util.Scanner;

import edu.institution.ApplicationHelper;
import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInUser;

public class AddSkillsetAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//ask the user for the skill set to add
		System.out.println("Enter a skillset to add:");
		String skillset = scanner.nextLine();
		
		//make sure the string is not null
		if(!(skillset == null)) {
			
			//add the skill set to the user's skill sets set
			loggedInUser.addSkillset(skillset);
			
			//increment the skill set count in the application helper
			ApplicationHelper.incrementSkillsetCount(skillset);
			
			//tell the user that the skill set was added
			System.out.println(skillset + " has been added to your skillsets.");
			
			
			//add the action to the action history in UndoAction
			RecentAction action = RecentAction.ADD_SKILLSET; //set the action type to add skill set
			
			//set the skill set to the added skill
			action.setSkillset(skillset);
			
			//push the action onto the stack in UndoAction
			UndoAction.history.push(action);
			
		}

		//return true to keep the user logged in
		return true;
	}

}
