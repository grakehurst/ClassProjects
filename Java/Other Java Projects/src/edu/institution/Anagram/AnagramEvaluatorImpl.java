package edu.institution.finalproj;

import java.util.*;

import com.sun.org.apache.xml.internal.utils.StringComparable;


//CITATIONS:

//https://www.geeksforgeeks.org/print-all-permutations-of-a-string-in-java/
	//I used this for the noFIlterAnagram method later in this class
	//This helped me get an idea of how to find all the possible combinations of a string


//this class evaluates an anagram based on the option (filtered or unfiltered) that is passed in
public class AnagramEvaluatorImpl implements AnagramEvaluator {

	//create a set to hold all the combinations without duplicates
	//this will be used in the recursive method called noFilterAnagram
	Set<String> noFilterAnagramSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
	
	
	//this method returns the words derived from the anagram
	//takes in the anagram to be evaluated
	//takes in the option (should be "nf" or "words"; null == "words)
	@Override
	public List<String> evaluate(String anagram, String option) {
		
		//get a set of all the data from the anagram_data.txt file
		AnagramDataReaderImpl dataReader = new AnagramDataReaderImpl();
		Set<String> wordsSet = dataReader.readData();
		
		//make the list that will be returned
		List<String> filteredAnagramList = new ArrayList<String>();
		
		//make a no-filter list
		//and get all of the possible words from the anagram
		List<String> noFilterAnagramList = noFilterAnagram(anagram, "");
		
		//if the option passed in was "words" or "filter-words"
		//it needs to be sorted so that only real words are in the list
		if(option.equals("words") || option.equals("filter-words")) {
			
			//loop through the list of words
			for(int i = 0; i < noFilterAnagramList.size(); i++) {
				
				//get the next word in the list
				String nextWord = noFilterAnagramList.get(i);
				
				//compare it to the data from the text file
				if(wordsSet.contains(nextWord)) {
					
					//add it to the final list
					filteredAnagramList.add(nextWord);
					
				} //otherwise it will not get added
			}
			
			//then return the list once it is sorted correctly
			return filteredAnagramList;
		}
		
		//otherwise, the option is no filter so the list is already correct
		else
			return noFilterAnagramList;
	}

	
	//this method returns all the possible combinations of the anagram
	//this method works recursively
	public List<String> noFilterAnagram(String anagram, String word) {
		
		//base case for the recursion:
		//there is nothing left in the passed through anagram
		if(anagram.length() == 0) {
			
			//add all the words gathered to the list
			noFilterAnagramSet.add(word);
		}
		
		//otherwise, start looping through the word
		//making each character the starting letter
		for(int i = 0; i < anagram.length(); i++) {
			
			//get the next character in the string that was passed in
			char nextChar = anagram.charAt(i);
			
			//now get the rest of the word before and after that character
			//0 -> i == the first part of the string up until the index character
			//i + 1  == the rest of the string after the index character
			String restOfAnagram = anagram.substring(0, i) + anagram.substring(i + 1);
			
			//add the index character to the word being made and
			//recursively pass through the rest of the anagram and the new word
			noFilterAnagram(restOfAnagram, word + nextChar);
			
		}
		
		
		//make a list to convert the set to
		List<String> noFilterAnagramList = new ArrayList<String>();
		
		//for each string variable in the set
		for(String nextString : noFilterAnagramSet) {
			
			//add the string to the list
			noFilterAnagramList.add(nextString);
		}
		
		
		//return the list when it is complete
		return noFilterAnagramList;
	}
}






























