package model.server.response;

import model.server.HotelServer.SavingThread;
import model.shared.Hotel;

public class CheckInResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckInResponse(Hotel hotel, SavingThread savingThread, int reservationId) {
		super (hotel, savingThread);
		// Check in the reservation that the client sent its ID
		checkIn(reservationId);
		// If success, save to the database
		if ((boolean) super.object)
			super.updateDataBase();
	}
	
	// Method to check in the reservation that the client sent its ID
	private void checkIn(int reservationId) {
		Boolean checkedInSuccess = hotel.chekInReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
