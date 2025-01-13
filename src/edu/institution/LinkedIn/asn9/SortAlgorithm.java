package edu.institution.asn9;

/**
 * Represents an algorithm that sorts data.
 */
public enum SortAlgorithm {
	/**
	 * Bubble Sort.
	 */
	BUBBLE_SORT("Bubble Sort"),
	/**
	 * Insertion Sort.
	 */
	INSERTION_SORT("Insertion Sort"),
	/**
	 * Merge Sort.
	 */
	MERGE_SORT("Merge Sort"),
	/**
	 * Quick Sort.
	 */
	QUICK_SORT("Quick Sort"),
	/**
	 * Heap Sort.
	 */
	HEAP_SORT("Heap Sort");

	private String displayName;

	SortAlgorithm(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the display name for this sort algorithm.
	 * 
	 * @return the display name.
	 */
	public String display() {
		return this.displayName;
	}
}
