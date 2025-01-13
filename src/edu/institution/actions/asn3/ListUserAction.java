package edu.institution.actions.asn3;

import java.util.*;
import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;

//this class lists all of the users from the LinkedInUsersList 
//located in the SerializedUserRepository
public class ListUserAction implements MenuAction {

	
	//this method is a menu action that displays all the user names 
	//from the LinkedInUsers list 
	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//user repository object variable
		//SerializedUserRepository serializedUserRepository = new SerializedUserRepository();
		
		//create a ArrayList of linkedInUsers and set it to the LinkedInUsersList
		List<LinkedInUser> usersList = new ArrayList<>();
		usersList = userRepository.retrieveAll();
		
		//display the user names from all the users
		for(int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i).getUsername());
		}
		
		//return true in order to keep the user logged in
		return true;
	}
}
