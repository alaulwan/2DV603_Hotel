package model.server.response;

import model.server.HotelServer;

public class CheckOutResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckOutResponse(int reservationId) {
		checkOut (reservationId);
	}

	private void checkOut(int reservationId) {
		Boolean checkedInSuccess = HotelServer.hotel.chekOutReservation(reservationId);
		this.object = checkedInSuccess;
//		Reservation reservation = HotelServer.hotel.getReservationById(reservationId);
//		if (reservation.getReservationStatus().equals(ReservationStatus.PENDING)) {
//			Room room = HotelServer.hotel.getRoomById(reservation.getRoomId());
//			if (room.getRoomStatus().equals(RoomStatus.CHEKIN_TODAY)) {
//				room.setRoomStatus(RoomStatus.OCCUPIED);
//				reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
//				this.object = true;
//			}
//			else
//				this.object = false;
//		}
//		else
//			this.object = false;
	}
}
