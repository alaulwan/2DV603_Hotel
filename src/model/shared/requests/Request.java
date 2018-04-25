package model.shared.requests;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum RequestType {
		GET_ROOMS, GET_RESERVATIONS, GET_USERS, GET_BILLS, GET_SERVICES, PUT, POST, CheckIn, CheckOut, CancelReservation, DELETE
	}

	public RequestType requestType;

	public Request() {

	}

}
