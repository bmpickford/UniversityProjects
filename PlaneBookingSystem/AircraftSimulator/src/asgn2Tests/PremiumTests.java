/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author poole
 *
 */
public class PremiumTests {
	
	private Premium premiumClassPassenger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		premiumClassPassenger = new Premium(1, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#upgrade()}.
	 */
	@Test
	public void testUpgradeStateChange() {
		String oldPassID = premiumClassPassenger.getPassID();
		premiumClassPassenger.upgrade();
		assertEquals("J(U) " + oldPassID, premiumClassPassenger.getPassID());
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testPremiumMinimumBoundaryPointsPassed() throws PassengerException {
		Premium premiumInput = new Premium(0, 1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testPremiumNegativeBookingTime() throws PassengerException {
		Premium premiumInput = new Premium(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testPremiumZeroDepartureTime() throws PassengerException {
		Premium premiumInput = new Premium(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testPremiumNegativeDepartureTime() throws PassengerException {
		Premium premiumInput = new Premium(1, -1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testPremiumDepartureTimeLessThanBooking() throws PassengerException {
		Premium premiumInput = new Premium(3, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium()}.
	 */
	@Test
	public void testPremium() {
		assertTrue(true);
	}

}
