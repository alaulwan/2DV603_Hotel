package model.shared.requests;

import java.util.ArrayList;
import model.shared.filters.reservationsFilters.ReservationsFilter;

public class ReservationsListRequest extends Request {
	private static final long serialVersionUID = 1L;
	public ArrayList <ReservationsFilter> reservationsFilterList;
	
	public ReservationsListRequest(ArrayList <ReservationsFilter> reservationsFilterList) {
		this.reservationsFilterList = reservationsFilterList;
		super.requestType = RequestType.GET_RESERVATIONS;
	}

}
