package model.server.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.server.HotelServer;
import model.shared.Room;
import model.shared.filters.roomsFilters.RoomsFilter;
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
