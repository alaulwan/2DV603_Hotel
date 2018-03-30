package model.shared.filters.roomsFilters;

import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;

public interface roomsFilter {

	public ArrayList<Room> applyRoomsFilter (ArrayList<Room> roomList , ArrayList<Reservation> reservationsList);
	
}
