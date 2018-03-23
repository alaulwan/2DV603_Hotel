package model.shared;

import java.util.ArrayList;

public class Reservation {
	private static int count = 0;
	private int reservationId;
	public enum ReservationStatus { PENDING, ACTIVE, CLOSED, CANCELED }
	private Customer customer;
	private Room room;
	private Bill bill;
	private ArrayList <Service> serviceList = new ArrayList<Service>();
	
	public Reservation() {
		
	}

}
