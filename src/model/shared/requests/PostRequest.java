package model.shared.requests;

public class PostRequest extends Request {
	private static final long serialVersionUID = 1L;
	public Object Object;
	public int Id;
	
	public PostRequest(Object Object, int Id) {
		this.Object = Object;
		this.Id = Id;
		super.requestType = RequestType.POST;
	}

}
