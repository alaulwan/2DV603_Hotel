package model.shared.filters.roomsFilters;

import java.util.ArrayList;
import model.shared.Reservation;
import model.shared.Room;

public class AirViewBalSmoRoomsFilter implements RoomsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean airCon;
	public boolean view;
	public boolean balcony;
	public boolean smoking;
	
	public AirViewBalSmoRoomsFilter () {
		
	}
	
	public AirViewBalSmoRoomsFilter (boolean airCon, boolean view, boolean balcony, boolean smoking) {
		this.airCon = airCon;
		this.view = view;
		this.balcony = balcony;
		this.smoking = smoking;
	}
	
	@Override
	public ArrayList<Room> applyRoomsFilter(ArrayList<Room> roomList, ArrayList<Reservation> reservationsList) {
		for (int i=0; i< roomList.size(); i++) {
			if (view && !roomList.get(i).isView()) {
				roomList.remove(i);
				i--;
			}
			else if (smoking && !roomList.get(i).isSmoking()) {
				roomList.remove(i);
				i--;
			}
			else if (balcony && !roomList.get(i).isBalcony()) {
				roomList.remove(i);
				i--;
			}
			else if (airCon && !roomList.get(i).isAirCon()) {
				roomList.remove(i);
				i--;
			}
		}
		return roomList;
	}

}
