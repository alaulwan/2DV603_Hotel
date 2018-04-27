package model.server.response;

import java.util.ArrayList;
import model.server.HotelServer.SavingThread;
import model.shared.Hotel;
import model.shared.Reservation;
import model.shared.filters.reservationsFilters.ReservationsFilter;

public class ReservationsListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Reservation> reservationsList;

	public ReservationsListResponse(Hotel hotel, SavingThread savingThread, ArrayList<ReservationsFilter> reservationsFilterList) {
		super (hotel, savingThread);
		reservationsList = new ArrayList<Reservation>(hotel.getReservationsList());
		if (reservationsFilterList != null) {
			for (ReservationsFilter filter : reservationsFilterList) {
				filter.applyReservationsFilter(reservationsList);
			}
		}
		super.object = reservationsList;
	}
}
