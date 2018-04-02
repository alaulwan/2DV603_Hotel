package model.shared;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private int reservationId;
	public enum ReservationStatus { PENDING, CHECKED_IN, CHECKED_OUT, CANCELED }
	private ReservationStatus reservationStatus;
	private int customerId;
	private String customerName;
	private String checkInDate;
	private String checkOutDate;
	private float price;
	private Bill bill;
	private int roomId;
	
	public Reservation() {
		
	}
	
	public Reservation(ReservationStatus reservationStatus, int customerId, int roomId, float roomPrice, float discount, String description ) {
		this.setReservationId(++count);
		this.setReservationStatus(reservationStatus);
		this.setCustomerId(customerId);
		this.setRoomId(roomId);
		this.setPrice(roomPrice);
		this.setBill(new Bill(reservationId, this.customerId, this.customerName, price, discount, ""));
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate checkInDateAsLocalDate() {
		return LocalDate.parse(checkInDate);
	}
	
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate.toString();
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public LocalDate checkOutDateAsLocalDate() {
		return LocalDate.parse(checkOutDate);
	}
	
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate.toString();
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Reservation.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	

}
