package model.shared.requests;

import java.io.Serializable;

// This class will be created by the client and then the client will send it to the server. When the server receive the request, it will check the request-type to generate the corresponding response
// This class is abstract. Each expended request has its own variable according to the desired request
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum RequestType {
		GET_ROOMS, GET_RESERVATIONS, GET_USERS, GET_BILLS, GET_SERVICES, PUT, POST, CheckIn, CheckOut, CancelReservation, DELETE
	}

	public RequestType requestType;

	public Request() {

	}

}
