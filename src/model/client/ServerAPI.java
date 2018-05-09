package model.client;

import java.util.ArrayList;

import model.shared.Bill;
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

/**
 * The Class ServerAPI.
 */
public class ServerAPI {
	
	/** The location. */
	public RoomLocation location;
	
	/** The Remote IP. */
	public String RemoteIP;

	/**
	 * Instantiates a new server API.
	 *
	 * @param RemoteIP the remote IP
	 */
	public ServerAPI(String RemoteIP) {
		this.RemoteIP = RemoteIP;
	}

	/**
	 * Creates new object (Reservation, Customer) to the server.
	 *
	 * @param object the object (Reservation, Customer).
	 * @return true, if successful
	 */
	public boolean put(Object object) {
		Request request = new PutRequest(object);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean addedSuccess = (boolean) c.receiveObject();
		c.close();
		return addedSuccess;
	}

	/**
	 * Modifies an existence object (Reservation, Customer, Bill) in the server.
	 *
	 * @param object the object (Reservation, Customer, Bill).
	 * @param Id the object id
	 * @return true, if successful
	 */
	public boolean post(Object object, int Id) {
		Request request = new PostRequest(object, Id);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean addedSuccess = (boolean) c.receiveObject();
		c.close();
		return addedSuccess;
	}

	/**
 	 * Deletes an existence object (Reservation, Customer) from the server.
	 *
	 * @param object the object (Reservation, Customer).
	 * @return true, if successful
	 */
	public boolean delete(Object object) {
		Request request = new DeleteRequest(object);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean deletingSuccess = (boolean) c.receiveObject();
		c.close();
		return deletingSuccess;
	}

	/**
	 * Checks-in a pending reservation in the server.
	 *
	 * @param reservationId the reservation id
	 * @return true, if successful
	 */
	public boolean checkIn(int reservationId) {
		Request request = new CheckInRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean checkedInSuccess = (boolean) c.receiveObject();
		c.close();
		return checkedInSuccess;
	}

	/**
	 * Checks-out a checked-in reservation in the server.
	 *
	 * @param reservationId the reservation id
	 * @return true, if successful
	 */
	public boolean checkOut(int reservationId) {
		Request request = new CheckOutRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean checkedOutSuccess = (boolean) c.receiveObject();
		c.close();
		return checkedOutSuccess;
	}

	/**
	 * Cancels a pending reservation in the server.
	 *
	 * @param reservationId the reservation id
	 * @return true, if successful
	 */
	public boolean cancelReservation(int reservationId) {
		Request request = new CancelReservationRequest(reservationId);
		Connection c = new Connection(RemoteIP);
		c.send(request);
		boolean cancelReservationSuccess = (boolean) c.receiveObject();
		c.close();
		return cancelReservationSuccess;
	}

	/**
	 * Gets an ArrayList contains all the rooms and suites of the hotel from the server.
	 *
	 * @return all the rooms and suites
	 */
	public ArrayList<Room> getAllRooms() {
		ArrayList<RoomsFilter> filterList = new ArrayList<RoomsFilter>();
		filterList.add(new LocationRoomsFilter(location));
		return getRoomsList(filterList);
	}

	/**
	 * Gets an ArrayList of the rooms and suites based on the given filters from the server.
	 *
	 * @param roomsFilterList the rooms filters list
	 * @return the filtered rooms list
	 */
	public ArrayList<Room> getRoomsList(ArrayList<RoomsFilter> roomsFilterList) {
		Request request = new RoomsListRequest(roomsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Room> roomsList = (ArrayList<Room>) c.receiveObject();
		c.close();

		return roomsList;
	}

	/**
	 * Gets an ArrayList of the reservations based on the given filters from the server.
	 *
	 * @param reservationsFilterList the reservations filters list
	 * @return the filtered reservations list
	 */
	public ArrayList<Reservation> getReservationsList(ArrayList<ReservationsFilter> reservationsFilterList) {
		Request request = new ReservationsListRequest(reservationsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Reservation> reservationsList = (ArrayList<Reservation>) c.receiveObject();
		c.close();

		return reservationsList;
	}

	/**
	 * Gets an ArrayList of the customers based on the given filters from the server.
	 *
	 * @param customerssFilterList the customers filters list
	 * @return the filtered customers list
	 */
	public ArrayList<Customer> getCustomersList(ArrayList<CustomersFilter> customerssFilterList) {
		Request request = new CustomersListRequest(customerssFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Customer> CustomersList = (ArrayList<Customer>) c.receiveObject();
		c.close();

		return CustomersList;
	}

	/**
	 * Gets an ArrayList of the bills based on the given filters from the server.
	 *
	 * @param BillsFilterList the bills filters list
	 * @return the filtered bills list
	 */
	public ArrayList<Bill> getBillsList(ArrayList<BillsFilter> BillsFilterList) {
		Request request = new BillsListRequest(BillsFilterList);
		Connection c = new Connection(RemoteIP);
		c.send(request);

		@SuppressWarnings("unchecked")
		ArrayList<Bill> billsList = (ArrayList<Bill>) c.receiveObject();
		c.close();

		return billsList;
	}

	/**
	 * Gets the services list from the server.
	 *
	 * @return the services list
	 */
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
