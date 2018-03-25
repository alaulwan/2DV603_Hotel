package model.shared;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Reservation {
	private static int count = 0;
	private int reservationId;
	public enum ReservationStatus { PENDING, CHECKED_IN, CHECKED_OUT, CANCELED }
	private ReservationStatus reservationStatus;
	private int customerId;
	private String customerName;
	private float price;
	private float discount;
	private Bill bill;
	
	@XmlElement(name = "roomIdList")
	private ArrayList <String> roomIdList;
	
	public Reservation() {
		
	}
	
	public Reservation(ReservationStatus reservationStatus, int customerId, ArrayList <Room> roomList, float discount, String description ) {
		this.setReservationId(++count);
		this.setReservationStatus(reservationStatus);
		this.setCustomerId(customerId);
		this.setRoomIdList(roomList);
		this.calculatePrice(roomList);
		this.setBill(new Bill(reservationId, this.customerId, this.customerName, price, roomList.size(), discount, ""));
	}

	private void calculatePrice(ArrayList <Room> roomList) {
		price = 0;
		for (Room room : roomList)
			price = room.getRate();
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

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public ArrayList<String> getRoomIdList() {
		return roomIdList;
	}

	public void setRoomIdList(ArrayList<Room> roomList) {
		this.roomIdList = new ArrayList<String>();
		for (Room room : roomList)
			this.roomIdList.add(room.getRoomId()+"");
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	

}
