package edu.institution.finalproj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AnagramDataReaderImpl implements AnagramDataReader {

	//field for the file path
	//THIS IS JUST THE FILE PATH ON MY COMPUTER
	public String filePath = "C:" + File.separator + "Users" + File.separator + 
			"Gavin" + File.separator + "Java2" + File.separator + "anagram_data.txt";
	
	//this method takes in all the words from a 
	@Override
	public Set<String> readData() {
		
		//create the set of strings to be returned
		//this won't allow duplicates to be added
		Set<String> wordsSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		
		//try to read the file into the set
		try {
			
			//use the file path to get the contents of the file
			File file = new File(filePath);
			
			//make a scanner to read the file
			Scanner input = new Scanner(file);
			
			//add each word to the set 
				
			//if the file is not empty
			if(file.length() != 0) {
				
				//loop through the file
				while(input.hasNext()) {
					
					//get the next word in the file
					String nextWord = input.nextLine();
					
					//make sure the value is not null
					if(wordsSet != null)
						//add that string to the hash set
						wordsSet.add(nextWord);
					//System.out.println("set: " + wordsSet.toString());
				}
			}
			
			
			//close the file
			//input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		
		
		//return the list of words
		return wordsSet;
	}
}
















