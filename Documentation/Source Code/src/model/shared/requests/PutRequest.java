package model.shared.requests;

//For explanation, see the super class
public class PutRequest extends Request {
	private static final long serialVersionUID = 1L;
	public Object Object;

	public PutRequest(Object Object) {
		this.Object = Object;
		super.requestType = RequestType.PUT;
	}

}
