package model.server.response;

import model.server.HotelServer.SavingThread;
import model.shared.Hotel;

public class CheckOutResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckOutResponse(Hotel hotel, SavingThread savingThread, int reservationId) {
		super (hotel, savingThread);
		// Check out the reservation that the client sent its ID
		checkOut(reservationId);
		// If success, save to the database
		if ((boolean) super.object)
			super.updateDataBase();
	}
	
	// Method to check out the reservation that the client sent its ID
	private void checkOut(int reservationId) {
		Boolean checkedInSuccess = hotel.chekOutReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
