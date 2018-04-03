package model.shared.filters.roomsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;

public class NumberRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int roomNum;

	public NumberRoomsFilter () {
	}
	
	public NumberRoomsFilter (int roomNum) {
		this.roomNum = roomNum;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (roomList.get(i).getRoomNum() != roomNum) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}

}
