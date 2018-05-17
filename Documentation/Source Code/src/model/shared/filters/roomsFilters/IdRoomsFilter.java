package model.shared.filters.roomsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;

public class IdRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int roomId;

	public IdRoomsFilter () {
	}
	
	public IdRoomsFilter (int roomId) {
		this.roomId = roomId;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (roomList.get(i).getRoomId() != roomId) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}

}
