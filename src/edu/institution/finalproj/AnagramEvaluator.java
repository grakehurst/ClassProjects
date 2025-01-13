package edu.institution.finalproj;

import java.util.*;

public interface AnagramEvaluator {

	
	//method that returns a list of words derived from the supplied anagram
	//takes in the anagram to evaluate
	//and the option from the command line
	List<String> evaluate(String anagram, String option);
}
