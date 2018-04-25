package model.server.response;

import model.server.HotelServer;

public class CheckOutResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckOutResponse(int reservationId) {
		checkOut (reservationId);
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void checkOut(int reservationId) {
		Boolean checkedInSuccess = HotelServer.hotel.chekOutReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
