package edu.institution.actions.asn4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInException;
import edu.institution.asn2.LinkedInUser;


//this class removes a user from the logged in user's connections list
//if the user is not connected or does not exist, it displays an error
public class RemoveConnectionAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//ask the user for the connection that will be deleted
		System.out.println("Enter the username of the user you want to remove from your connections");
		String username = scanner.nextLine();
		
		//get the list of users in the data
		List<LinkedInUser> usersList = new ArrayList<>();
		usersList = userRepository.retrieveAll();
		
		//see if the user name entered matches a user
		boolean usernameExists = false;
		int index = 0; //index to get the element of the user if they do exist
		for(int i = 0; i < usersList.size(); i++) {
			
			//compare the user name of the passed in user to the user names in the list
			String checkUsername = usersList.get(i).getUsername(); //user name of passed in user
			
			//set the boolean variable to true if it equals a user name in the list
			if(username.equals(checkUsername)) {
				usernameExists = true;
				index = i;
			}
		}
		
		//display an error message if the user name does not exist
		if(usernameExists == false)
			System.out.println("There is no user with that user name");
		
		//otherwise pass the user to the removeConnection method
		//catch an exception if the user is not connected with the input user name
		//otherwise remove the connection from the connections list
		else if (usernameExists == true) {
			
			//create a LinkedInUser object to pass to the removeConnections method
			//this is initialized with the index of the correct element from the array of users
			LinkedInUser user = usersList.get(index);
			
			//try to remove the user with the removeConnections method in the LinkedInUser class
			//catches a LinkedInException if the user is not already connected to the input user
			try {
				loggedInUser.removeConnection(user); //add the connection to this user's connections
				
				//tell the user that the removal was successful
				System.out.println("The connection was removed successfully.");
				
				//add this action to the action history in UndoAction
				RecentAction action = RecentAction.REMOVE_CONNECTION; //set to the remove connection enum
				
				//set the user name as the input user name
				//this is the important user because it was the user removed from the list
				action.setUserName(username);
				
				//push the action to the history stack in UndoAction
				UndoAction.history.push(action);
				
			} catch (LinkedInException e) {
				
				//if the user is already connected, output an error message
				System.out.println(e.getMessage());
			}
		}
		
		

		//return true to keep the user logged in
		return true;
	}

}




























