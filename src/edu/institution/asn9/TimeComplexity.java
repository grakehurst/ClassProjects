package edu.institution.asn9;

/**
 * Represents a time complexity depicted in the Big O notation.
 */
public enum TimeComplexity {
	/**
	 * Linear O(n) notation.
	 */
	LINEAR("Linear O(n)"),
	/**
	 * Constant O(1) notation.
	 */
	CONSTANT("Constant O(1)"),
	/**
	 * Quadratic O(n2) notation.
	 */
	QUADRATIC("Quadratic O(n2)"),
	/**
	 * Logarithmic O(n log n)
	 */
	LOGARITHMIC("Logarithmic O(n log n)"),
	/**
	 * Cubic O(n3)
	 */
	CUBIC("Cubic O(n3)"),
	/**
	 * Exponential O(2n)
	 */
	EXPONENTIAL("Exponential O(2n)");

	private String displayName;

	TimeComplexity(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the display name for this time complexity.
	 * 
	 * @return the display name.
	 */
	public String display() {
		return this.displayName;
	}
}
