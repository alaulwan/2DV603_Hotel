package model.shared.filters.customersFilters;

import java.util.ArrayList;

import model.shared.Customer;
import model.shared.Reservation;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;

public class ReservationStatusCustumersFilter implements CustomersFilter {
	private static final long serialVersionUID = 1L;
	private boolean PENDING;
	private boolean CHECKED_IN;
	private boolean CHECKED_OUT;
	private boolean CANCELED;
	
	public ReservationStatusCustumersFilter (boolean PENDING, boolean CHECKED_IN, boolean CHECKED_OUT, boolean CANCELED) {
		this.PENDING = PENDING;
		this.CHECKED_IN = CHECKED_IN;
		this.CHECKED_OUT = CHECKED_OUT;
		this.CANCELED = CANCELED;
	}
	
	@Override
	public ArrayList<Customer> applyCustomersFilter(ArrayList<Customer> customersList) {
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(PENDING, CHECKED_IN, CHECKED_OUT, CANCELED);
		for (int i=0; i< customersList.size(); i++) {
			ArrayList<Reservation> reservationsList = new ArrayList<Reservation>(customersList.get(i).getReservationsList());
			statusReservationsFilter.applyReservationsFilter(reservationsList);
			if (reservationsList.isEmpty()) {
				customersList.remove(i);
				i--;
			}
		}
		return customersList;
	}
}
