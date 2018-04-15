package model.shared.filters.reservationsFilters;

import java.util.ArrayList;
import model.shared.Reservation;

public class CustomerNameReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	private String [] customerName;
	
	public CustomerNameReservationsFilter(String customerName) {
		this.customerName = customerName.split("\\s+");
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			for (String name : customerName) {
				if(!reservationsList.get(i).getCustomerName().toLowerCase().contains(name.toLowerCase())) {
					reservationsList.remove(i);
					i--;
					break;
				}
			}
		}
		return reservationsList;
	}
}
