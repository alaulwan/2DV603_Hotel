package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import model.shared.Bill.PayStatus;
import model.shared.Room.RoomLocation;
import model.shared.Service.ServiceType;

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
		this.setPrice(roomPrice);
		this.setGuestsNumber(guestsNumber);
		this.setDiscription(description);
		String reservationDescription = roomLocation + " Room: " + roomNumber + " Days: " + getTotalDays();
		this.setBill(new Bill(this.getReservationId(), this.customerId, this.customerName, price * getTotalDays(),
				reservationDescription));
	}

	public boolean checkIn() {
		if (reservationStatus.equals(ReservationStatus.PENDING)) {
			this.setReservationStatus(ReservationStatus.CHECKED_IN);
			return true;
		}
		return false;
	}

	// Method to check-out an reservation. This can only be executed on an pending reservations
	public boolean checkOut() {
		this.setReservationStatus(ReservationStatus.CHECKED_OUT);
		if (LocalDate.now().isBefore(this.checkOutDateAsLocalDate())) {
			this.setCheckOutDate(LocalDate.now().plusDays(1));
			String reservationDescription = roomLocation + " Room: " + roomNumber + " Days: " + getTotalDays();
			bill.getServiceList().get(0).setDescraption(reservationDescription);
			bill.getServiceList().get(0).setPrice(price * this.getTotalDays());
		}
		return true;
	}

	// Method to cancel an reservation. This can only be executed on the pending reservations
	public boolean cancel() {
		this.setReservationStatus(ReservationStatus.CANCELED);
		String reservationDescription = "";
		// Check how long time the reservation canceled before the check-in day to calculate the bill
		// and update the bill description
		int cancelPeriod = Period.between(LocalDate.now(), this.checkInDateAsLocalDate()).getDays();
		if (cancelPeriod > 1) {
			bill.getServiceList().remove(0);
			bill.setPayStatus(PayStatus.PAID);
			return true;
		} else if (cancelPeriod == 1) {
			this.setCheckOutDate(this.checkInDateAsLocalDate().plusDays(1));
			reservationDescription = roomLocation + " Room: " + roomNumber + " ,Canceled less than 24H. Billed for "
					+ this.getTotalDays() + " days";
		} else if (cancelPeriod < 1) {
			this.setCheckOutDate(this.checkInDateAsLocalDate().plusDays(2));
			reservationDescription = roomLocation + " Room: " + roomNumber
					+ " ,Canceled less than 24H, or customer did not chekin. Billed for " + this.getTotalDays()
					+ " days";
		}
		bill.getServiceList().get(0).setDescraption(reservationDescription);
		bill.getServiceList().get(0).setPrice(price * this.getTotalDays());

		return true;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
		if (this.getBill() != null)
			this.getBill().setBillId(reservationId);
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
		if (this.getBill() != null)
			this.getBill().setCustomerId(customerId);
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
		if (this.getBill() != null)
			this.getBill().setCustomerName(customerName);
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
		updateBill();
	}

	public LocalDate checkInDateAsLocalDate() {
		return LocalDate.parse(checkInDate);
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate.toString();
		updateBill();
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
		updateBill();
	}

	public LocalDate checkOutDateAsLocalDate() {
		return LocalDate.parse(checkOutDate);
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate.toString();
		updateBill();
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
		updateBill();
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
		updateBill();
	}

	public RoomLocation getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
		updateBill();
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
		int period = Period.between(this.checkInDateAsLocalDate(), this.checkOutDateAsLocalDate()).getDays();
		if (period == 0)
			period = 1;
		return period;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	private void updateBill() {
		if (this.bill != null && this.bill.getServiceList() != null) {
			String reservationDescription = roomLocation + " Room: " + roomNumber + " Days: " + getTotalDays();
			Service reserveService = new Service(ServiceType.RESERVATION, price * getTotalDays(), 1,
					reservationDescription);
			this.bill.getServiceList().set(0, reserveService);
		}
	}

}
