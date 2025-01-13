package edu.institution.midterm;

//this class holds a bill of material entry in the parts store
public class BomEntry {
	
	//identifies the part in the BOM
	private String partNumber = "";
	
	//quantity needed for this part
	private int quantity = 0;
	
	
	//getter, setter for partNumber
	public String getPartNumber() {
		return this.partNumber;
	}
	
	public void setPartNumber(String partNumber) {
		//make sure the value is not null
		if(partNumber == null) 
			System.out.println("Part number cannot be null.");
		else
			this.partNumber = partNumber;
	}
	
	
	//getter, setter for quantity
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		
		//make sure the quantity is not negative
		if(quantity < 0) //you can have 0 parts, but not a negative amount
			System.out.println("Quantity cannot be negative");
		else
			this.quantity = quantity;
	}

	
	//toString, hash code, and equals method 
	//where part number is the unique value for the object
	@Override
	public String toString() {
		return "BomEntry [partNumber=" + partNumber + "]";
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
		BomEntry other = (BomEntry) obj;
		if (partNumber == null) {
			if (other.partNumber != null) //can't test this because it won't except a null value
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		return true;
	}
	
	
}
