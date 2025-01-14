package edu.institution.actions.asn3;

import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInException;
import edu.institution.asn2.LinkedInUser;

//create an account to be added to the LinkedInUser list
public class AddUserAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//variables for the LinkedInUser object
		String username;
		String password;
		String type;
		
		//ask the user for their user name, password, and type
		System.out.println("Enter a username");
		username = scanner.nextLine();
		System.out.println("Enter a password");
		password = scanner.nextLine();
		System.out.println("Enter the type of user (S or P)");
		type = scanner.nextLine();
		
		//create a LinkedInUser and set the user name, password, type
		LinkedInUser newUser = new LinkedInUser(username, password);
		newUser.setType(type);
		
		//pass the user to the add method in the repository
		try {
			userRepository.add(newUser);
			
			//create an action to add to the stack
			RecentAction action = RecentAction.SIGN_UP_USER;
			
			//set the importantUser as the newUser that was just created
			action.setImportantUser(newUser);
			
			//add the action to the history stack in the UndoAction class
			UndoAction.history.push(action);
			
			
		} catch (LinkedInException e) {
			System.out.println(e.getMessage());
		}
		
		//return true to keep the user signed in
		return true;
	}

}
