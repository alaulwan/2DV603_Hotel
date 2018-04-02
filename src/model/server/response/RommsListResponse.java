package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer;
import model.shared.Room;
import model.shared.filters.roomsFilters.RoomsFilter;

public class RommsListResponse extends Response {
	public ArrayList <Room> roomList;
	
	public RommsListResponse (ArrayList <RoomsFilter> roomsFilterList) {
		roomList = HotelServer.hotel.getRoomsAndSuitesList();
		if (roomsFilterList != null) {
			for (RoomsFilter filter : roomsFilterList) {
				filter.applyRoomsFilter(roomList, null);
			}
		}
		super.object = roomList;
	}
	
}
