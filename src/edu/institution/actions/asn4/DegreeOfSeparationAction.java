package edu.institution.actions.asn4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;


//this class outputs the degree of separation between the logged in user
//and another user through connections
public class DegreeOfSeparationAction implements MenuAction {

	//this field is for the user name that the logged in user enters
	//this will be the user name that this class looks for to 
	//create the degree of separation output
	LinkedInUser enteredUsername;
	
	//this field is an array list of users that have already been checked 
	//by the degree of separation method
	//should be initialized to 0 each time to the process method is called
	List<LinkedInUser> alreadyChecked;
	
	//this method takes in a user name and checks that that user exists
	//if they do, the user is sent to the next method to get the degree of separation
	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//Initialize the already checked field to reset the list
		alreadyChecked = new ArrayList<>();
		
		//ask the user for the user name that they are looking for the degree of separation of
		System.out.println("Enter a username");
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
		
		//if the user name exists, set the user field to that user
		//and send the logged in user to the degree of separation method
		if(usernameExists == true) {
			enteredUsername = usersList.get(index);
			
			//get the logged in user's connections list
			List<LinkedInUser> connections = loggedInUser.getConnections();
			
			//make sure the user is not already connected to the entered user name
			if (connections.contains(usersList.get(index))) {
				
				//if they are connected:
				System.out.println("There are 0 degrees of separation between you and " + enteredUsername.getUsername());
				System.out.println(loggedInUser.getUsername() + " -> " + enteredUsername.getUsername());
			}
			
			//otherwise, call the degree of separation method
			else {
				
				//make an array list to retrieve the user names of the degree of separation
				List<LinkedInUser> degreeOfSeparationList = new ArrayList<>();
				
				//send the logged in user and array list, return the array list of users
				degreeOfSeparationList = degreeOfSeparation(loggedInUser, degreeOfSeparationList);
				
				//then output the degree of separation and the users in the list
				
				//if there is no path to the entered user:
				if(degreeOfSeparationList.size() == 0) {
					System.out.println("There are 0 degrees of separation between you and " + enteredUsername.getUsername());
					//System.out.println("size = 0"); //test
				}
				
				//otherwise, output the path
				else {
					
					//get the size of the array
					//subtract two because:
					//the user we are looking for does not count in the degree of separation number and
					//the logged in user does not count in the degree of separation number
					int size = degreeOfSeparationList.size() - 2; 
					
					//output the size of the degree of separation
					System.out.println("There are " + size + " degrees of separation between you and " + enteredUsername.getUsername());
					
					//then output all the user names that are in the array
					//the list is backwards, so output the names in reverse order
					for (int i = degreeOfSeparationList.size() - 1; i > -1; i--) {
						
						System.out.print(degreeOfSeparationList.get(i).getUsername());
						
						//if it is not the first element in the array (last time in the loop)
						//add an arrow in between the names
						if(i > 0)
							System.out.print(" -> ");
						
					}
					System.out.println(); //add an extra space before the next menu pops up
				}			
			}
		} 
		//otherwise, display an error message
		else
			System.out.println("Username does not exist");

		//return true to keep the user logged in
		return true;
	}

	
	//this method gets the degree of separation
	//it takes in a user and checks their connections list for the entered user name
	//it also takes in an array list to hold all the users in the degree of separation
	public List<LinkedInUser> degreeOfSeparation(LinkedInUser user, List<LinkedInUser> degreeOfSeparation) {
		
		//base case 1:
		//if the user entered has already been checked, return the array list
		if(alreadyChecked.contains(user)) {
			//degreeOfSeparation = new ArrayList<>();
			return degreeOfSeparation;
		} else {
			
			//add the passed in user to the already checked list
			alreadyChecked.add(user);
			
			//get the user's list of connections
			List<LinkedInUser> connections = user.getConnections();
			
			//base case 2:
			//if the user entered has the user name that we are looking for
			if(user.getUsername().equals(enteredUsername.getUsername())) {
				
				//add that user to the array list and return it
				degreeOfSeparation.add(user);
				return degreeOfSeparation;
			}
			
			//base case 3:
			//the user does not have any connections in their list
			else if(connections.size() == 0) {
				//return the list
				return degreeOfSeparation;
			}
			
			//otherwise look through the connections list to try to find the entered user name
			else {
				
				//loop to check the user's connections
				for(int i = 0; i < connections.size(); i++) {
					
					//get the next connection
					LinkedInUser nextConnection = connections.get(i);
					
					//RECURSION:
					//send that connection and the array list to this same method
					//returns the array list of separation
					List<LinkedInUser> checkDegreeOfSeparation = degreeOfSeparation(nextConnection, degreeOfSeparation);
					
					
					//check to see if the user we are looking for is in the new array list
					//if they are, add the current user and return the correct array list
					if(checkDegreeOfSeparation.contains(enteredUsername)) {
						degreeOfSeparation.add(user);
						return degreeOfSeparation;
					}
						
				}
				
				//return a blank array list if the entered user was not found
				return new ArrayList<>();
			}
		}
	}
}

































