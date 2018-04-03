package model.shared.filters.reservationsFilters;

import java.util.ArrayList;
import model.shared.Reservation;
import model.shared.Reservation.ReservationStatus;

public class StatusReservationsFilter implements ReservationsFilter {
	private static final long serialVersionUID = 1L;
	private boolean PENDING;
	private boolean CHECKED_IN;
	private boolean CHECKED_OUT;
	private boolean CANCELED;
	
	public StatusReservationsFilter (boolean PENDING, boolean CHECKED_IN, boolean CHECKED_OUT, boolean CANCELED) {
		this.PENDING = PENDING;
		this.CHECKED_IN = CHECKED_IN;
		this.CHECKED_OUT = CHECKED_OUT;
		this.CANCELED = CANCELED;
	}
	
	@Override
	public ArrayList<Reservation> applyReservationsFilter(ArrayList<Reservation> reservationsList) {
		for (int i=0; i< reservationsList.size(); i++) {
			if (!PENDING && reservationsList.get(i).getReservationStatus()== ReservationStatus.PENDING) {
				reservationsList.remove(i);
				i--;
			}
			else if (!CHECKED_IN && reservationsList.get(i).getReservationStatus()== ReservationStatus.CHECKED_IN) {
				reservationsList.remove(i);
				i--;
			}
			else if (!CHECKED_OUT && reservationsList.get(i).getReservationStatus()== ReservationStatus.CHECKED_OUT) {
				reservationsList.remove(i);
				i--;
			}
			else if (!CANCELED && reservationsList.get(i).getReservationStatus()== ReservationStatus.CANCELED) {
				reservationsList.remove(i);
				i--;
			}
		}
		return reservationsList;
	}

}
