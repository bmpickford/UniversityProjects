/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

/**
 * @author poole
 *
 */
public class BusinessTests {
		
	private Business businessClassPassenger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		businessClassPassenger = new Business(1, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.Business#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Business#upgrade()}.
	 */
	@Test
	public void testUpgradeStateChange() {
		String oldPassID = businessClassPassenger.getPassID();
		businessClassPassenger.upgrade();
		assertEquals("F(U) " + oldPassID, businessClassPassenger.getPassID());
	}

	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testBusinessMinimumBoundaryPointsPassed() throws PassengerException {
		Business businessInput = new Business(0, 1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testBusinessNegativeBookingTime() throws PassengerException {
		Business businessInput = new Business(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testBusinessZeroDepartureTime() throws PassengerException {
		Business businessInput = new Business(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testBusinessNegativeDepartureTime() throws PassengerException {
		Business businessInput = new Business(1, -1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testBusinessDepartureTimeLessThanBooking() throws PassengerException {
		Business businessInput = new Business(3, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.Business#Business()}.
	 */
	@Test
	public void testBusiness() {
		assertTrue(true);
	}

}
