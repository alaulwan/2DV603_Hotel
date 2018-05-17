package model.server.response;

import model.server.HotelServer.SavingThread;
import model.shared.Hotel;

public class CancelReservationResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CancelReservationResponse(Hotel hotel, SavingThread savingThread, int reservationId) {
		super (hotel, savingThread);
		// Cancel the reservation that the client sent its ID
		cancelReservation(reservationId);
		// If success, save to the database
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void cancelReservation(int reservationId) {
		Boolean checkedInSuccess = hotel.cancelReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
