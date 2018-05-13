package model.shared.requests;

//For explanation, see the super class
public class CancelReservationRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int reservationId;

	public CancelReservationRequest(int reservationId) {
		this.reservationId = reservationId;
		this.requestType = RequestType.CancelReservation;
	}
}
