package edu.institution.actions.asn6;

import java.util.*;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;


//this class lists the users first by type (P or S)
//and then alphabetically by user name
public class ListUserByTypeAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//get the users list
		List<LinkedInUser> usersList = userRepository.retrieveAll();
		
		//sort the list alphabetically
		Collections.sort(usersList);
		
		//create two arrays
		//one for all the type P users
		//one for all the type S users
		List<LinkedInUser> pTypeUsers = new ArrayList<>();
		List<LinkedInUser> sTypeUsers = new ArrayList<>();
		
		//sort through the list of users
		//add the users to the right array list based off of their type
		for (int i = 0; i < usersList.size(); i++) {
			
			//if the type is P
			if(usersList.get(i).getType().equals("P"))
				pTypeUsers.add(usersList.get(i)); //add to the P list
			//if the type is S
			else
				sTypeUsers.add(usersList.get(i)); //add to the S list
		}
		
		//display the P users first
		for (int i = 0; i < pTypeUsers.size(); i++) {
			
			System.out.println(pTypeUsers.get(i).getUsername() + "; type = " + 
					pTypeUsers.get(i).getType());
		}
		
		//then display the S users
		for (int i = 0; i < sTypeUsers.size(); i++) {
			
			System.out.println(sTypeUsers.get(i).getUsername() + "; type = " + 
					sTypeUsers.get(i).getType());
		}

		//return true to keep the user logged in
		return true;
	}

}