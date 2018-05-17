package model.shared.filters.reservationsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room.RoomLocation;

public class LocationReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	public RoomLocation roomLocation;
	
	public LocationReservationsFilter(RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			if(reservationsList.get(i).getRoomLocation() != roomLocation) {
				reservationsList.remove(i);
				i--;
			}
		}
		return reservationsList;
	}

}
