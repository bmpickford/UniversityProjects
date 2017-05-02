/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Util;

/**
 * 
 * Utility methods relating to numeric processing, usually parsing of integers
 * and floats
 * 
 * @author hogan
 * 
 */
public class Numbers {

	/**
	 * Method reads integer from string and handles the NumberFormatException if
	 * the parsing doesn't work, returning a null value.
	 * 
	 * @param token <code>String</code> extracted through earlier parsing
	 * @return valid <code>Integer</code> or null
	 */
	public static Integer getIntegerFromToken(String token) {
		int value;

		try {
			value = Integer.parseInt(token);
			return new Integer(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Method to wrap parsing of an integer value from a <code>String</code> token. 
	 * 
	 * @param token <code>String</code> extracted through earlier parsing
	 * @param message <code>String</code> with custom error message
	 * @return valid <code>Integer</code> parsed from the token
	 * @throws NumbersException if parsing error 
	 */
	public static int parseIntFromToken(String token, String message)
			throws NumbersException {
		// Grab value if we can
		int result;
		try {
			result = Integer.parseInt(token.trim());
		} catch (NumberFormatException e) {
			throw new NumbersException(message);
		}
		return result;
	}
}
