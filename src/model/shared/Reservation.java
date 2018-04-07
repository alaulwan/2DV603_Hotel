package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import model.shared.Room.RoomLocation;

public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private int reservationId;

	public enum ReservationStatus {
		PENDING, CHECKED_IN, CHECKED_OUT, CANCELED
	}

	private ReservationStatus reservationStatus;
	private int customerId;
	private String customerName;
	private String checkInDate;
	private String checkOutDate;
	private float price;
	private Bill bill;
	private int roomId;
	private int roomNumber;
	private RoomLocation roomLocation;
	private int guestsNumber;
	private String discription;
	private int totalDays ;

	public Reservation() {
		
	}

	public Reservation(ReservationStatus reservationStatus, int customerId, String customerName, int roomId,
			int roomNumber, RoomLocation roomLocation, LocalDate checkInDate, LocalDate checkOutDate, float roomPrice,
			float discount, int guestsNumber, String description) {
		this.setReservationId(++count);
		this.setReservationStatus(reservationStatus);
		this.setCustomerId(customerId);
		this.setCustomerName(customerName);
		this.setRoomId(roomId);
		this.setRoomNumber(roomNumber);
		this.setRoomLocation(roomLocation);
		this.setCheckInDate(checkInDate);
		this.setCheckOutDate(checkOutDate);
		this.setTotalDays(checkInDate , checkOutDate);
		this.setPrice(roomPrice);
		this.setGuestsNumber(guestsNumber);
		this.setDiscription(description);
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

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomLocation getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
	}

	public int getGuestsNumber() {
		return guestsNumber;
	}

	public void setGuestsNumber(int guestNumber) {
		this.guestsNumber = guestNumber;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(LocalDate checkInDate , LocalDate checkOutDate) {
		this.totalDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}
