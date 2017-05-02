/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author poole
 *
 */
public class EconomyTests {
	
	private Economy economyClassPassenger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		economyClassPassenger = new Economy(1, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.Economy#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Economy#upgrade()}.
	 */
	@Test
	public void testUpgradeStateChange() {
		String oldPassID = economyClassPassenger.getPassID();
		economyClassPassenger.upgrade();
		assertEquals("P(U) " + oldPassID, economyClassPassenger.getPassID());
	}

	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testEconomyMinimumBoundaryPointsPassed() throws PassengerException {
		Economy economyInput = new Economy(0, 1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testEconomyNegativeBookingTime() throws PassengerException {
		Economy economyInput = new Economy(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testEconomyZeroDepartureTime() throws PassengerException {
		Economy economyInput = new Economy(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testEconomyNegativeDepartureTime() throws PassengerException {
		Economy economyInput = new Economy(1, -1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testEconomyDepartureTimeLessThanBooking() throws PassengerException {
		Economy economyInput = new Economy(3, 2);
	}
	
}
