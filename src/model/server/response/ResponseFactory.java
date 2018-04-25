package model.server.response;

import model.shared.requests.BillsListRequest;
import model.shared.requests.CancelReservationRequest;
import model.shared.requests.CheckInRequest;
import model.shared.requests.CheckOutRequest;
import model.shared.requests.CustomersListRequest;
import model.shared.requests.PostRequest;
import model.shared.requests.PutRequest;
import model.shared.requests.Request;
import model.shared.requests.Request.RequestType;
import model.shared.requests.ReservationsListRequest;
import model.shared.requests.RoomsListRequest;

public class ResponseFactory {
	Request recievedRequest;
	Response response;

	public ResponseFactory(Request recievedRequest) {
		this.recievedRequest = recievedRequest;
	}

	public Response getResponse() {
		response=null;
		
		if (recievedRequest.requestType == RequestType.PUT) {
			response = new PutResponse(((PutRequest)recievedRequest).Object);
		}
		else if (recievedRequest.requestType == RequestType.POST) {
			response = new PostResponse(((PostRequest)recievedRequest).Object, ((PostRequest)recievedRequest).Id);
		}
		else if (recievedRequest.requestType == RequestType.CheckIn) {
			response = new CheckInResponse(((CheckInRequest)recievedRequest).reservationId);
		}
		else if (recievedRequest.requestType == RequestType.CheckOut) {
			response = new CheckOutResponse(((CheckOutRequest)recievedRequest).reservationId);
		}
		else if (recievedRequest.requestType == RequestType.CancelReservation) {
			response = new CancelReservationResponse(((CancelReservationRequest)recievedRequest).reservationId);
		}
		else if (recievedRequest.requestType == RequestType.GET_ROOMS) {
			response = new RoomsListResponse(((RoomsListRequest)recievedRequest).rFilList);
		}
		else if (recievedRequest.requestType == RequestType.GET_RESERVATIONS){
			response = new ReservationsListResponse(((ReservationsListRequest)recievedRequest).reservationsFilterList);
			
		}
		else if (recievedRequest.requestType == RequestType.GET_USERS){
			response = new CustomersListResponse(((CustomersListRequest)recievedRequest).customersFilterList);
			
		}
		else if (recievedRequest.requestType == RequestType.GET_BILLS){
			response = new BillsListResponse(((BillsListRequest)recievedRequest).billsFilterList);
			
		}
		
		return response;
	}
}
