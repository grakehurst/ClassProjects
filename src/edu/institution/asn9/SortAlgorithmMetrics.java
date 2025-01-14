package edu.institution.asn9;

import java.io.*;
import java.time.*;
import java.util.*;

import edu.institution.asn2.LinkedInUser;

public class SortAlgorithmMetrics {

	
	//this method uses the different sort classes 
	//to compare the time it takes to sort an array
	public List<MetricData> retrieveMetrics(String filePath) {
		
		//create a list of MetricData objects to be returned
		List<MetricData> metrics = new ArrayList<>();
		
		//create an array of integers to get the contents of the file
		List<Integer> numbers = new ArrayList<>();
		
		//read from the file into the array
		try {
			//create a file object
			File file = new File(filePath);
			
			//make a scanner to read the file
			Scanner input = new Scanner(file);
			int i = 0;
			//make sure there is data in the file
			if(file.length() == 0) 
				System.out.println("The File is empty.");
			
			//otherwise add to the array
			else {
				//loop through the contents in the file
				while(input.hasNext()) {
					
					//get the next integer
					Integer integer = input.nextInt();
					
					//add it to the array
					numbers.add(integer);
				}
			}
			
			//close the file
			input.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
		} 
		
		//shuffle the list
		Collections.shuffle(numbers);
		
		//turn the list into an array of integers
		//create a template for the toArray class
		Integer[] template = {};
		
		//turn the list into an array 5 times
		//there are 5 arrays for the five different sort methods
		Integer[] list = numbers.toArray(template);
		Integer[] list2 = numbers.toArray(template);
		Integer[] list3 = numbers.toArray(template);
		Integer[] list4 = numbers.toArray(template);
		Integer[] list5 = numbers.toArray(template);
		
		//now use the data to create the 5 different MetricData objects
		//create variables for each of the MetricData fields
		SortAlgorithm sortAlgorithm;
		TimeComplexity timeComplexity;
		Long executionTime;
		
		
		/* BUBBLE SORT */
		//set the SortAlgorithm object to bubble sort
		sortAlgorithm = SortAlgorithm.BUBBLE_SORT;
		
		//create a MetricData object with that sort algorithm
		MetricData bubbleSort = new MetricData(sortAlgorithm);
		
		//set the TimeComplexity object to quadratic
		timeComplexity = TimeComplexity.QUADRATIC; //bubble sort worst case is O(n2)
		
		//set the TimeComplexity
		bubbleSort.setTimeComplexity(timeComplexity);
		
		//get the start time for the sort
		LocalTime start = LocalTime.now();
		
		//sort the array 
		BubbleSort.bubbleSort(list);
		
		//get the end time after the sort
		LocalTime end = LocalTime.now();
		
		//get the execution time
		executionTime = Duration.between(start, end).toMillis(); //time before and after sort
		
		//set the ExecutionTime
		bubbleSort.setExecutionTime(executionTime);
		
		//add the bubbleSort data to the metrics list
		metrics.add(bubbleSort);
		
		
		/* MERGE SORT */
		//set the SortAlgorithm object to merge sort
		sortAlgorithm = SortAlgorithm.MERGE_SORT;
		
		//create a MetricData object with that sort algorithm
		MetricData mergeSort = new MetricData(sortAlgorithm);
		
		//set the TimeComplexity object to logarithmic
		timeComplexity = TimeComplexity.LOGARITHMIC; //merge sort is O(n log n)
		
		//set the TimeComplexity
		mergeSort.setTimeComplexity(timeComplexity);
		
		//get the start time for the sort
		start = LocalTime.now();
		
		//sort the array 
		MergeSort.mergeSort(list2);
		
		//get the end time after the sort
		end = LocalTime.now();
		
		//get the execution time
		executionTime = Duration.between(start, end).toMillis(); //time before and after sort
		
		//set the ExecutionTime
		mergeSort.setExecutionTime(executionTime);
		
		//add the mergeSort data to the metrics list
		metrics.add(mergeSort);
		
		
		/* QUICK SORT */
		//set the SortAlgorithm object to merge sort
		sortAlgorithm = SortAlgorithm.QUICK_SORT;
		
		//create a MetricData object with that sort algorithm
		MetricData quickSort = new MetricData(sortAlgorithm);
		
		//set the TimeComplexity object to quadratic
		timeComplexity = TimeComplexity.QUADRATIC; //quickSort is O(n2)
		
		//set the TimeComplexity
		quickSort.setTimeComplexity(timeComplexity);
		
		//get the start time for the sort
		start = LocalTime.now();
		
		//sort the array 
		QuickSort.quickSort(list3);
		
		//get the end time after the sort
		end = LocalTime.now();
		
		//get the execution time
		executionTime = Duration.between(start, end).toMillis(); //time before and after sort
		
		//set the ExecutionTime
		quickSort.setExecutionTime(executionTime);
		
		//add the mergeSort data to the metrics list
		metrics.add(quickSort);
		
		
		/* HEAP SORT */
		//set the SortAlgorithm object to merge sort
		sortAlgorithm = SortAlgorithm.HEAP_SORT;
		
		//create a MetricData object with that sort algorithm
		MetricData heapSort = new MetricData(sortAlgorithm);
		
		//set the TimeComplexity object to logarithmic
		timeComplexity = TimeComplexity.LOGARITHMIC; //heap sort is O(n log n)
		
		//set the TimeComplexity
		heapSort.setTimeComplexity(timeComplexity);
		
		//get the start time for the sort
		start = LocalTime.now();
		
		//sort the array 
		HeapSort.heapSort(list4);
		
		//get the end time after the sort
		end = LocalTime.now();
		
		//get the execution time
		executionTime = Duration.between(start, end).toMillis(); //time before and after sort
		
		//set the ExecutionTime
		heapSort.setExecutionTime(executionTime);
		
		//add the mergeSort data to the metrics list
		metrics.add(heapSort);
		
		
		/* Insertion SORT */
		//set the SortAlgorithm object to merge sort
		sortAlgorithm = SortAlgorithm.INSERTION_SORT;
		
		//create a MetricData object with that sort algorithm
		MetricData insertionSort = new MetricData(sortAlgorithm);
		
		//set the TimeComplexity object to quadratic
		timeComplexity = TimeComplexity.QUADRATIC; //insertion sort is O(n2)
		
		//set the TimeComplexity
		insertionSort.setTimeComplexity(timeComplexity);
		
		//get the start time for the sort
		start = LocalTime.now();
		
		//sort the array 
		InsertionSort.insertionSort(list5);
		
		//get the end time after the sort
		end = LocalTime.now();
		
		//get the execution time
		executionTime = Duration.between(start, end).toMillis(); //time before and after sort
		
		//set the ExecutionTime
		insertionSort.setExecutionTime(executionTime);
		
		//add the insertionSort data to the metrics list
		metrics.add(insertionSort);
		
		//sort the array
		Collections.sort(metrics);
		
		
		//return the list of metric data objects
		return metrics;
	}
}

















