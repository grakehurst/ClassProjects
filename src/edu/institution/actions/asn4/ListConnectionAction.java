package edu.institution.actions.asn4;

import java.util.*;
import java.util.Scanner;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;

public class ListConnectionAction implements MenuAction {

	//this method lists all the users that the 
	//logged in user is connected to
	//(connections ArrayList from the LinkedInUser class)
	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {
		
		//get an ArrayList of the logged in user's connections
		List<LinkedInUser> connections = loggedInUser.getConnections();
		
		//if there are no users in the connections list
		if(connections.size() == 0)
			System.out.println("You have no connections");
		
		//otherwise, display all of the connections
		else {
			
			//output each connection that the user has
			//for each connection
			for(int i = 0; i < connections.size(); i++) {
				 	
				//output the user name of the user 
				System.out.println(connections.get(i).getUsername());
			
			}
		}
		//return true to keep the user logged in
		return true;
	}

}
