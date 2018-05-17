package model.shared.filters.roomsFilters;

import java.io.Serializable;
import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;

//This filter to search for rooms
public interface RoomsFilter extends Serializable {
	// When apply this method on rooms-list, it will remove all rooms that do not
	// much the filter from the given rooms-list
	public abstract ArrayList<Room> applyRoomsFilter (ArrayList<Room> roomList , ArrayList<Reservation> reservationsList);
	
}
