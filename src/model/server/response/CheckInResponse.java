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
		checkIn(reservationId);
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void checkIn(int reservationId) {
		Boolean checkedInSuccess = hotel.chekInReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
