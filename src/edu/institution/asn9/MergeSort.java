package edu.institution.asn9;

public class MergeSort {
	/** The method for sorting the numbers */
	public static void mergeSort(Integer[] list) {
		if (list.length > 1) {
			// Merge sort the first half
			Integer[] firstHalf = new Integer[list.length / 2];
			System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
			mergeSort(firstHalf);

			// Merge sort the second half
			Integer secondHalfLength = list.length - list.length / 2;
			Integer[] secondHalf = new Integer[secondHalfLength];
			System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);
			mergeSort(secondHalf);

			// Merge firstHalf with secondHalf into list
			merge(firstHalf, secondHalf, list);
		}
	}

	/** Merge two sorted lists */
	public static void merge(Integer[] list1, Integer[] list2, Integer[] temp) {
		Integer current1 = 0; // Current index in list1
		Integer current2 = 0; // Current index in list2
		Integer current3 = 0; // Current index in temp

		while (current1 < list1.length && current2 < list2.length) {
			if (list1[current1] < list2[current2])
				temp[current3++] = list1[current1++];
			else
				temp[current3++] = list2[current2++];
		}

		while (current1 < list1.length)
			temp[current3++] = list1[current1++];

		while (current2 < list2.length)
			temp[current3++] = list2[current2++];
	}
}
