package model.client;

import java.util.ArrayList;
import model.shared.Connection;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.filters.customersFilters.CustomersFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.roomsFilters.LocationRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;
import model.shared.requests.CustomersListRequest;
import model.shared.requests.Request;
import model.shared.requests.Request.RequestType;
import model.shared.requests.ReservationsListRequest;
import model.shared.requests.RoomsListRequest;

public class ServerAPI {
	public static RoomLocation location ;
	
	public ServerAPI () {
		
	}
	
	public static ArrayList <Room> getAllRooms () {
		ArrayList<RoomsFilter> filterList= new ArrayList<RoomsFilter>() ;
		filterList.add(new LocationRoomsFilter(location));
		return getRoomsList(filterList);
	}
	
	public static ArrayList <Room> getRoomsList (ArrayList<RoomsFilter> reservationsFilterList) {
		Request request = new RoomsListRequest (reservationsFilterList);
		request.requestType = RequestType.GET_ROOMS;
		Connection c = new Connection();
		c.send(request);
		
		@SuppressWarnings("unchecked")
		ArrayList <Room> roomList = (ArrayList<Room>) c.receiveObject ();
		c.close();
		
		return roomList;
	}
	
	public static ArrayList <Reservation> getReservationsList (ArrayList<ReservationsFilter> reservationsFilterList) {
		Request request = new ReservationsListRequest (reservationsFilterList);
		request.requestType = RequestType.GET_RESERVATIONS;
		Connection c = new Connection();
		c.send(request);
		
		@SuppressWarnings("unchecked")
		ArrayList <Reservation> reservationsList = (ArrayList<Reservation>) c.receiveObject ();
		c.close();
		
		return reservationsList;
	}
	
	public static ArrayList <Customer> getCustomersList (ArrayList<CustomersFilter> customerssFilterList) {
		Request request = new CustomersListRequest (customerssFilterList);
		request.requestType = RequestType.GET_USERS;
		Connection c = new Connection();
		c.send(request);
		
		@SuppressWarnings("unchecked")
		ArrayList <Customer> CustomersList = (ArrayList<Customer>) c.receiveObject ();
		c.close();
		
		return CustomersList;
	}

}
