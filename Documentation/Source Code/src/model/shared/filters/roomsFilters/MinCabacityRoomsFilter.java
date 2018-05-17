package model.shared.filters.roomsFilters;

import java.util.ArrayList;
import model.shared.Reservation;
import model.shared.Room;

public class MinCabacityRoomsFilter implements RoomsFilter {
	private static final long serialVersionUID = 1L;
	private int minCabacity;

	public MinCabacityRoomsFilter () {
	}
	
	public MinCabacityRoomsFilter (int minCabacity) {
		this.minCabacity = minCabacity;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (roomList.get(i).getMaxGuestCapacity() < minCabacity) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}
}
