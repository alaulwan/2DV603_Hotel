package model.shared.requests;

public class CheckOutRequest extends Request{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int reservationId;
	public CheckOutRequest(int reservationId){
		this.reservationId = reservationId;
		this.requestType = RequestType.CheckOut;
	}
}
