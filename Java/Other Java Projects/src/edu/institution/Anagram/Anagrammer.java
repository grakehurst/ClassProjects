//package edu.institution.finalproj;

import java.util.*;
import org.apache.commons.cli.*;


//CITATIONS:

//https://commons.apache.org/proper/commons-cli/usage.html
	//I used this to get a better understanding of how to code a CLI for this program.
	//Specifically, I looked at the Option syntax and CommandLine commands mostly

//this class creates the CLI for the Anagram program
public class Anagrammer {

	//main method
	public static void main(String[] args) {
		
		//starting output
		System.out.println("Anagrammer:");
		System.out.println();
		
		//CREATE THE OPTIONS FOR THE MENU
		
		//-a or --anagram
		Option anagramOption = new Option("a","anagram",true,"Supplies the anagram to evaluate");
		//set the argument to "word"
		anagramOption.setArgName("word");
		
		//-h or --help
		Option helpOption = new Option("h","help",false,"Displays this help textdisplays the help for this program");
		
		//-nf or --no-filter
		Option noFilterOption = new Option("nf","no-filter",false,"Output all anagram values with no filter");
		
		//-words or --filter-words
		Option filterWordsOption = new Option("words","filter-words",false,"Output only values that are known words");
		
		
		//create an Options object to hold all the options
		Options options = new Options();
		
		//add the options
		options.addOption(anagramOption);
		options.addOption(helpOption);
		options.addOption(noFilterOption);
		options.addOption(filterWordsOption);
		
		
		//parse the command line arguments
		CommandLineParser parser = new DefaultParser();
		
		try {
			
			//create a command line object
			CommandLine commandLine = parser.parse(options, args);
			
			//make sure -a and -h are not in the same line
			if(commandLine.hasOption("a") && commandLine.hasOption("h"))
				System.out.println("Cannot use the anagram command and help command at the same time.");
			
			//make sure that the command line has the -a option or -h option
			else if(commandLine.hasOption("a")) {
				
				//string variable to hold the anagram word
				String anagram = "";
				
				//anagram evaluator object to pass in the anagram and option
				AnagramEvaluatorImpl evaluator = new AnagramEvaluatorImpl();
				
				//list of strings to hold all of the anagram words that are returned
				List<String> anagramList = new ArrayList<>();
				
				//get the argument attached to the anagram command
				//loop through each argument to find the one that does not have the - in front 
				for(int i = 0; i < args.length; i++) {
					
					//get the next argument in the string array
					String nextArgs = args[i];
					
					//get the first character of that string
					String firstChar = nextArgs.substring(0, 1);
					
					//if the first character is a - symbol, then it is an option
					//if it is not, then it will be the anagram
					//there will definitely be a word passed in
					//if there was not a word, an exception would have been thrown before this
					if(!(firstChar.equals("-"))) 
						anagram = nextArgs;
				}
				
				
				//now to figure out what to do with the anagram
				//figure out if -nf or -words was entered
				if(commandLine.hasOption("nf")) { //this means there is no filter, so set option == nf
					anagramList = evaluator.evaluate(anagram, "nf");
					
					//call the final output method
					Anagrammer anagrammer = new Anagrammer();;
					anagrammer.finalOutput(anagramList);
				}
				
				//this means there is a filter so option == words 
				//OR there was no filter specified, so it defaults to the words filter
				else { 
					anagramList = evaluator.evaluate(anagram, "words");
					
					//call the final output method
					Anagrammer anagrammer = new Anagrammer();;
					anagrammer.finalOutput(anagramList);
				}
				
				
				
			} 
			//or the -h option
			else if(commandLine.hasOption("h")) {
				
				//output the help menu to the console
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "anagrammer", options );
			}
			
			//if not, then display an error message because there is incorrect argument
			else 
				System.out.println("Incorrect option entered. Enter -h for help.");
			
			
		} catch(MissingArgumentException m) { //missing argument for the -a option
			System.out.println("Missing argument for option -a.");
			
		}catch (ParseException e) { //incorrect option input in the command line
			System.out.println("Incorrect option entered");
		} 
		
	}
	
	
	//this is the final output for the anagram option
	//used if -a is called
	public void finalOutput(List<String> anagramList) {
		
		//output the anagram list
		for (String nextWord : anagramList) 
			System.out.println(nextWord);
		
		//output the number of words 
		System.out.println("-- " + anagramList.size() + " value(s) found.");
		
	}
}




	























