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
		checkOut(reservationId);
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void checkOut(int reservationId) {
		Boolean checkedInSuccess = hotel.chekOutReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
