package model.shared.filters.roomsFilters;

import java.util.ArrayList;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomSize;

public class SizeRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RoomSize sizeRoom;

	public SizeRoomsFilter () {
	}
	
	public SizeRoomsFilter (RoomSize sizeRoom) {
		this.sizeRoom = sizeRoom;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (roomList.get(i).getRoomSize() != sizeRoom) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}

}
