package model.server.response;

import model.server.HotelServer;
import model.shared.Customer;
import model.shared.Reservation;

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
		// TODO Auto-generated method stub
		
	}
}
