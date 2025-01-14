package edu.institution.midterm;

import java.util.*;

//interface for the part manager class
public interface PartManager {
	
	//import the file when given the file's file path
	public int importPartStore(String filePath);
	
	//calculate the cost of a part when given the part number
	//returns the part after the price has been set in the object
	public Part costPart(String partNumber);
	
	//return the Part object when given the part number
	public Part retrievePart(String partNumber);
	
	//return all the final assembly parts sorted alphabetically
	//all objects in the array have the part type of ASSEMBLY
	public List<Part> getFinalAssemblies();
	
	//return all the purchased parts by order of highest to lowest price
	//all objects in the array have the part type of PURCHASE
	public List<Part> getPurchasePartsByPrice();

}
