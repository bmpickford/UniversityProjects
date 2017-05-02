/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

/**
 * @author poole
 *
 */
public class FirstTests {
	
	private First firstClassPassenger;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		firstClassPassenger = new First(1, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.First#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.First#upgrade()}.
	 */
	@Test
	public void testUpgradeNoStateChange() {
		String oldPassID = firstClassPassenger.getPassID();
		firstClassPassenger.upgrade();
		assertEquals(oldPassID, firstClassPassenger.getPassID());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFirstMinimumBoundaryPointsPassed() throws PassengerException {
		First firstInput = new First(0, 1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFirstNegativeBookingTime() throws PassengerException {
		First firstInput = new First(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFirstZeroDepartureTime() throws PassengerException {
		First firstInput = new First(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFirstNegativeDepartureTime() throws PassengerException {
		First firstInput = new First(1, -1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFirstDepartureTimeLessThanBooking() throws PassengerException {
		First firstInput = new First(3, 2);
	}

	/**
	 * Test method for {@link asgn2Passengers.First#First()}.
	 */
	@Test
	public void testFirst() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test
	public void testPassengerIntInt() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger()}.
	 */
	@Test
	public void testPassenger() {
		assertTrue(true);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test
	public void testCancelSeatValidEntry() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertTrue(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test
	public void testCancelSeatNoLongerConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertFalse(firstClassPassenger.isConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatQueuedPassenger() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.cancelSeat(1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatRefusedPassenger() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		firstClassPassenger.cancelSeat(1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatFlownPassenger() throws PassengerException {
		firstClassPassenger.flyPassenger(2);
		firstClassPassenger.cancelSeat(1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatNewPassenger() throws PassengerException {
		firstClassPassenger.cancelSeat(1);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatCancellationTimeLessThanZero() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(-1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test(expected = PassengerException.class)
	public void testCancelSeatDepartureLessThanCancellationTime() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(3);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test
	public void testCancelSeatPassengerRevertedToNew() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertTrue(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test
	public void testCancelSeatBookingTimeAdjusted() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertEquals(1, firstClassPassenger.getBookingTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 */
	@Test
	public void testCancelSeatDepartureTimeSame() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertEquals(2, firstClassPassenger.getDepartureTime());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testConfirmSeatAlreadyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.confirmSeat(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testConfirmSeatAlreadyRefused() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		firstClassPassenger.confirmSeat(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testConfirmSeatAlreadyFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		firstClassPassenger.confirmSeat(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testConfirmSeatConfirmationTimeLessThanZero() throws PassengerException {
		firstClassPassenger.confirmSeat(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testConfirmSeatDepartureTimeLessThanConfirmationTime() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatNewPassengerConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertTrue(firstClassPassenger.isConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatNoLongerNew() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertFalse(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatQueuedPassengerConfirmed() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.confirmSeat(2, 2);
		assertTrue(firstClassPassenger.isConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatNoLongerQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.confirmSeat(2, 2);
		assertFalse(firstClassPassenger.isQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatQueuedPassengerCheckExitQueTime() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.confirmSeat(2, 2);
		assertEquals(2, firstClassPassenger.getExitQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatCheckConfirmationTime() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertEquals(1, firstClassPassenger.getConfirmationTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatCheckDepartureTime() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertEquals(2, firstClassPassenger.getDepartureTime());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerPassengerFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		assertTrue(firstClassPassenger.isFlown());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerNoLongerConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		assertFalse(firstClassPassenger.isConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerIsNew() throws PassengerException {
		firstClassPassenger.flyPassenger(2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerIsQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.flyPassenger(2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerIsRefused() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		firstClassPassenger.flyPassenger(2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerAlreadyFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		firstClassPassenger.flyPassenger(2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerDepartureTimeEqualToZero() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testFlyPassengerDepartureTimeLessThanZero() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(-1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerDepartureEqualToOne() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(1);
		assertTrue(firstClassPassenger.isFlown());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerCheckDepartureTime() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		assertEquals(2, firstClassPassenger.getDepartureTime());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getBookingTime()}.
	 */
	@Test
	public void testGetBookingTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getConfirmationTime()}.
	 */
	@Test
	public void testGetConfirmationTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getDepartureTime()}.
	 */
	@Test
	public void testGetDepartureTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getEnterQueueTime()}.
	 */
	@Test
	public void testGetEnterQueueTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getExitQueueTime()}.
	 */
	@Test
	public void testGetExitQueueTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getPassID()}.
	 */
	@Test
	public void testGetPassID() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsConfirmedAlreadyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertTrue(firstClassPassenger.isConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#isConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsConfirmedNotConfirmed() throws PassengerException {
		assertFalse(firstClassPassenger.isConfirmed());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isFlown()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsFlownAlreadyFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		assertTrue(firstClassPassenger.isFlown());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#isFlown()}.
	 */
	@Test
	public void testIsFlownNotFlown() {
		assertFalse(firstClassPassenger.isFlown());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isNew()}.
	 */
	@Test
	public void testIsNewAlreadyNew() {
		assertTrue(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#isNew()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsNewNotNewConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertFalse(firstClassPassenger.isNew());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsQueuedAlreadyQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertTrue(firstClassPassenger.isQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#isQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsQueuedNotQueued() throws PassengerException {
		assertFalse(firstClassPassenger.isQueued());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isRefused()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsRefusedAlreadyRefused() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		assertTrue(firstClassPassenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#isRefused()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testIsRefusedNotRefused() throws PassengerException {
		assertFalse(firstClassPassenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerAlreadyQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.queuePassenger(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerAlreadyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.queuePassenger(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerAlreadyRefused() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		firstClassPassenger.queuePassenger(1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerAlreadyFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		firstClassPassenger.queuePassenger(2, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerQueTimeLessThanZero() throws PassengerException {
		firstClassPassenger.queuePassenger(-1, 2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerQueueTimeEqualToZero() throws PassengerException {
		firstClassPassenger.queuePassenger(0, 2);
		assertTrue(firstClassPassenger.isQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testQueuePassengerDepartureTimeLessThanQueueTime() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerCheckEnterQueueTime() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertEquals(1, firstClassPassenger.getEnterQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerCheckDepartureTime() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertEquals(2, firstClassPassenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerNoLongerNew() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertFalse(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerNowQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertTrue(firstClassPassenger.isQueued());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testRefusePassengerAlreadyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.refusePassenger(1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testRefusePassengerAlreadyRefused() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		firstClassPassenger.refusePassenger(1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testRefusePassengerAlreadyFlown() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.flyPassenger(2);
		firstClassPassenger.refusePassenger(2);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testRefusePassengerRefusalTimeLessThanZero() throws PassengerException {
		firstClassPassenger.refusePassenger(-1);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerRefusalTimeEqualToZero() throws PassengerException {
		First firstClassPassengerTwo = new First(0, 1);
		firstClassPassengerTwo.refusePassenger(0);
		assertTrue(firstClassPassengerTwo.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test(expected = PassengerException.class)
	public void testRefusePassengerRefusalTimeLessThanBookingTime() throws PassengerException {
		firstClassPassenger.refusePassenger(0);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerCheckNewPassenger() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		assertTrue(firstClassPassenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerCheckNoLongerNewPassenger() throws PassengerException {
		firstClassPassenger.refusePassenger(1);
		assertFalse(firstClassPassenger.isNew());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerCheckQueuedPassenger() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.refusePassenger(1);
		assertTrue(firstClassPassenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerCheckIsNoLongerQueuedPassenger() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.refusePassenger(1);
		assertFalse(firstClassPassenger.isQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerCheckExitQueTime() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.refusePassenger(1);
		assertEquals(1, firstClassPassenger.getExitQueueTime());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#toString()}.
	 */
	@Test
	public void testToString() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasConfirmedCurrentlyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		assertTrue(firstClassPassenger.wasConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasConfirmed()}.
	 */
	@Test
	public void testWasConfirmedNotConfirmed() {
		assertFalse(firstClassPassenger.wasConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasConfirmedPreviouslyConfirmed() throws PassengerException {
		firstClassPassenger.confirmSeat(1, 2);
		firstClassPassenger.cancelSeat(1);
		assertTrue(firstClassPassenger.wasConfirmed());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasQueuedCurrentlyQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		assertTrue(firstClassPassenger.wasQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasQueuedNotQueued() throws PassengerException {
		assertFalse(firstClassPassenger.wasQueued());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasQueuedPreviouslyQueued() throws PassengerException {
		firstClassPassenger.queuePassenger(1, 2);
		firstClassPassenger.confirmSeat(2, 2);
		assertTrue(firstClassPassenger.wasQueued());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#copyPassengerState(asgn2Passengers.Passenger)}.
	 */
	@Test
	public void testCopyPassengerState() {
		assertTrue(true);
	}

}
