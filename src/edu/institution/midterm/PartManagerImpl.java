package edu.institution.midterm;

import java.util.*;
import java.util.stream.Stream;

import com.google.gson.Gson;

import edu.institution.asn2.LinkedInUser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

//this class implements the methods from the PartManager interface
public class PartManagerImpl implements PartManager{
	 
	//create a hash map to store the parts 
	private HashMap<String, Part> hashMap = new HashMap<String, Part>();
	
	
	//this method reads data from a JSon file to a HashMap
	@Override
	public int importPartStore(String filePath) {
		
		//create a string builder 
		StringBuilder jsonData = new StringBuilder();
		
		try {
			//create a buffered reader to read the file
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			//create a current line string to examine the current line in the file
			String nextLine;
			
			//read through the file, add to the jsonData string
			while((nextLine = reader.readLine()) != null) {
				//add to the string
				jsonData.append(nextLine);
			}
			
			//create a GSON object to read the data into an array
			Gson gson = new Gson();

			//create an array of parts, add the data
			Part[] parts = gson.fromJson(jsonData.toString(), Part[].class);
			 
			//add the array into the hash map
			for (int i = 0; i < parts.length; i++) {
				
				//get the next object
				Part nextPart = new Part();
				nextPart = parts[i];
				
				//get the partNumber string for that object
				String nextPartNumber = nextPart.getPartNumber();
				
				//if the HashMap does not already have that partNumber,
				if(!(hashMap.containsKey(nextPartNumber))) {
					//add it to the HashMap with it's object
					hashMap.put(nextPartNumber, nextPart);
				} //otherwise, it is a duplicate so move on to the next one
			}
			
			//close the file
			reader.close();
			 
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("There was a problem reading the file");
		}
		
		//return the amount of parts in the store
		return hashMap.size();
		
	}

	//this method calculates the cost of the part number passed in
	//and returns the part object
	@Override
	public Part costPart(String partNumber) {
		
		//create a float to hold the price of a certain part
		Float price = 0f;
		
		//use the partNumber to get the part object with retrievePart
		Part part = retrievePart(partNumber);
		
		//if the part does not exist, return a null value
		if(part == null)
			return null;
		
		//otherwise, start the recursion
		else {
			
			//if the part does not have a bill of materials
			if(part.getBillOfMaterial().size() == 0) {
				//System.out.println("BOM = 0: " + part.getPartNumber() + " " + part.getPrice());
				return part; //return the part
			}
			
			//otherwise get the BOM to calculate the total price
			else {
					
				//get the component's bom
				List<BomEntry> bom = part.getBillOfMaterial();
				
				//compute the price of each item in the bom
				for(int i = 0; i < bom.size(); i++) {
					
					//System.out.println("for loop: " + part.getPartNumber() + " " + part.getPrice());
					//pass the next part number into the method
					Part nextPart = costPart(bom.get(i).getPartNumber());
					
					//add that part's price to the price variable
					price += nextPart.getPrice() * bom.get(i).getQuantity();
				}
				
				//set the cost as this component's price
				part.setPrice(price);
				//System.out.println("after loop: " + part.getPartNumber() + " " + part.getPrice());
				
				//return the part
				return part;
				}
			}
		}

	
	//this method takes in a partNumber 
	//and returns the part object associated with it
	@Override
	public Part retrievePart(String partNumber) {
		
		//if the part exists
		if(hashMap.containsKey(partNumber)) {
			
			//use the partNumber as the key to find the object that it belongs to
			Part part = hashMap.get(partNumber);
			
			
			//then return that part
			return part;
			
		} else { //otherwise return a null value if it does not exist
			return null;	
		}
	}

	@Override
	public List<Part> getFinalAssemblies() {
		
		//create an array list to hold the final assemblies
		List<Part> finalAssemblies = new ArrayList<>();
		
		//get a set of all the keys in the hashMap 
		Set<String> keySet = hashMap.keySet();
		
		//turn the set of keys into an array of strings
		//this has all the part numbers in it to use in the for loop
		String[] partNumbers = keySet.toArray(new String[keySet.size()]);
		
		//step through the hash map 
		//and add all parts with type "ASSEMBLY" to the list
		for (int i = 0; i < hashMap.size(); i++) {
			
			//get the next part in the hashMap 
			//with the next part number in the array of partNumbers
			Part part = hashMap.get(partNumbers[i]);
			
			//check if this part is an ASSEMBLY type
			if(part.getPartType().equals("ASSEMBLY")) {
				
				//if it is, add it to the array
				finalAssemblies.add(part);
			}
		}
		
		//sort the array in ascending order using a comparator
		Collections.sort(finalAssemblies, new Comparator<Part>() {
			
			public int compare(Part part1, Part part2) {
				return part1.getPartNumber().compareTo(part2.getPartNumber());
			}
			
		});
		
		//return the sorted array list
		return finalAssemblies;
	}

	
	
	@Override
	public List<Part> getPurchasePartsByPrice() {
		
		//create an array list to hold the final assemblies
		List<Part> purchasedParts = new ArrayList<>();
		
		//get a set of all the keys in the hashMap 
		Set<String> keySet = hashMap.keySet();
		
		//turn the set of keys into an array of strings
		//this has all the part numbers in it to use in the for loop
		String[] partNumbers = keySet.toArray(new String[keySet.size()]);
		
		//step through the hash map 
		//and add all parts with type "PURCHASE" to the list
		for (int i = 0; i < hashMap.size(); i++) {
			
			//get the next part in the hashMap 
			//with the next part number in the array of partNumbers
			Part part = hashMap.get(partNumbers[i]);
			
			//check if this part is an ASSEMBLY type
			if(part.getPartType().equals("PURCHASE")) {
				
				//if it is, add it to the array
				purchasedParts.add(part);
			}
		}
		
		//sort the list by descending order by their prices
		Collections.sort(purchasedParts, new Comparator<Part>() {

			@Override
			public int compare(Part o1, Part o2) {
				
				//float value comparators are different
				//you have to use getChange to be able to compare the prices
				//and return an integer value
				Float change1 = Float.valueOf(o1.getPrice());
				Float change2 = Float.valueOf(o2.getPrice());
				
				return change2.compareTo(change1);
			}
		});
		
		//return the array of purchased parts
		return purchasedParts;
	}

}