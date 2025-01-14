package edu.institution.actions.asn3;

import java.io.*;
import java.util.*;

import edu.institution.ApplicationHelper;
import edu.institution.UserRepository;
import edu.institution.asn2.LinkedInException;
import edu.institution.asn2.LinkedInUser;

public class SerializedUserRepository implements UserRepository, Serializable {
	//default serial version ID
	private static final long serialVersionUID = 1L;
	
	//fields
	private String filePath; //the path to get to the file on the computer
	private String fileName; //the name of the file
	
	//an ArrayList that holds all of the accounts created and stored
	private List<LinkedInUser> LinkedInUsersList = new ArrayList<>();

	//this method opens the data file and writes a list of 
	//all LinkedIn users to the ArrayList of LinkedIn users
	@Override
	public void init(String filePath, String fileName)  {

		//add the file name and path to the class
		this.filePath = filePath;
		this.fileName = fileName;
		
		//string for the complete file path
		String path = filePath + fileName;
		
		
		//create a new file object for the file
		File dataFile = new File(path);
		
		//open the file, try to read it into the ArrayList
		try {
			
			//check if the file is empty
			if (dataFile.length() == 0) {
				
				//initialize a blank array list
				LinkedInUsersList = new ArrayList<>();
				
			} else { //if there is data in the file
				
				//file input stream object
				FileInputStream fis = new FileInputStream(dataFile);
				
				//object input stream ; file input stream is wrapped in a buffer
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				//read in from the file until you get to the end
				
				try {
					
					//read from the file into a new LinkedInUser object
					//runs until the EOF exception
					for(;;) {
						
						
						//get the next object from the file
						LinkedInUser user = (LinkedInUser) (ois.readObject());
						
						//add that object to the ArrayList
						LinkedInUsersList.add(user);
					}	
					
				} catch(EOFException e) {
					
				} finally {
					ois.close();
				}
			}
			
			
		//exceptions when opening and using the file	
		} catch(FileNotFoundException e) {
			System.out.println("File not found.");
		} catch (IOException e) {
			System.out.println("There was a problem reading the file.");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find the right class.");
		}
		
		//List<LinkedInUser> listusers = new ArrayList<>(this.LinkedInUsersList);
		
		//initialize the skill set hash map in the Application Helper class
		ApplicationHelper.initSkillsetUsages(this.LinkedInUsersList);
		
	}

	
	//add a new user to the ArrayList and update the file
	@Override
	public void add(LinkedInUser user) throws LinkedInException {
		
		//check if the entered user name is already used by another user
		//this will be used for the later if/else to see if a person has already 
		//made an account with this user name
		boolean usernameAlreadyTaken = doesUsernameExist(user.getUsername());
		
		//check to make sure the LinkedInUser object is correct
		
		if(user.getUsername() == null || user.getType() == null) { //user name and type must be filled in
			throw new LinkedInException("The user name and type are required to add a new user.");
			
		} else if(!user.getType().equals("P") && !user.getType().equals("S")) { //user type must be "S" or "P" (standard or premium)
			throw new LinkedInException("Invalid user type. Valid types are P or S.");
			
		} else if(usernameAlreadyTaken == true) { //user's user name must not already be taken by another user
			throw new LinkedInException("A user already exists with that user name.");
			
		} else { //otherwise, add this user to the list and save the data
			LinkedInUsersList.add(user); //add the user
			saveAll(); //save the data
		}
		
	}

	
	//save the array list to the data file
	@Override
	public void saveAll() {
		
		String path = filePath + fileName;
		//create a new file object for the file
		File dataFile = new File(path);
		
		//open the file, rewrite it with the data from the ArrayList of LinkedInUsers
		try {
			
			
			//object output stream ; file output stream is wrapped in a buffer
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(dataFile)));
			
			//write each object to the file
			for (int i = 0; i < LinkedInUsersList.size(); i++) {

				//System.out.println(LinkedInUsersList.get(i).getUsername() + " " + LinkedInUsersList.get(i).getType());
				//write each user object to the file
				oos.writeObject(LinkedInUsersList.get(i));
			}
			
			//close the file
			oos.close();
			
		} catch (IOException e) {
			
			System.out.println("There was a problem writing to the file.");
		}
		
	}

	
	//delete a user from the list of LinkedIn users
	@Override
	public void delete(LinkedInUser user) {
		
		//variable to check if the user exists
		//this will be used later in the if/else that would remove the user from the list
		//if they were already on the list
		boolean userExists = false;
		
		//variable to show which object to remove from the list
		int removeIndex = 0;
		
		//check to see if the user exists
		for(int i = 0; i < LinkedInUsersList.size(); i++) {
			
			//compare the user name of the passed in user to the user names in the list
			String userName = LinkedInUsersList.get(i).getUsername(); //user name of passed in user
			
			//set the boolean variable to true if it equals a user name in the list
			if(user.getUsername().equals(userName)) {
				userExists = true;
				removeIndex = i; //this is the index of the object to be removed
			}
		}
		
		//remove the object from the list if it exists
		if(userExists == true) {
			LinkedInUsersList.remove(removeIndex);
			
		} else { //if it does not exist, display an error message
			System.out.println("Username does not exist");
		}
	}

	
	
	//this method retrieves one specific user from the list of LinkedIn users
	//or returns null if the user name passed does not exist
	@Override
	public LinkedInUser retrieve(String username) {
		
		//variable to determine if the user exists
		boolean userExists = doesUsernameExist(username);
		
		//if the user name does exist, return the object
		if(userExists == true) {
			
			int index = findUser(username);
			
			//if the index is not negative
			if(index > -1) { //return the user from that index
				
				return LinkedInUsersList.get(index);
				
			} else //otherwise return a null value
				return null;
			
		} else { //otherwise return a null value
			return null;
		}
		
	}

	//this method returns the list of all LinkedInUsers
	@Override
	public List<LinkedInUser> retrieveAll() {
		return LinkedInUsersList;
	}
	

	
	//this method returns a boolean 
	//true if the user name exists
	//false if the user name does not exist
	public boolean doesUsernameExist(String username) {
		
		//variable to determine if the user exists
		boolean userExists = false;
		
		//check to see if the user exists
		for(int i = 0; i < LinkedInUsersList.size(); i++) {
						
			//compare the user name of the passed in user to the user names in the list
			String checkUsername = LinkedInUsersList.get(i).getUsername(); //user name of passed in user
			
			//set the boolean variable to true if it equals a user name in the list
			if(username.equals(checkUsername)) {
				userExists = true;
			}
		}
		
		return userExists;
	}
	
	
	
	//this method returns a index of a LinkedInUser in the list that matches the passed user name
	public int findUser(String username) {
		
		//index variable to point to the right object in the list
		int index = -1;
		
		//use the LinkedInUser list to find the user name
		for(int i = 0; i < LinkedInUsersList.size(); i++) {
									
			//compare the user name of the passed in user to the user names in the list
			String checkUsername = LinkedInUsersList.get(i).getUsername(); //user name of passed in user
									
			//when the user name is found, set the index variable to the value of i
			if(username.contentEquals(checkUsername)) {
				index = i;
			}
		}
		
		//return the index of the correct LinkedInUser 
		return index;
	}
}






















