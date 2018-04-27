package model.server.response;

import java.time.LocalDate;

import model.server.HotelServer.SavingThread;
import model.shared.Bill;
import model.shared.Customer;
import model.shared.Hotel;
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

	public PostResponse(Hotel hotel, SavingThread savingThread, Object Object, int Id) {
		super (hotel, savingThread);
		this.receivedObject = Object;
		this.Id = Id;
		if (receivedObject instanceof Customer) {
			updateCustomer();
		} else if (receivedObject instanceof Reservation) {
			updateReservation();
		} else if (receivedObject instanceof Bill) {
			updateBill();
		}
		if ((boolean) super.object)
			super.updateDataBase();
	}

	private void updateCustomer() {
		try {
			Customer receivedCustomer = (Customer) receivedObject;
			hotel.getCustomerById(Id).updateFrom(receivedCustomer);
			super.object = true;
		} catch (Exception e) {
			super.object = false;
		}

	}

	private void updateReservation() {
		try {
			Reservation receivedReservation = (Reservation) receivedObject;
			Reservation oldReservation = hotel.getReservationById(Id);
			if (receivedReservation != null && oldReservation != null) {
				if (oldReservation.getReservationStatus().equals(ReservationStatus.PENDING)) {
					hotel.deleteReservation(oldReservation.getReservationId());
					hotel.getCustomerById(receivedReservation.getCustomerId()).getReservationsList()
							.add(receivedReservation);
					if (receivedReservation.checkInDateAsLocalDate().isEqual(LocalDate.now()))
						hotel.getRoomById(receivedReservation.getRoomId())
								.setRoomStatus(RoomStatus.CHECKIN_TODAY);
				} else if (oldReservation.getBill().getServiceList().size() != receivedReservation.getBill()
						.getServiceList().size()) {
					oldReservation.getBill().setServiceList(receivedReservation.getBill().getServiceList());
				}
				super.object = true;
			} else
				super.object = false;
		} catch (Exception e) {
			super.object = false;
		}
	}

	private void updateBill() {
		try {
			Bill receivedBill = (Bill) receivedObject;
			Reservation reservation = hotel.getReservationById(Id);
			reservation.setBill(receivedBill);
			super.object = true;
		} catch (Exception e) {
			super.object = false;
		}
	}
}
