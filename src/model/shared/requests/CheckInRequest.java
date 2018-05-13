package model.shared.requests;

//For explanation, see the super class
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
