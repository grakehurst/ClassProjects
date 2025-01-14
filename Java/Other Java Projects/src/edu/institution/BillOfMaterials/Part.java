package edu.institution.midterm;

import java.util.*;

//this class holds the properties of the parts in the store
public class Part {

	//identifies the part in the store
	private String partNumber = "";
	
	//display name for the part
	private String name = "";
	
	//the type of the part. valid types:
	//"ASSEMBLY" : final assemblies sold to the customer
	//"COMPONENT" : sub component parts that make up final assemblies
	//"PURCHASE" : external parts purchased from a vendor
	private String partType = "";
	
	//the price for this part
	private float price = 0;
	
	//list of BOM entries on level under this part
	private List<BomEntry> billOfMaterial = new ArrayList<>();

	
	//getter and setter for part number
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		//make sure the string is not null
		if(partNumber == null)
			System.out.println("Part number cannot be null.");
		else
			this.partNumber = partNumber;
	}

	
	//getter and setter for name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		//make sure the string is not null
		if(name == null)
			System.out.println("Name cannot be null.");
		else
			this.name = name;
	}

	
	//getter and setter for part type
	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		//make sure the string is not null
		if(partType == null)
			System.out.println("Part type cannot be null.");
		else {
			
			if(partType.equals("ASSEMBLY") || partType.equals("COMPONENT") || partType.equals("PURCHASE"))
				this.partType = partType;
			else
				System.out.println("Part type must be 'ASSEMBLY', 'COMPONENT', or 'PURCHASE'");
		}
	}

	
	//getter and setter for price
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		
		//make sure the price is not negative
		if(price < 0)
			System.out.println("Price cannot be negative");
		else
			this.price = price;
		
	}

	
	//getter and setter for bill of material list
	public List<BomEntry> getBillOfMaterial() {
		return billOfMaterial;
	}

	public void setBillOfMaterial(List<BomEntry> billOfMaterial) {
		
		//make sure the list is not null
		if(billOfMaterial == null)
			System.out.println("Bill of Materials cannot be null.");
		else
			this.billOfMaterial = billOfMaterial;
	}

	
	//to string, hash code, and equals methods
	//part number is the unique field for this class
	@Override
	public String toString() {
		return "Part [partNumber=" + partNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Part other = (Part) obj;
		if (partNumber == null) {
			if (other.partNumber != null) //can't test for this because partNumber cannot be null
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		return true;
	}
	
	
}
