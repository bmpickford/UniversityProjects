/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Util;


/**
 * 
 * Utility methods relating to String processing
 * 
 * @author hogan
 * 
 */
public class Strings {

	/**
	 * Wrapper method to check whether a String array contains null or empty tokens
	 * 
	 * @param tokens <code>String[]</code> to be validated
	 * @return <code>AND isNullOrEmpty(token)</code>
	 */
	public static boolean checkTokensValid(String[] tokens) {
		// Check tokens available
		boolean validTokens = true;
		for (String token : tokens) {
			if (Strings.nullOrEmpty(token)) {
				validTokens = false;
			}
		}
		return validTokens;
	}

	/**
	 * Method to create a string containing the specified number of characters
	 * 
	 * @param ch <code>char</code> to be output
	 * @param numChars <code>int</code> defined number of copies
	 * @return <code>String</code> consisting of numChars copies of ch
	 */
	public static String createPadding(char ch, int numChars) {
		String str = "";
		for (int i = 0; i <= (numChars - 1); ++i) {
			str += ch;
		}
		return str;
	}

	/**
	 * Method to host a right justified string within a string of a specified
	 * size, with padding as necessary using the space character.
	 * 
	 * @param str <code>String</code> to be padded
	 * @param numSpaces <code>int</code> width of the containing field
	 */
	public static String createString(String str, int numSpaces) {
		int padding = numSpaces - str.length();
		if (padding >= 0) {
			return Strings.createPadding(' ', padding) + str;
		} else {
			return str;
		}
	}

	/**
	 * Helper method to simplify tests for null or empty strings
	 * 
	 * @param str <code>String</code> to be tested
	 * @return <code>true</code> if <code>isNull(str) OR isEmpty(str)</code>
	 */
	public static boolean nullOrEmpty(String str) {
		return (str == null) || (str.isEmpty());
	}
}