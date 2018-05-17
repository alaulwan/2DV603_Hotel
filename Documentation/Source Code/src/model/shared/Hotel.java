package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomStatus;

@XmlRootElement(name = "Hotel")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Customer> customersList = new ArrayList<Customer>();
	private ArrayList<Room> roomsList = new ArrayList<Room>();
	private ArrayList<Suite> suitesList = new ArrayList<Suite>();	// The suite in adjacent rooms
	private ArrayList<Service> servicesList = new ArrayList<Service>(); // A list of services that can the hotel offer

	public Hotel() {

	}

	public void addCustomer(Customer customer) {
		this.customersList.add(customer);
	}

	public Customer getCustomerById(int customerId) {
		for (Customer custumer : customersList) {
			if (custumer.getCustomerId() == customerId) {
				return custumer;
			}
		}
		return null;
	}

	public boolean isCustomerExists(int customerId) {
		for (Customer custumer : customersList) {
			if (custumer.getCustomerId() == customerId) {
				return true;
			}
		}
		return false;
	}

	public Reservation getReservationById(int reservationId) {
		ArrayList<Reservation> reservationsList = this.getReservationsList();
		for (Reservation res : reservationsList) {
			if (res.getReservationId() == reservationId)
				return res;
		}
		return null;
	}

	// Method to check-in an reservation. This can only be executed on an pending reservations
	public boolean chekInReservation(int reservationId) {
		Reservation res = this.getReservationById(reservationId);
		if (res != null && res.checkIn()) {
			this.getRoomById(res.getRoomId()).setRoomStatus(RoomStatus.OCCUPIED);
			return true;
		}
		return false;
	}

	// Method to check-out an reservation. This can only be executed on an checked-in reservations
	public boolean chekOutReservation(int reservationId) {
		Reservation res = this.getReservationById(reservationId);
		Room room = this.getRoomById(res.getRoomId());
		if (res != null && res.checkOut()) {
			if (room.getRoomStatus().equals(RoomStatus.CHECK_OUT_IN))
				room.setRoomStatus(RoomStatus.CHECKIN_TODAY);
			else
				room.setRoomStatus(RoomStatus.AVAILABLE);

			return true;
		}
		return false;
	}

	// Method to cancel an reservation. This can only be executed on the pending reservations
	public boolean cancelReservation(int reservationId) {
		Reservation res = this.getReservationById(reservationId);
		if (res != null && res.getReservationStatus().equals(ReservationStatus.PENDING)) {
			Room room = this.getRoomById(res.getRoomId());
			if (LocalDate.now().isAfter(res.checkInDateAsLocalDate()))
				room.setRoomStatus(RoomStatus.AVAILABLE);
			else if (LocalDate.now().equals(res.checkInDateAsLocalDate())
					&& room.getRoomStatus().equals(RoomStatus.CHECK_OUT_IN))
				room.setRoomStatus(RoomStatus.CHECKOUT_TODAY);
			else if (LocalDate.now().equals(res.checkInDateAsLocalDate()))
				room.setRoomStatus(RoomStatus.AVAILABLE);
			return res.cancel();
		}
		return false;
	}

	// Method to delete an reservation completely. This can only be executed on the pending or checked-out reservations
	public boolean deleteReservation(int reservationId) {
		boolean canceled = false;
		boolean deleted = false;
		Reservation res = this.getReservationById(reservationId);
		if (res != null) {
			if (res.getReservationStatus().equals(ReservationStatus.PENDING)) {
				canceled = this.cancelReservation(reservationId);
			}
			if (canceled || res.getReservationStatus().equals(ReservationStatus.CHECKED_OUT)) {
				deleted = this.getCustomerById(res.getCustomerId()).getReservationsList().remove(res);
			}
		}
		return deleted;
	}

	public ArrayList<Customer> getCustomersList() {
		return customersList;
	}

	public void setCustomersList(ArrayList<Customer> customersList) {
		this.customersList = customersList;
	}

	public ArrayList<Room> getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(ArrayList<Room> roomsList) {
		this.roomsList = roomsList;
	}

	public ArrayList<Suite> getSuitesList() {
		return suitesList;
	}

	public void setSuitesList(ArrayList<Suite> suitesList) {
		this.suitesList = suitesList;
	}

	// This method will return all rooms and suite in the hotel in all locations
	public ArrayList<Room> getRoomsAndSuitesList() {
		ArrayList<Room> allRoomsAndSuits = new ArrayList<Room>();
		allRoomsAndSuits.addAll(roomsList);
		allRoomsAndSuits.addAll(suitesList);
		return allRoomsAndSuits;
	}

	
	// This method will return all reservations in the hotel in all locations
	public ArrayList<Reservation> getReservationsList() {
		ArrayList<Reservation> reservationsList = new ArrayList<Reservation>();
		for (Customer c : customersList)
			reservationsList.addAll(c.getReservationsList());
		return reservationsList;
	}

	// This method will return all bills in the hotel in all locations
	public ArrayList<Bill> getBillsList() {
		ArrayList<Reservation> reservationsList = getReservationsList();
		ArrayList<Bill> billsList = new ArrayList<Bill>();
		for (Reservation r : reservationsList)
			billsList.add(r.getBill());
		return billsList;
	}

	public ArrayList<Service> getServicesList() {
		return servicesList;
	}

	public void setServicesList(ArrayList<Service> servicesList) {
		this.servicesList = servicesList;
	}

	// Get room by its ID
	public Room getRoomById(int roomId) {
		for (Room r : roomsList)
			if (r.getRoomId() == roomId)
				return r;
		for (Suite s : suitesList)
			if (s.getRoomId() == roomId)
				return s;
		return null;
	}

}
