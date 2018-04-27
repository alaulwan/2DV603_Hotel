package model.server.response;

import model.server.HotelServer.SavingThread;
import model.shared.Hotel;
import model.shared.requests.BillsListRequest;
import model.shared.requests.CancelReservationRequest;
import model.shared.requests.CheckInRequest;
import model.shared.requests.CheckOutRequest;
import model.shared.requests.CustomersListRequest;
import model.shared.requests.PostRequest;
import model.shared.requests.PutRequest;
import model.shared.requests.DeleteRequest;
import model.shared.requests.Request;
import model.shared.requests.Request.RequestType;
import model.shared.requests.ReservationsListRequest;
import model.shared.requests.RoomsListRequest;

public class ResponseFactory {
	public Hotel hotel;
	public SavingThread savingThread;
	Request recievedRequest;
	Response response;

	public ResponseFactory(Hotel hotel, SavingThread savingThread, Request recievedRequest) {
		this.hotel = hotel;
		this.savingThread = savingThread;
		this.recievedRequest = recievedRequest;
	}

	public Response getResponse() {
		if (recievedRequest.requestType == RequestType.PUT) {
			response = new PutResponse(hotel, savingThread, ((PutRequest) recievedRequest).Object);
		} else if (recievedRequest.requestType == RequestType.POST) {
			response = new PostResponse(hotel, savingThread, ((PostRequest) recievedRequest).Object, ((PostRequest) recievedRequest).Id);
		} else if (recievedRequest.requestType == RequestType.DELETE) {
			response = new DeleteResponse(hotel, savingThread, ((DeleteRequest) recievedRequest).Object);
		} else if (recievedRequest.requestType == RequestType.CheckIn) {
			response = new CheckInResponse(hotel, savingThread, ((CheckInRequest) recievedRequest).reservationId);
		} else if (recievedRequest.requestType == RequestType.CheckOut) {
			response = new CheckOutResponse(hotel, savingThread, ((CheckOutRequest) recievedRequest).reservationId);
		} else if (recievedRequest.requestType == RequestType.CancelReservation) {
			response = new CancelReservationResponse(hotel, savingThread, ((CancelReservationRequest) recievedRequest).reservationId);
		} else if (recievedRequest.requestType == RequestType.GET_ROOMS) {
			response = new RoomsListResponse(hotel, savingThread, ((RoomsListRequest) recievedRequest).rFilList);
		} else if (recievedRequest.requestType == RequestType.GET_RESERVATIONS) {
			response = new ReservationsListResponse(hotel, savingThread, ((ReservationsListRequest) recievedRequest).reservationsFilterList);
		} else if (recievedRequest.requestType == RequestType.GET_USERS) {
			response = new CustomersListResponse(hotel, savingThread, ((CustomersListRequest) recievedRequest).customersFilterList);
		} else if (recievedRequest.requestType == RequestType.GET_BILLS) {
			response = new BillsListResponse(hotel, savingThread, ((BillsListRequest) recievedRequest).billsFilterList);
		} else if (recievedRequest.requestType == RequestType.GET_SERVICES) {
			response = new ServicesListResponse(hotel, savingThread);
		}
		return response;
	}
}
