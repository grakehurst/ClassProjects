package edu.institution.actions.asn6;

import java.util.*;

import edu.institution.UserRepository;
import edu.institution.actions.MenuAction;
import edu.institution.asn2.LinkedInUser;


//this method sorts the list of users by the number of connections that they have
public class ListUserByConnectionAction implements MenuAction {

	@Override
	public boolean process(Scanner scanner, UserRepository userRepository, LinkedInUser loggedInUser) {

		//get the users list
		List<LinkedInUser> usersList = userRepository.retrieveAll();
		
		//sort the list
		Collections.sort(usersList, new Comparator<LinkedInUser>() {
			
			public int compare(LinkedInUser user1, LinkedInUser user2) {
				return user2.getConnections().size() - user1.getConnections().size();
			}
			
		});
		
		
		//print the list (users with more connections are first)
		for (int i = 0; i < usersList.size(); i++) {
			System.out.println(usersList.get(i).getUsername() + "; connection size = " +
					usersList.get(i).getConnections().size());
		}

		
		//return true to keep the user logged in
		return true;
	}

}
