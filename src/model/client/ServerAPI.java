package model.client;

import java.util.ArrayList;
import model.shared.Connection;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.filters.roomsFilters.LocationRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;
import model.shared.requests.Request;
import model.shared.requests.Request.RequestType;
import model.shared.requests.RoomsListRequest;

public class ServerAPI {
	public static RoomLocation location ;
	
	public ServerAPI () {
		
	}
	
	public static ArrayList <Room> getAllRooms () {
		ArrayList<RoomsFilter> filterList= new ArrayList<RoomsFilter>() ;
		filterList.add(new LocationRoomsFilter(location));
		return getRoomsList(filterList);
	}
	
	public static ArrayList <Room> getRoomsList (ArrayList<RoomsFilter> filterList) {
		Request request = new RoomsListRequest (filterList);
		request.requestType = RequestType.GET_ROOMS;
		Connection c = new Connection();
		c.send(request);
		
		@SuppressWarnings("unchecked")
		ArrayList <Room> roomList = (ArrayList<Room>) c.receiveObject ();
		c.close();
		
		return roomList;
	}

}
