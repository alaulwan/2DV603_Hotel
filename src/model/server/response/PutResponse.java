package model.server.response;

import java.time.LocalDate;

import model.server.HotelServer;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.Room.RoomStatus;

public class PutResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Object receivedObject;
	
	public PutResponse (Object Object) {
		this.receivedObject = Object;
		if (receivedObject instanceof Customer) {
			addCustomer();
		}
		else if (receivedObject instanceof Reservation) {
			addReservation();
		}
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void addReservation() {
		try {
			Reservation receivedReservation = (Reservation) receivedObject;
			Reservation.setCount(Reservation.getCount()+1);
			receivedReservation.setReservationId(Reservation.getCount());
			HotelServer.hotel.getCustomerById(receivedReservation.getCustomerId()).getReservationsList().add(receivedReservation);
			if (receivedReservation.checkInDateAsLocalDate().isEqual(LocalDate.now()))
				HotelServer.hotel.getRoomById(receivedReservation.getRoomId()).setRoomStatus(RoomStatus.CHECKIN_TODAY);
			super.object = true;
		}catch (Exception e) {
			super.object = false;
		}
		
	}

	private void addCustomer() {
		try {
			Customer receivedCustomer = (Customer) receivedObject;
			Reservation receivedReservation = receivedCustomer.getReservationsList().get(0);
			Customer.setCount(Customer.getCount()+1);
			receivedCustomer.setCustomerId(Customer.getCount());
			Reservation.setCount(Reservation.getCount()+1);
			receivedReservation.setReservationId(Reservation.getCount());
			receivedReservation.setCustomerId(receivedCustomer.getCustomerId());
			receivedReservation.getBill().setBillId(receivedReservation.getReservationId());
			receivedReservation.getBill().setCustomerId(receivedCustomer.getCustomerId());
			HotelServer.hotel.addCustomer(receivedCustomer);
			if (receivedReservation.checkInDateAsLocalDate().isEqual(LocalDate.now()))
				HotelServer.hotel.getRoomById(receivedReservation.getRoomId()).setRoomStatus(RoomStatus.CHECKIN_TODAY);
			super.object = true;
			}catch (Exception e) {
			super.object = false;
		}
		
	}
}
