package model.shared.requests;

public class CheckInRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int reservationId;

	public CheckInRequest(int reservationId) {
		this.reservationId = reservationId;
		this.requestType = RequestType.CheckIn;
	}
}
