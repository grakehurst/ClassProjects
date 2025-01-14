package edu.institution.actions.asn5;

import java.util.*;


//this class has two methods:
//remove duplicates and linear search
public class Utilities {

	//this generic method takes in an array list
	//and checks it for duplicates
	//if there are duplicates, they are removed
	public <T> void removeDuplicates(List<T> items) {
		
		//just return to the caller is the array list is null
		if(items == null)
			return;
		
		
		//check to see that the array list passed in has more than one element
		//if it is null or empty, this method should not do anything
		//if there is one element, there cannot be a duplicate element
		if(items.size() > 1) {
			
			//create a new array list to hold each checked element in the passed array list
			List<T> checkedItems = new ArrayList<>();
			
			int i = 0;
			//create a for loop to check each item in the array
			while(i < items.size()) {
				
				//get the next item in the array
				T nextItem = items.get(i);
				
				//compare it to the items that are in the checked item array
				boolean isDuplicate = false;
				int j = 0;
				
				//this loop stops if it finds a duplicate of the next element
				//if not, it cycles through each item in the checkedItems array
				//and isDuplicate remains false
				while(!isDuplicate && j < checkedItems.size()) {
					
					if(nextItem.equals(checkedItems.get(j))) //if the item is a duplicate
							isDuplicate = true; //stop the loop
					else //if the item is not a duplicate
						j++;
				}
				
				//if the item is a duplicate, remove it from the list
				if(isDuplicate) {
					items.remove(i);
					//numItemsRemoved++;
				}
				//otherwise, add that item to the checkedItems list
				else {
					checkedItems.add(items.get(i));
					i++;
				}
				//System.out.println("checked: " + checkedItems);
				//i++;
			}
			
			//now make the original list match the copy
			//that has been edited if there were duplicates
			//items = itemsCopy;
			//System.out.println("items: " + itemsCopy);
			
		}
		
	}
	
	
	//this generic method looks through a list to find an element matching the passed in key
	//if it finds the key, it returns the correct element
	//otherwise, it returns null
	public <E> E linearSearch(List<E> list, E key) {
		
		//make sure that the list is not null
		if(list == null)
			return null;
		//make sure that the passed in array is not blank
		//if it is return null
		else if(list.isEmpty()) 
			return null;
		//make sure that the key is not null
		else if(key == null)
			return null;
		//otherwise, look for the key in the array list
		else {
		
			//loop to check each element or the list for the key
			for(int i = 0; i < list.size(); i++) {
				
				//return that element if it is equal to the key
				if(list.get(i).equals(key)) {
					return list.get(i);
				}
			}
		}
		
		//if the key does not exist in the list, return null
		return null;
	}
}