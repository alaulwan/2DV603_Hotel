package model.server.response;

import model.server.HotelServer;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Reservation.ReservationStatus;

public class DeleteResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object receivedObject;

	public DeleteResponse(Object Object) {
		this.receivedObject = Object;
		if (receivedObject instanceof Customer) {
			deleteCustomer();
		} else if (receivedObject instanceof Reservation) {
			deleteReservation();
		}
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void deleteReservation() {
		try {
			Reservation receivedReservation = (Reservation) receivedObject;
			super.object = HotelServer.hotel.deleteReservation(receivedReservation.getReservationId());
		} catch (Exception e) {
			super.object = false;
		}

	}

	private void deleteCustomer() {
		super.object = false;
		try {
			Customer receivedCustomer = HotelServer.hotel.getCustomerById(((Customer) receivedObject).getCustomerId());
			System.out.println(receivedCustomer);
			for (Reservation reservation : receivedCustomer.getReservationsList()) {
				if (reservation.getReservationStatus().equals(ReservationStatus.PENDING)
						|| reservation.getReservationStatus().equals(ReservationStatus.PENDING))
					return;
			}
			HotelServer.hotel.getCustomersList().remove(receivedCustomer);
			super.object = true;
		} catch (Exception e) {
			super.object = false;
		}

	}
}
