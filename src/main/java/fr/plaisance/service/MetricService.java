package fr.plaisance.service;

public interface MetricService {

	/**
	 * Compare the likeliness of two strings based on Levenstein distance.
	 * The result is true if the score is higher than a given threshold.
	 * 
	 * @param a - One of the strings to compare.
	 * @param b - One of the strings to compare.
	 * @return <code>true</code> if the strings match <code>false</code> if they don't.
	 */
	boolean matches(String a, String b);
}
