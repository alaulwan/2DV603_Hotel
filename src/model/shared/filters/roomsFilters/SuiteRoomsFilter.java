package model.shared.filters.roomsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;
import model.shared.Suite;

public class SuiteRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int numberOfRooms;

	public SuiteRoomsFilter () {
	}
	
	public SuiteRoomsFilter (int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (!roomList.get(i).isSuite()||(numberOfRooms>0 && ((Suite)roomList.get(i)).getNumberOfRooms()!=numberOfRooms)) {
				roomList.remove(i);
				i--;
			}
			/*else if (numberOfRooms>0 && ((Suite) roomList.get(i)).getNumberOfRooms() != numberOfRooms) {
				roomList.remove(i);
				i--;
			}*/
		}
		return roomList;
	}
}
