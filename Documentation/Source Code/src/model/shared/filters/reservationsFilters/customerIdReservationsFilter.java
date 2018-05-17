package model.shared.filters.reservationsFilters;

import java.util.ArrayList;
import model.shared.Reservation;

public class customerIdReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	private int customerId;
	
	public customerIdReservationsFilter(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			if(reservationsList.get(i).getCustomerId() != customerId) {
				reservationsList.remove(i);
				i--;
			}
		}
		return reservationsList;
	}

}
