package model.client;

import java.util.ArrayList;

import model.shared.Room;
import model.shared.filters.roomsFilters.MinCabacityRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;

public class HotelClient {
	private ArrayList <Room> roomList;
	private ServerAPI API;
	
	public static void main(String[] args) {
		HotelClient hClient = new HotelClient();
		hClient.getAllRooms();
	}
	
	private void getAllRooms() {
		ArrayList <RoomsFilter> rFL = new ArrayList <RoomsFilter> ();
		RoomsFilter rF = new MinCabacityRoomsFilter(3);
		rFL.add(rF);
		roomList = API.getRoomsList(rFL);
		System.out.println(roomList.size());
	}

	public HotelClient () {
		API = new ServerAPI();
		
	}
}