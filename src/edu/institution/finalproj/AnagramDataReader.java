package edu.institution.finalproj;

import java.util.*;

public interface AnagramDataReader {

	//this methods returns a set of words from the anagram text file
	//returns empty if no words were found
	Set<String> readData();
}
