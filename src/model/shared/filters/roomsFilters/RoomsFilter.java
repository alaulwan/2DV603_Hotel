package model.shared.filters.roomsFilters;

import java.io.Serializable;
import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;

public interface RoomsFilter extends Serializable {
	public abstract ArrayList<Room> applyRoomsFilter (ArrayList<Room> roomList , ArrayList<Reservation> reservationsList);
	
}
