package model.shared.filters.roomsFilters;

import java.time.LocalDate;
import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room;

public class ChekInOutDateRoomsFilter implements RoomsFilter {
	private static final long serialVersionUID = 1L;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	
	public ChekInOutDateRoomsFilter (LocalDate checkInDate, LocalDate checkOutDate) {
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList,  ArrayList<Reservation> reservationsList) {
		ArrayList<Integer> roomsToRemoved = new ArrayList<Integer>();
		for (Reservation rs : reservationsList) {
			boolean valid = checkInDate.isAfter(rs.checkOutDateAsLocalDate().minusDays(1)) || checkOutDate.isBefore(rs.checkInDateAsLocalDate().plusDays(1)) || rs.getReservationStatus()== ReservationStatus.CANCELED || rs.getReservationStatus()== ReservationStatus.CHECKED_OUT;
			if (!valid)
				roomsToRemoved.add(rs.getRoomId());
		}
		
		for (int i=0; i< roomList.size(); i++) {
			for (int roomId : roomsToRemoved) {
				if (roomList.get(i).getRoomId() == roomId) {
					roomList.remove(i);
					i--;
				}	
			}
		}
		return roomList;
	}
	
}
