package model.shared.requests;

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
