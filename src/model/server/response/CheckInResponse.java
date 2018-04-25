package model.server.response;

import model.server.HotelServer;

public class CheckInResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckInResponse(int reservationId) {
		checkIn (reservationId);
	}

	private void checkIn(int reservationId) {
		Boolean checkedInSuccess = HotelServer.hotel.chekInReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
