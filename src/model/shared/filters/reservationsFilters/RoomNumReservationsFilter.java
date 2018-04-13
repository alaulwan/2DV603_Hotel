package model.shared.filters.reservationsFilters;

import java.util.ArrayList;
import model.shared.Reservation;

public class RoomNumReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	private int roomNum;
	
	public RoomNumReservationsFilter(int roomNum) {
		this.roomNum = roomNum;
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			if(reservationsList.get(i).getRoomNumber() != roomNum) {
				reservationsList.remove(i);
				i--;
			}
		}
		return reservationsList;
	}

}
