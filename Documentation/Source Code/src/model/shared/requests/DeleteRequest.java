package model.shared.requests;

//For explanation, see the super class
public class DeleteRequest extends Request {
	private static final long serialVersionUID = 1L;
	public Object Object;

	public DeleteRequest(Object Object) {
		this.Object = Object;
		super.requestType = RequestType.DELETE;
	}

}
