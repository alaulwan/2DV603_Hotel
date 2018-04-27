package model.client;

import java.util.ArrayList;

import model.shared.Bill;
import model.shared.Connection;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.Service;
import model.shared.filters.billsFilters.BillsFilter;
import model.shared.filters.customersFilters.CustomersFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.roomsFilters.LocationRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;
import model.shared.requests.BillsListRequest;
import model.shared.requests.CancelReservationRequest;
import model.shared.requests.CheckInRequest;
import model.shared.requests.CheckOutRequest;
import model.shared.requests.CustomersListRequest;
import model.shared.requests.DeleteRequest;
import model.shared.requests.PostRequest;
import model.shared.requests.PutRequest;
import model.shared.requests.Request;
import model.shared.requests.ReservationsListRequest;
import model.shared.requests.RoomsListRequest;
import model.shared.requests.ServicesListRequest;

public class ServerAPI {
	public RoomLocation location;
	public String RemoteIP;

	public ServerAPI(String RemoteIP) {
		this.RemoteIP = RemoteIP;
	}

	public boolean put(Object object) {
		Request request = new PutRequest(object);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean addedSuccess = (boolean) c.receiveObject();
		c.close();
		return addedSuccess;
	}

	public boolean post(Object object, int Id) {
		Request request = new PostRequest(object, Id);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean addedSuccess = (boolean) c.receiveObject();
		c.close();
		return addedSuccess;
	}

	public boolean delete(Object object) {
		Request request = new DeleteRequest(object);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean deletingSuccess = (boolean) c.receiveObject();
		c.close();
		return deletingSuccess;
	}

	public boolean checkIn(int reservationId) {
		Request request = new CheckInRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean checkedInSuccess = (boolean) c.receiveObject();
		c.close();
		return checkedInSuccess;
	}

	public boolean checkOut(int reservationId) {
		Request request = new CheckOutRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean checkedOutSuccess = (boolean) c.receiveObject();
		c.close();
		return checkedOutSuccess;
	}

	public boolean cancelReservation(int reservationId) {
		Request request = new CancelReservationRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean cancelReservationSuccess = (boolean) c.receiveObject();
		c.close();
		return cancelReservationSuccess;
	}

	public ArrayList<Room> getAllRooms() {
		ArrayList<RoomsFilter> filterList = new ArrayList<RoomsFilter>();
		filterList.add(new LocationRoomsFilter(location));
		return getRoomsList(filterList);
	}

	public ArrayList<Room> getRoomsList(ArrayList<RoomsFilter> reservationsFilterList) {
		Request request = new RoomsListRequest(reservationsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Room> roomList = (ArrayList<Room>) c.receiveObject();
		c.close();

		return roomList;
	}

	public ArrayList<Reservation> getReservationsList(ArrayList<ReservationsFilter> reservationsFilterList) {
		Request request = new ReservationsListRequest(reservationsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Reservation> reservationsList = (ArrayList<Reservation>) c.receiveObject();
		c.close();

		return reservationsList;
	}

	public ArrayList<Customer> getCustomersList(ArrayList<CustomersFilter> customerssFilterList) {
		Request request = new CustomersListRequest(customerssFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Customer> CustomersList = (ArrayList<Customer>) c.receiveObject();
		c.close();

		return CustomersList;
	}

	public ArrayList<Bill> getBillsList(ArrayList<BillsFilter> BillsFilterList) {
		Request request = new BillsListRequest(BillsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Bill> billsList = (ArrayList<Bill>) c.receiveObject();
		c.close();

		return billsList;
	}

	public ArrayList<Service> getServicesList() {
		Request request = new ServicesListRequest();
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Service> servicesList = (ArrayList<Service>) c.receiveObject();
		c.close();

		return servicesList;
	}

}
