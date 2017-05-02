package asgn2Tests;

import static org.junit.Assert.*;

import asgn2Aircraft.*;
import asgn2Passengers.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pickford
 */
public class A380Tests {

    Aircraft plane;
    Passenger p;
    
    @Before
    public void SetUp() throws AircraftException, PassengerException {
        plane = new A380("ABC123", 15, 15, 15, 15, 15);
        p = new Economy(1, 5);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int)}
     */
    @Test
    public void testAircraftConstructorPassesSimpleConstructorLowerBoundaryDepartureTime() throws AircraftException{
        Aircraft plane = new A380("ABC123", 1);
        assertNotNull(plane);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNullFlightCodeSimpleConstructor() throws AircraftException{
        Aircraft plane = new A380(null, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNullDepartureTimeSimpleConstrcutor() throws AircraftException{
        Aircraft plane = new A380("ABC123", 0);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativeDepartureTimeSimpleConstructor() throws AircraftException{
        Aircraft plane = new A380("ABC123", -40);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     */
    @Test
    public void testAircraftConstructorPassesZeroEconomy() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 0, 1, 1, 1);
        assertNotNull(plane); //Using flight empty just to prove plane exists
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     */
    @Test
    public void testAircraftConstructorPassesZeroPremium() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, 0, 1, 1);
        assertNotNull(plane); //Using flight empty just to prove plane exists
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     */
    @Test
    public void testAircraftConstructorPassesZeroBusiness() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, 1, 0, 1);
        assertNotNull(plane); //Using flight empty just to prove plane exists
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     */
    @Test
    public void testAircraftConstructorPassesZeroFirst() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, 1, 1, 0);
        assertNotNull(plane); //Using flight empty just to prove plane exists
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     */
    @Test
    public void testAircraftConstructorPassesLowerBoundaryDepartureTime() throws AircraftException{
        Aircraft plane = new A380("ABC123", 1, 1, 1, 1, 1);
        assertNotNull(plane); //Using flight empty just to prove plane was successfully created
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNullFlightCode() throws AircraftException{
        Aircraft plane = new A380(null, 10, 1, 1, 1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNullDepartureTime() throws AircraftException{
        Aircraft plane = new A380("ABC123", 0, 1, 1, 1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativeDepartureTime() throws AircraftException{
        Aircraft plane = new A380("ABC123", -40, 1, 1, 1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativeFirst() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, -1, 1, 1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativeBusiness() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, -1, 1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativePremium() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, 1, -1, 1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws AircraftException
     */
    @Test(expected= AircraftException.class)
    public void testAircraftConstructorThrowsExceptionNegativeEconomy() throws AircraftException{
        Aircraft plane = new A380("ABC123", 10, 1, 1, 1, -1);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingCancellationTimePassesLowerBoundary() throws AircraftException, PassengerException {
        Passenger p2 = new Premium(1, 5);
        plane.confirmBooking(p2, 1);
        plane.cancelBooking(p2, 1);

        assertEquals(0, plane.getNumPremium());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
   @Test
    public void testCancelBookingEconomy() throws AircraftException, PassengerException {
       plane.confirmBooking(p, 10);
       plane.cancelBooking(p, 10);
       
       assertEquals(0, plane.getNumEconomy());
    }
   
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingPremium() throws AircraftException, PassengerException {
        Passenger p2 = new Premium(1, 5);
        plane.confirmBooking(p2, 10);
        plane.cancelBooking(p2, 10);

        assertEquals(0, plane.getNumPremium());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingBusiness() throws AircraftException, PassengerException {
        Passenger p2 = new Business(1, 5);
        plane.confirmBooking(p2, 10);
        plane.cancelBooking(p2, 10);

        assertEquals(0, plane.getNumBusiness());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingFirst() throws AircraftException, PassengerException {
        Passenger p2 = new First(1, 5);
        plane.confirmBooking(p2, 10);
        plane.cancelBooking(p2, 10);

        assertEquals(0, plane.getNumEconomy());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingMultipleInOneClass() throws AircraftException, PassengerException {
        p = new Economy(1, 5);
        Passenger p2 = new Economy(1, 5);
        Passenger p3 = new Economy(1, 5);
        Passenger p4 = new Economy(1, 5);

        //Adds 4 Economy class passengers
        plane.confirmBooking(p, 10);
        plane.confirmBooking(p2, 10);
        plane.confirmBooking(p3, 10);
        plane.confirmBooking(p4, 10);

        //Removes one
        plane.cancelBooking(p3, 10);

        //Tests there are 3 left
        assertEquals(3, plane.getNumEconomy());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test
    public void testCancelBookingMultipleCancelsOutOfOrder() throws AircraftException, PassengerException {
        p = new Economy(1, 5);
        Passenger p2 = new Economy(1, 5);
        Passenger p3 = new Economy(1, 5);
        Passenger p4 = new Economy(1, 5);

        plane.confirmBooking(p, 10);
        plane.confirmBooking(p2, 10);
        plane.confirmBooking(p3, 10);
        plane.cancelBooking(p3, 10);
        plane.confirmBooking(p4, 10);
        plane.cancelBooking(p, 10);

        assertEquals(2, plane.getNumEconomy());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#A380(String, int, int, int, int, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testCancelBookingThrowsPassengerExceptionPassengerNotConfirmed() throws PassengerException, AircraftException {
        p.cancelSeat(10);
        plane.cancelBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testCancelBookingThrowsPassengerExceptionNegativeCancellationTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 10);
        plane.cancelBooking(p, -10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testCancelBookingThrowsPassengerExceptionInvalidCancellationTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 10);
        plane.cancelBooking(p, 'i');
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#cancelBooking(Passenger, int)}
     */
    @Test (expected = AircraftException.class)
    public void testCancelBookingThrowsAircraftExceptionPassengerNotOnPlane() throws PassengerException, AircraftException {
        Passenger p2 = new Economy(1, 12);
        plane.confirmBooking(p, 10);

        //Tries to remove a passenger not confirmed on the plane
        plane.cancelBooking(p2, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testConfirmBookingCancellationTimePassesLowerBoundary() throws AircraftException, PassengerException {
        Passenger p2 = new Premium(1, 5);
        plane.confirmBooking(p2, 1);

        assertEquals(1, plane.getNumPremium());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testConfirmBookingEconomy() throws AircraftException, PassengerException {
        plane.confirmBooking(p, 10);
        assertEquals(1, plane.getNumEconomy());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testConfirmBookingPremium() throws AircraftException, PassengerException {
        plane.confirmBooking(new Premium(1, 5), 10);;
        assertEquals(1, plane.getNumPremium());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testConfirmBookingBusiness() throws AircraftException, PassengerException {
        plane.confirmBooking(new Business(1, 5), 10);
        assertEquals(1, plane.getNumBusiness());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testConfirmBookingFirst() throws AircraftException, PassengerException {
        plane.confirmBooking(new First(1, 5), 10);
        assertEquals(1, plane.getNumFirst());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     */
    @Test
    public void testMultipleConfirmBookingVariousClasses() throws AircraftException, PassengerException{
        plane.confirmBooking(p, 10);
        plane.confirmBooking(new Premium(1, 5), 10);
        plane.confirmBooking(new Business(1, 5), 10);
        plane.confirmBooking(new First(1, 5), 10);

        assertEquals(4, plane.getPassengers().size());
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionPassengerNotConfirmed() throws PassengerException, AircraftException {
        p.cancelSeat(10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeConfirmationTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, -10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionInvalidConfirmationTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 'm');
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeDepartureTime() throws PassengerException, AircraftException {
        Passenger p1 = new Economy(1, -10);
        plane.confirmBooking(p1, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNullDepartureTimeEconomy() throws PassengerException, AircraftException {
        Passenger p1 = new Economy(1, 0);
        plane.confirmBooking(p1, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeBookingTimeEconomy() throws PassengerException, AircraftException {
        Passenger p = new Economy(-1, 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionInvalidBookingTimeEconomy() throws PassengerException, AircraftException {
        Passenger p = new Economy('b', 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeBookingTimePremium() throws PassengerException, AircraftException {
        Passenger p = new Premium(-1, 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionInvalidBookingTimePremium() throws PassengerException, AircraftException {
        Passenger p = new Premium('b', 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeBookingTimeBusiness() throws PassengerException, AircraftException {
        Passenger p = new Business(-1, 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionInvalidBookingTimeBusiness() throws PassengerException, AircraftException {
        Passenger p = new Business('b', 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionNegativeBookingTimeFirst() throws PassengerException, AircraftException {
        Passenger p = new First(-1, 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testConfirmBookingThrowsPassengerExceptionInvalidBookingTimeFirst() throws PassengerException, AircraftException {
        Passenger p = new First('b', 10);
        plane.confirmBooking(p, 10);
    }
    
    /**
     * Test method for {@link asgn2Aircraft.A380#confirmBooking(Passenger, int)}
     * @throws AircraftException
     */
    @Test (expected = AircraftException.class)
    public void testConfirmBookingThrowsAircraftExceptionCapacityExceeded() throws PassengerException, AircraftException {
        Aircraft plane2 = new A380("ABC123", 15, 1, 1, 1, 1);
        Passenger p2 = new Economy(1, 5);
        
        //Throws exception because Economy has a capacity of 1
        plane2.confirmBooking(p, 10);
        plane2.confirmBooking(p2, 10);
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyWithNoConfirmations() throws AircraftException, PassengerException {
        assertTrue(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyReturnsFalseWithEconomyPassenger() throws AircraftException, PassengerException {
        plane.confirmBooking(p, 8);
        assertFalse(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyReturnsFalseWithPremiumPassenger() throws AircraftException, PassengerException {
        Passenger p2 = new Premium(1, 5);
        plane.confirmBooking(p2, 8);
        assertFalse(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyReturnsFalseWithBusinessPassenger() throws AircraftException, PassengerException {
        Passenger p2 = new Business(1, 5);
        plane.confirmBooking(p2, 8);
        assertFalse(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyReturnsFalseWithFirstPassenger() throws AircraftException, PassengerException {
        Passenger p2 = new First(1, 5);
        plane.confirmBooking(p2, 8);
        assertFalse(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyAfterEconomyCancellation() throws AircraftException, PassengerException {
        plane.confirmBooking(p, 8);
        plane.cancelBooking(p, 8);
        assertTrue(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyAfterPremiumCancellation() throws AircraftException, PassengerException {
        Passenger p2 = new Premium(1, 5);
        plane.confirmBooking(p2, 8);
        plane.cancelBooking(p2, 8);
        assertTrue(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyAfterBusinessCancellation() throws AircraftException, PassengerException {
        Passenger p2 = new Business(1, 5);
        plane.confirmBooking(p2, 8);
        plane.cancelBooking(p2, 8);
        assertTrue(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightEmpty()}
     */
    @Test
    public void testFlightEmptyAfterFirstCancellation() throws AircraftException, PassengerException {
        Passenger p2 = new First(1, 5);
        plane.confirmBooking(p2, 8);
        plane.cancelBooking(p2, 8);
        assertTrue(plane.flightEmpty());
    }
    
    /**
     * Test method for {@link A380#flightFull()}
     */
    @Test
    public void testFlightFullReturnsTrue() throws AircraftException, PassengerException {
        Aircraft plane2 = new A380("ABC123", 15, 1, 1, 1, 1);
        Passenger p1 = new Economy(1, 5);
        Passenger p2 = new Business(1, 5);
        Passenger p3 = new First(1, 5);
        Passenger p4 = new Premium(1, 5);
        plane2.confirmBooking(p1, 8);
        plane2.confirmBooking(p2, 8);
        plane2.confirmBooking(p3, 8);
        plane2.confirmBooking(p4, 8);
        assertTrue(plane2.flightFull());
    }
    
    /**
     * Test method for {@link A380#flightFull()}
     */
    @Test
    public void testFlightFullReturnsTrueWithCancellations() throws AircraftException, PassengerException {
        Aircraft plane2 = new A380("ABC123", 15, 1, 1, 1, 1);
        Passenger p1 = new Economy(1, 5);
        Passenger p2 = new Business(1, 5);
        Passenger p3 = new First(1, 5);
        Passenger p4 = new Premium(1, 5);
        plane2.confirmBooking(p1, 8);
        plane2.confirmBooking(p2, 8);
        plane2.confirmBooking(p3, 8);
        plane2.confirmBooking(p4, 8);
        plane2.cancelBooking(p1, 8);
        plane2.confirmBooking(new Economy(1, 5), 8);
        assertTrue(plane2.flightFull());
    }
    
    /**
     * Test method for {@link A380#flightFull()}
     */
    @Test
    public void testFlightFullReturnsFalseWithCancellation() throws AircraftException, PassengerException {
        Aircraft plane2 = new A380("ABC123", 15, 1, 1, 1, 1);
        Passenger p1 = new Economy(1, 5);
        Passenger p2 = new Business(1, 5);
        Passenger p3 = new First(1, 5);
        Passenger p4 = new Premium(1, 5);
        plane2.confirmBooking(p1, 8);
        plane2.confirmBooking(p2, 8);
        plane2.confirmBooking(p3, 8);
        plane2.confirmBooking(p4, 8);
        plane2.cancelBooking(p1, 8);
        assertFalse(plane2.flightFull());
    }
    
    /**
     * Test method for {@link A380#getPassengers()}
     */
    @Test
    public void testGetPassengersWhenTrue() throws AircraftException, PassengerException {
        plane.confirmBooking(p, 8);
        List<Passenger> pList = new ArrayList<>();
        pList.add(p);

        assertEquals(pList, plane.getPassengers());
    }
    
    /**
     * Test method for {@link A380#getPassengers()}
     */
    @Test
    public void testGetPassengersWhenFalse() throws AircraftException, PassengerException {
        Passenger p2 = new Economy(1, 5);
        plane.confirmBooking(p, 8);

        List<Passenger> pList = new ArrayList<>();
        pList.add(p);
        pList.add(p2);

        assertNotEquals(pList, plane.getPassengers());
    }
    
    /**
     * Test method for {@link A380#hasPassenger(Passenger)}
     */
    @Test
    public void testHasPassengerWhenTrue() throws AircraftException, PassengerException {
        Passenger p1 = new Economy(1, 5);
        plane.confirmBooking(p1, 8);
        assertTrue(plane.hasPassenger(p1));
    }
    
    /**
     * Test method for {@link A380#hasPassenger(Passenger)}
     */
    @Test
    public void testHasPassengerWhenFalse() throws AircraftException, PassengerException {
        Passenger p2 = new Business(1, 5);
        plane.confirmBooking(p, 8);
        assertFalse(plane.hasPassenger(p2));
    }
    
    /**
     * Test method for {@link A380#flyPassengers(int)}
     */
    @Test
    public void testFlyPassengers() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 5);
        plane.flyPassengers(10);

        assertEquals(10, p.getDepartureTime());
    }
    
    /**
     * Test method for {@link A380#flyPassengers(int)}
     */
    @Test
    public void testFlyPassengersPassesLowerBoundary() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 1);
        plane.flyPassengers(1);

        assertEquals(1, p.getDepartureTime());
    }
    
    /**
     * Test method for {@link A380#flyPassengers(int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testFlyPassengersFailsZeroDepartureTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 1);
        plane.flyPassengers(0);
    }
    
    /**
     * Test method for {@link A380#flyPassengers(int)}
     * @throws PassengerException
     */
    @Test (expected = PassengerException.class)
    public void testFlyPassengersFailsNegativeDepartureTime() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 1);
        plane.flyPassengers(-10);
    }
    
    /**
     * Test method for {@link A380#upgradeBookings()}
     */
    @Test
    public void testUpgradeFromEconomyToPremium() throws PassengerException, AircraftException {
        plane.confirmBooking(p, 5);
        String oldPassID = p.getPassID();
        plane.upgradeBookings();
        assertEquals("P(U) " + oldPassID, p.getPassID());
    }
    
    /**
     * Test method for {@link A380#upgradeBookings()}
     */
    @Test
    public void testUpgradeFromPremiumToBusiness() throws PassengerException, AircraftException {
        Passenger p2 = new Premium(1, 5);
        String oldPassID = p2.getPassID();
        plane.confirmBooking(p2, 5);
        plane.upgradeBookings();
        assertEquals("J(U) " + oldPassID, p2.getPassID());
    }
    
    /**
     * Test method for {@link A380#upgradeBookings()}
     */
    @Test
    public void testUpgradeFromBusinessToFirst() throws PassengerException, AircraftException {
        Passenger p2 = new Business(1, 5);
        String oldPassID = p2.getPassID();
        plane.confirmBooking(p2, 5);
        plane.upgradeBookings();
        assertEquals("F(U) " + oldPassID, p2.getPassID());
    }
    
    /**
     * Test method for {@link A380#upgradeBookings()}
     */
    @Test
    public void testUpgradeFromFirstToFirst() throws PassengerException, AircraftException {
        Passenger p2 = new First(1, 5);
        String oldPassID = p2.getPassID();
        plane.confirmBooking(p2, 5);
        plane.upgradeBookings();
        assertEquals(oldPassID, p2.getPassID());
    }
    
    /**
     * Test method for {@link A380#upgradeBookings()}
     */
    @Test
    public void testUpgradesAllPassengers() throws PassengerException, AircraftException {
        Passenger p2 = new First(1, 5);
        String oldPassID = p2.getPassID();
        Passenger p3 = new Business(1, 5);
        Passenger p4 = new Premium(1, 5);
        plane.confirmBooking(p, 5);
        plane.confirmBooking(p2, 5);
        plane.confirmBooking(p3, 5);
        plane.confirmBooking(p4, 5);
        plane.upgradeBookings();
        assertEquals(oldPassID, p2.getPassID());
    }
}