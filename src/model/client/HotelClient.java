package model.client;

import java.util.ArrayList;

import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.filters.roomsFilters.MinCabacityRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;

public class HotelClient {
	private static ArrayList <Room> roomList;
	
	public static ServerAPI API = new ServerAPI();
	
	public HotelClient() {
		//getAllRooms();
	}
	
	private void getAllRooms() {
		ArrayList <RoomsFilter> rFL = new ArrayList <RoomsFilter> ();
		RoomsFilter rF = new MinCabacityRoomsFilter(3);
		rFL.add(rF);
		roomList = API.getRoomsList(rFL);
		System.out.println(roomList.size());
	}

}