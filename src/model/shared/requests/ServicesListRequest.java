package model.shared.requests;

public class ServicesListRequest extends Request {
	private static final long serialVersionUID = 1L;

	public ServicesListRequest() {
		super.requestType = RequestType.GET_SERVICES;
	}

}
