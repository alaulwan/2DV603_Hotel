package model.server.response;

import java.util.ArrayList;
import model.server.HotelServer.SavingThread;
import model.shared.Hotel;
import model.shared.Room;
import model.shared.filters.roomsFilters.RoomsFilter;

public class RoomsListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Room> roomList;

	public RoomsListResponse(Hotel hotel, SavingThread savingThread, ArrayList<RoomsFilter> roomsFilterList) {
		super (hotel, savingThread);
		roomList = new ArrayList<Room>(hotel.getRoomsAndSuitesList());
		if (roomsFilterList != null) {
			for (RoomsFilter filter : roomsFilterList) {
				filter.applyRoomsFilter(roomList, hotel.getReservationsList());
			}
		}
		super.object = roomList;
	}

}
