/**
 * 
 */
package asgn2Passengers;

/**
 * @author hogan and pickford
 *
 */
public class Economy extends Passenger {

	/**
	 * Economy Constructor (Partially Supplied)
	 * Passenger is created in New state, later given a Confirmed Economy Class reservation,
	 * Queued, or Refused booking if waiting list is full.
	 *
	 * @param bookingTime   <code>int</code> day of the original booking.
	 * @param departureTime <code>int</code> day of the intended flight.
	 * @throws PassengerException if invalid bookingTime or departureTime
	 * @see asgn2Passengers.Passenger#Passenger(int, int)
	 */
	public Economy(int bookingTime, int departureTime) throws PassengerException {
		super(bookingTime, departureTime);
		this.passID = "Y:" + this.passID;
	}

	@Override
	public String noSeatsMsg() {
		return "No seats available in Economy";
	}

	@Override
	public Passenger upgrade() {
		Passenger p = new Premium();
		copyPassengerState(p);
		this.passID = "P(U) " + this.passID;
		return p;
	}
}