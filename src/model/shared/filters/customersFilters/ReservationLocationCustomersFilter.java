package model.shared.filters.customersFilters;

import java.util.ArrayList;

import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Room.RoomLocation;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;

public class ReservationLocationCustomersFilter implements CustomersFilter {
	private static final long serialVersionUID = 1L;
	public RoomLocation roomLocation;
	
	public ReservationLocationCustomersFilter (RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
	}
	
	@Override
	public ArrayList<Customer> applyCustomersFilter(ArrayList<Customer> customersList) {
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(roomLocation);
		for (int i=0; i< customersList.size(); i++) {
			ArrayList<Reservation> reservationsList = new ArrayList<Reservation>(customersList.get(i).getReservationsList());
			locationReservationsFilter.applyReservationsFilter(reservationsList);
			if (reservationsList.isEmpty()) {
				customersList.remove(i);
				i--;
			}
		}
		return customersList;
	}
}
