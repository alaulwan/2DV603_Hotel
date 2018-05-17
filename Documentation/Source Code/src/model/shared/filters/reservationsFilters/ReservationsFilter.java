package model.shared.filters.reservationsFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Reservation;

//This filter to search for reservations
public interface ReservationsFilter extends Serializable {
	// When apply this method on reservations-list, it will remove all reservations that do not
	// much the filter from the given reservations-list
	public ArrayList<Reservation> applyReservationsFilter (ArrayList<Reservation> reservationsList);
	
}
