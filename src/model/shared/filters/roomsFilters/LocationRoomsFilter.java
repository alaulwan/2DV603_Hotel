package model.shared.filters.roomsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomLocation;

public class LocationRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RoomLocation roomLocation;

	public LocationRoomsFilter () {
	}
	
	public LocationRoomsFilter (RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (roomList.get(i).getRoomLocation() != roomLocation) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}

}
