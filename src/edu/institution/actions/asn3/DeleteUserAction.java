package edu.institution.actions.asn3;

import java.util.*;
import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInUser;

//this class deletes a user from the repository
public class DeleteUserAction  implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//variable for the user name from the account to be deleted
		String username;
		
		//ask the user which user name needs to be deleted
		System.out.println("Username to delete");
		username = scanner.nextLine();
		
		//get the list of users in the data
		List<LinkedInUser> usersList = new ArrayList<>();
		usersList = userRepository.retrieveAll();
		
		//see if the user name entered matches a user
		boolean usernameExists = false;
		int index = 0;
		for(int i = 0; i < usersList.size(); i++) {
			
			//compare the user name of the passed in user to the user names in the list
			String checkUsername = usersList.get(i).getUsername(); //user name of passed in user
			
			//set the boolean variable to true if it equals a user name in the list
			if(username.equals(checkUsername)) {
				usernameExists = true;
				index = i;
			}
			
		}
		
		//this nested if/else determines several things:
		//if the user name exists,
		//if the logged in user enters the right password to delete the account,
		//and whether or not they just deleted their own account
		
		//if they did delete their own account, then they are signed out
		//otherwise they stay logged in and are sent back to the menu
		
		
		//if the user name does exist
		if(usernameExists == true) {
			
			//ask the user for the password
			System.out.println("Password");
			String password = scanner.nextLine();
			
			//check to see if the password is correct
			//this tests the input password with the isPasswordCorrect method from LinkedInUser
			boolean isPasswordCorrect = usersList.get(index).isPasswordCorrect(password);
			
			if(isPasswordCorrect == true) { //if the password is correct
				 
				
				//delete the user from the repository
				LinkedInUser user = usersList.get(index); //save the user
				userRepository.delete(user); //delete that user
				
				//create an action to add to the stack
				RecentAction action = RecentAction.DELETE_USER;
				
				//set the importantUser as the newUser that was just created
				action.setImportantUser(user);
				
				//add the action to the history stack in the UndoAction class
				UndoAction.history.push(action);
				
				//check if the user deleted was the logged in user
				if(user.getUsername() == loggedInUser.getUsername()) { 
					return false; //log out if logged in account was deleted
					
				} else { //otherwise stay logged in
					return true;
				}
				
			} else { //if the password is incorrect
				System.out.println("Incorrect Password");
				return true;
			}
			
		} else { //if the user name does not exist
			System.out.println("The entered user does not exist");
			return true;
		}
	}

}




























