package edu.institution.actions.asn6;

import java.util.*;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;


//this action displays all the users in alphabetical order, ignoring capitalization 
public class ListUserAlphabeticallyAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//get the users list
		List<LinkedInUser> usersList = userRepository.retrieveAll();
		
		//sort the list
		Collections.sort(usersList);
		
		//display the list of users
		for (int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i).getUsername());
		}
		
		//return true to keep the user logged in
		return true;
	}

}
