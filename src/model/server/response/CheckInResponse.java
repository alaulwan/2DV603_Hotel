package model.server.response;

import model.server.HotelServer;

public class CheckInResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CheckInResponse(int reservationId) {
		CheckIn (reservationId);
	}

	private void CheckIn(int reservationId) {
		Boolean checkedInSuccess = HotelServer.hotel.chekInReservation(reservationId);
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
