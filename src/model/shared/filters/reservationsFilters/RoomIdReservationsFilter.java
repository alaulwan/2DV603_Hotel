package model.shared.filters.reservationsFilters;

import java.util.ArrayList;

import model.shared.Reservation;

public class RoomIdReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	private int roomId;
	
	public RoomIdReservationsFilter(int customerId) {
		this.roomId = customerId;
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			if(reservationsList.get(i).getRoomId() != roomId) {
				reservationsList.remove(i);
				i--;
			}
		}
		return reservationsList;
	}

}
