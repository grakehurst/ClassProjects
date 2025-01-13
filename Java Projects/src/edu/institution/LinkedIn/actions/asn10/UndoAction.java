package edu.institution.actions.asn10;

import java.util.*;

import edu.institution.ApplicationHelper;
import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInException;
import edu.institution.asn2.LinkedInUser;

//this class undoes the last action that was performed by the user
public class UndoAction implements MenuAction {

	//stack variable to hold all the recent actions
	//recent action is a custom class in this same package
	public static Stack<RecentAction> history = new Stack<RecentAction>();
	
	
	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//check if the stack is empty
		//if it is, display an error message
		//this should be empty each time the program starts
		if(history.size() > 0) {
			
			//get the most recent action and the user name and user object attached to it
			String actionType = history.lastElement().displayAction();
			String importantUsername = history.lastElement().getUserName();
			LinkedInUser importantUser = history.lastElement().getImportantUser();
			
			int answer; //the user's input answer (Y or N)
			
			//switch statement to decide what to do with the action
			switch(actionType) {
			
			//undo the add connection
			case ("Add Connection"): 
				
				//display the starting output (using the logged in user's user name)
				loggedInUserDisplay(loggedInUser, actionType);
				
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//undo the action
					undoAddConnection(loggedInUser, importantUsername);
					
				}
				
				break;
				
				
			//undo the remove connection
			case("Remove Connection"): 
				
				//display the starting output (using the logged in user's user name)
				loggedInUserDisplay(loggedInUser, actionType);
				
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//undo the action
					undoRemoveConnection(loggedInUser, importantUsername, userRepository);
					
				}
				
				break; 
				
			case("Add Skillset"):
				
				//display the starting output(using the logged in user's user name)
				loggedInUserDisplay(loggedInUser, actionType);
			
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//get the skill set attached to this action
					String skillset = history.lastElement().getSkillset();
					
					//undo the action (pass the user, and the recently added skill set)
					undoAddSkillset(loggedInUser, skillset);
				}
				
				break;
				
			case("Remove Skillset"):
				
				//display the starting output(using the logged in user's user name)
				loggedInUserDisplay(loggedInUser, actionType);
			
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//get the skill set attached to this action
					String skillset = history.lastElement().getSkillset();
					
					//undo the action (pass the user, and the recently added skill set)
					undoRemoveSkillset(loggedInUser, skillset);
				}
			
				break;
				
			case("Sign Up User"):
				
				//display starting output (using the user name of the account that was just signed up)
				usernameDisplay(importantUser.getUsername(), actionType);
			
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//undo the action(pass the important user name and the user repository)
					undoSignUpUser(importantUser.getUsername(), userRepository);
				}
				
				break;
			
			case("Delete User"):
				
				//display starting output (using the user name of the account that was just signed up)
				usernameDisplay(importantUser.getUsername(), actionType);
				
				//get the user's input
				answer = checkUserInput(scanner);
				
				//if the answer value is greater than 0, undo the action
				if(answer > 0) {
					
					//undo the action(pass the important user  and the user repository)
					undoDeleteUser(importantUser, userRepository);
				}
			
				break;
			
			//default case if the actionType is something crazy
			default: System.out.println("Action was not undone."); break;
		
			}
		}
		
		//otherwise display the error message
		else
			System.out.println("There are no actions to undo.");
		
		//return true to keep the user logged in
		return true;
	}

	
	/* DISPLAY LAST MENU OPTION TEXT */
	//these methods happen at the beginning of the class
	//there are two of them because there are 2 different scenarios
	//for the starting sysout statement:
	//involved user name == logged in user OR in involved user name == another user name
	
	//method that use the logged in user's user name as the involved user
	//takes in the logged in user and the type of action to be undone
	//used for: addConnection, removeConnection, addSkillset, removeSkillset
	public void loggedInUserDisplay(LinkedInUser loggedInUser, String actionType) {
		
		//display the last menu option and ask the user if they want to undo
		System.out.println("The last menu option selected was \"" + actionType 
				+ "\" involving " + loggedInUser.getUsername() + ". Undo? (Y/N)");
		
	}
	
	//method that displays a different user's name as the involved user
	//takes in a string of the user name and the type of action to be undone
	//used for: addUser, deleteUser
	public void usernameDisplay(String username, String actionType) {
		
		//display the last menu option and ask the user if they want to undo
		System.out.println("The last menu option selected was \"" + actionType 
				+ "\" involving " + username + ". Undo? (Y/N)");
	}
	
	
	//method to check the user's input
	//takes in a string, returns a value > 0 if the answer is yes
	public int checkUserInput(Scanner scanner) {
		
		//get the user's input
		String answer = scanner.nextLine();
		
		//if the answer was Y
		if(answer.equals("Y") || answer.equals("y")) {
			//return a value greater than 0
			return 1;
		}
		//if the answer is N tell the user
		else if(answer.equals("N") || answer.equals("n")) {
			
			//tell the user the action was not undone
			System.out.println("Action was not undone.");
			
			//return 0
			return 0;
		}
		
		//otherwise, the answer is not recognizable, so display an error message
		else {
			System.out.println("Answer must be Y or N.");
			
			//return 0
			return 0;
		}
		
		
	}
	
	
	/* METHODS TO UNDO THE RECENT ACTION */
	
	//undo addConnection action
	public void undoAddConnection(LinkedInUser loggedInUser, String importantUsername) {
		
		//the important user in this case is the logged in user
		//get this user's connections list
		List<LinkedInUser> connections = loggedInUser.getConnections();
		
		//get the last connection that was added to the list
		LinkedInUser lastConnection = null;
		
		//find the right user
		for(int i = 0; i < connections.size(); i++) {
			
			//get the next user name
			String nextUsername = connections.get(i).getUsername();
			
			//set the last connection to that user name
			//this will be the user that will be removed from the list
			if(nextUsername.equals(importantUsername))
				lastConnection = connections.get(i);
		}
		
		
		//make sure the connection is not a null value
		if(lastConnection != null) {
			//remove the most recent connection
			try {
				loggedInUser.removeConnection(lastConnection);
				
				//tell the user the action was done
				System.out.println("Action was undone.");
				
				//pop this item off the stack
				history.pop();
				
			} catch (LinkedInException e) {
				System.out.println("You are not connected with this user.");
			}
		}
	}
	
	
	//undo removeConnection action
	public void undoRemoveConnection(LinkedInUser loggedInUser, String importantUsername, UserRepository userRepository) {
		
		//get the list of users
		List<LinkedInUser> usersList = userRepository.retrieveAll();
		
		//get the connection that was just removed from the list
		LinkedInUser removedConnection = null;
		
		//find the right user
		for(int i = 0; i < usersList.size(); i++) {
			
			//get the next user name
			String nextUsername = usersList.get(i).getUsername();
			
			//set the last connection to that user name
			//this will be the user that will be added back to the list
				if(nextUsername.equals(importantUsername))
					removedConnection = usersList.get(i);
		}
		
		//make sure the removed user variable is not null
		//then add the removed connection back to the list
		if(removedConnection != null) {
			
			//try to add the user back to the connections list
			try {
				loggedInUser.addConnection(removedConnection);
				
				//tell the user the action was done
				System.out.println("Action was undone.");
				
				//pop this item off the stack
				history.pop();
				
			} catch (LinkedInException e) {
				System.out.println("You are already connected to this user.");
			}
		}
}
	
	
	/* EXTRA CREDIT */
	
	//undo the add skill set action
	public void undoAddSkillset(LinkedInUser loggedInUser, String skillset) {
		
		//remove the skill from the logged in user's skill sets 
		loggedInUser.removeSkillset(skillset);
		
		//increment the skill set count in the application helper
		ApplicationHelper.decrementSkillsetCount(skillset);
		
		System.out.println("Action was undone.");
		
		//pop this item off the stack
		history.pop();
		
	}
	
	
	//undo the remove skill set action
	public void undoRemoveSkillset(LinkedInUser loggedInUser, String skillset) {
		
		//remove the skill from the logged in user's skill sets 
		loggedInUser.addSkillset(skillset);
		
		//increment the skill set count in the application helper
		ApplicationHelper.incrementSkillsetCount(skillset);
		
		System.out.println("Action was undone.");
		
		//pop this item off the stack
		history.pop();
	}
	
	
	//undo the add user action
	public void undoSignUpUser(String username, UserRepository userRepository) {
		
		//get the user with that user name
		LinkedInUser user = userRepository.retrieve(username);
		
		//delete the user
		userRepository.delete(user);
		
		System.out.println("Action was undone.");
		
		//pop the action off of the stack
		history.pop();
	}
	
	
	//undo the add user action
	public void undoDeleteUser(LinkedInUser user, UserRepository userRepository) {
		
		//add the user back to the user repository
		try {
			userRepository.add(user);
		} catch (LinkedInException e) {
			System.out.println("Could not add the user.");
		}
		
		System.out.println("Action was undone.");
		
		//pop the action off of the stack
		history.pop();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
