package model.shared.filters.reservationsFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Reservation;

//This filter to search for reservations
public interface ReservationsFilter extends Serializable {

	public ArrayList<Reservation> applyReservationsFilter (ArrayList<Reservation> reservationsList);
	
}
