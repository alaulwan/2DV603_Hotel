package model.shared.requests;

public class PutRequest extends Request {
	private static final long serialVersionUID = 1L;
	public Object Object;

	public PutRequest(Object Object) {
		this.Object = Object;
		super.requestType = RequestType.PUT;
	}

}
