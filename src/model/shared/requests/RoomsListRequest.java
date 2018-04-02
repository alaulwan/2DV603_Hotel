package model.shared.requests;

import java.util.ArrayList;

import model.shared.filters.roomsFilters.RoomsFilter;

public class RoomsListRequest extends Request {
	private static final long serialVersionUID = 1L;
	public ArrayList <RoomsFilter> rFilList;
	
	public RoomsListRequest(ArrayList <RoomsFilter> rFilList) {
		this.rFilList = rFilList;
	}

}
