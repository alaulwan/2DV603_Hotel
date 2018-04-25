package model.server.response;

import model.server.HotelServer;

public class CancelReservationResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CancelReservationResponse(int reservationId) {
		cancelReservation (reservationId);
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void cancelReservation(int reservationId) {
		Boolean checkedInSuccess = HotelServer.hotel.cancelReservation(reservationId);
		this.object = checkedInSuccess;
	}
}
