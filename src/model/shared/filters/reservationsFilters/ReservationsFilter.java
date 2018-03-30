package model.shared.filters.reservationsFilters;

import java.util.ArrayList;

import model.shared.Reservation;

public interface ReservationsFilter {

	public ArrayList<Reservation> applyReservationsFilter (ArrayList<Reservation> reservationsList);
	
}
