package model.server.response;

import java.time.LocalDate;

import model.server.HotelServer;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomStatus;

public class PostResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object receivedObject;
	public int Id;
	
	public PostResponse (Object Object, int Id) {
		this.receivedObject = Object;
		this.Id = Id;
		if (receivedObject instanceof Customer) {
			updateCustomer();
		}
		else if (receivedObject instanceof Reservation) {
			updateReservation();
		}
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void updateCustomer() {
		try {
			Customer receivedCustomer = (Customer) receivedObject;
			HotelServer.hotel.getCustomerById(Id).updateFrom(receivedCustomer);
			super.object = true;
		}catch (Exception e) {
			super.object = false;
		}
		
	}

	private void updateReservation() {
		try {
			Reservation receivedReservation = (Reservation) receivedObject;
			Reservation oldReservation = HotelServer.hotel.getReservationById(Id);
			if (receivedReservation != null && oldReservation!=null && oldReservation.getReservationStatus().equals(ReservationStatus.PENDING)) {
				HotelServer.hotel.deleteReservation(oldReservation.getReservationId());
				HotelServer.hotel.getCustomerById(receivedReservation.getCustomerId()).getReservationsList().add(receivedReservation);
				if (receivedReservation.checkInDateAsLocalDate().isEqual(LocalDate.now()))
					HotelServer.hotel.getRoomById(receivedReservation.getRoomId()).setRoomStatus(RoomStatus.CHECKIN_TODAY);
				super.object = true;
			}
		}catch (Exception e) {
			super.object = false;
		}		
	}
}
