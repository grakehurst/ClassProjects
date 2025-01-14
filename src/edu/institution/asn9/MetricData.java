package edu.institution.asn9;

public class MetricData implements Comparable<MetricData> {

	
	//the sort algorithm used to sort the data
	private SortAlgorithm sortAlgorithm;
	
	//time complexity for the sort algorithm
	private TimeComplexity timeComplexity; 
	
	//time (in milliseconds) that it took to sort the data
	private long executionTime;
	
	//constructor that takes in a sortAlgorithm
	public MetricData(SortAlgorithm sortAlgorithm) {
		
		//set this sort algorithm to the parameter
		this.sortAlgorithm = sortAlgorithm;
	}

	
	//getter and setter for time complexity
	public TimeComplexity getTimeComplexity() {
		return timeComplexity;
	}

	public void setTimeComplexity(TimeComplexity timeComplexity) {
		if(timeComplexity != null)
			this.timeComplexity = timeComplexity;
	}

	
	//getter and setter for execution time
	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	
	//getter for sort algorithm
	public SortAlgorithm getSortAlgorithm() {
		return sortAlgorithm;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sortAlgorithm == null) ? 0 : sortAlgorithm.hashCode());
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
		MetricData other = (MetricData) obj;
		if (sortAlgorithm != other.sortAlgorithm)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MetricData [sortAlgorithm=" + sortAlgorithm + "]";
	}


	//sort the array based off of their execution times (shortest to longest)
	@Override
	public int compareTo(MetricData o) {
		
		return Long.compare(this.getExecutionTime(), o.getExecutionTime());
	}


	
	
	
}
