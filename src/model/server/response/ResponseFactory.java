package model.server.response;

import model.shared.requests.Request;
import model.shared.requests.Request.RequestType;
import model.shared.requests.RoomsListRequest;

public class ResponseFactory {
	Request recievedRequest;
	Response response;

	public ResponseFactory(Request recievedRequest) {
		this.recievedRequest = recievedRequest;
	}

	public Response getResponse() {
		response=null;
		
		if (recievedRequest.requestType == RequestType.GET_ROOMS) {
			response = new RommsListResponse(((RoomsListRequest)recievedRequest).rFilList);
		}
		
		return response;
	}
}
