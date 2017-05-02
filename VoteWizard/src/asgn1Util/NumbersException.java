/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Util;

/**
 * 
 * This class represents any exceptions associated with the <code>Numbers</code>
 * utility class.
 * 
 * @author hogan
 * 
 */
@SuppressWarnings("serial")
public class NumbersException extends Exception {

	/**
	 * Constructor for <code>Exception</code>s relating to <code>Numbers</code>
	 * 
	 * @param msg
	 *            <code>String</code> containing message for the user
	 */
	public NumbersException(String msg) {
		super("Numbers Exception: " + msg);
	}
}
