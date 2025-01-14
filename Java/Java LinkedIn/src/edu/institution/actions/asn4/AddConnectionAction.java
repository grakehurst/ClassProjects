package edu.institution.actions.asn4;

import java.util.*;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.actions.asn10.RecentAction;
import edu.institution.actions.asn10.UndoAction;
import edu.institution.asn2.LinkedInException;
import edu.institution.asn2.LinkedInUser;


//this class adds a connection to the user's list of connections 
//if the user that the logged in user wants to add is already in 
//the list of connections, this catches a LinkedInException
public class AddConnectionAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//ask the logged in user for a user name to add to their connections
		System.out.println("Enter the username of the user you want to connect to");
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
		
		//otherwise check the logged in user's current connections list
		else if (usernameExists == true) {
			
			//create a LinkedInUser object to pass to the addConnections method
			//this is initialized with the index of the correct element from the array of users
			LinkedInUser user = usersList.get(index);
			
			//try to add the user with the addConnections method in the LinkedInUser class
			//catches a LinkedInException if the user is already connected
			try {
				loggedInUser.addConnection(user); //add the connection to this user's connections
				
				//tell the user that the connection was successful
				System.out.println("The connection was added successfully.");
				
				//create a new recent action to add to the action history
				RecentAction action = RecentAction.ADD_CONNECTION; //set to the add connection enum
				
				//set the user name as the input user name
				//this is the important user because it was the user added to the list
				action.setUserName(username);
				
				//now push the action to the history stack in undoAction
				UndoAction.history.push(action);
				
			} catch (LinkedInException e) {
				
				//if the user is already connected, output an error message
				System.out.println(e.getMessage());
			}
		}
		
		//push this action onto the stack of menu actions in UndoAction
		//UndoAction.history.push();
		
		//return true to keep the user logged in
		return true;
	}

}