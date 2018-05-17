package model.server.response;

import model.server.HotelServer.SavingThread;
import model.shared.Customer;
import model.shared.Hotel;
import model.shared.Reservation;
import model.shared.Reservation.ReservationStatus;

public class DeleteResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object receivedObject;

	// Delete response
	public DeleteResponse(Hotel hotel, SavingThread savingThread, Object Object) {
		super (hotel, savingThread);
		this.receivedObject = Object;
		// If the received object from the client is a customer or a reservation, then invoke the corresponding method
		if (receivedObject instanceof Customer) {
			deleteCustomer();
		} else if (receivedObject instanceof Reservation) {
			deleteReservation();
		}
		
		// If success, save to the database
		if ((boolean) super.object)
			super.updateDataBase();
	}

	// Method to delete the received reservation
	private void deleteReservation() {
		try {
			Reservation receivedReservation = (Reservation) receivedObject;
			super.object = hotel.deleteReservation(receivedReservation.getReservationId());
		} catch (Exception e) {
			super.object = false;
		}

	}

	// Method to delete the received customer
	private void deleteCustomer() {
		super.object = false;
		try {
			Customer receivedCustomer = hotel.getCustomerById(((Customer) receivedObject).getCustomerId());
			// Check if the customer has an pending or checked in reservation,
			// don't perform the deleting
			for (Reservation reservation : receivedCustomer.getReservationsList()) {
				if (reservation.getReservationStatus().equals(ReservationStatus.PENDING)
						|| reservation.getReservationStatus().equals(ReservationStatus.CHECKED_IN))
					return;
			}
			
			hotel.getCustomersList().remove(receivedCustomer);
			super.object = true;
		} catch (Exception e) {
			super.object = false;
		}

	}
}
