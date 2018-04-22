package model.shared.requests;

public class CheckInRequest extends Request{
	public int reservationId;
	public CheckInRequest(int reservationId){
		this.reservationId = reservationId;
		this.requestType = RequestType.CheckIn;
	}
}
