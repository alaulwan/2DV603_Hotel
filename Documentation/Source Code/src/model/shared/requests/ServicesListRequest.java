package model.shared.requests;

//For explanation, see the super class
public class ServicesListRequest extends Request {
	private static final long serialVersionUID = 1L;

	public ServicesListRequest() {
		super.requestType = RequestType.GET_SERVICES;
	}

}
