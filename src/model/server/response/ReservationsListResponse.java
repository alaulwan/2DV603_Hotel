package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer;
import model.shared.Reservation;
import model.shared.filters.reservationsFilters.ReservationsFilter;

public class ReservationsListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList <Reservation> reservationsList;
	
	public ReservationsListResponse (ArrayList <ReservationsFilter> reservationsFilterList) {
		reservationsList = HotelServer.hotel.getReservationsList();
		if (reservationsFilterList != null) {
			for (ReservationsFilter filter : reservationsFilterList) {
				filter.applyReservationsFilter(reservationsList);
			}
		}
		super.object = reservationsList;
	}
}
