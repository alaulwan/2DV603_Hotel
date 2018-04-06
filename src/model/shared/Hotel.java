package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.xml.bind.annotation.XmlRootElement;

import model.shared.Bed.BedSize;
import model.shared.Customer.Gender;
import model.shared.Customer.IdentificationType;
import model.shared.Room.RoomLocation;
import model.shared.Room.RoomSize;

@XmlRootElement(name = "Hotel")
public class Hotel implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList <Customer> customersList = new ArrayList <Customer>(); 
	
	private ArrayList <Room> roomsList = new ArrayList <Room>();
	
	private ArrayList <Suite> suitesList = new ArrayList <Suite>();
	
	// private ArrayList <Reservation> reservationsList = new ArrayList <Reservation>();
	// private ArrayList <Bill> billsList = new ArrayList <Bill>();
	
	public Hotel() {
		
	}
	
	public void addCustomer (Customer custumer){
		this.customersList.add(custumer);
	}
	
	public Customer getCustomerById (int customerId){
		for (Customer custumer : customersList) {
			if (custumer.getCustomerId() == customerId) {
				return custumer;
			}
		}
		return null;
	}
	
	public boolean isCustomerExists (int customerId) {
		for (Customer custumer : customersList) {
			if (custumer.getCustomerId() == customerId) {
				return true;
			}
		}
		return false;
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
	
	public ArrayList<Room> getRoomsAndSuitesList() {
		ArrayList<Room> allRoomsAndSuits = new ArrayList<Room> ();
		allRoomsAndSuits.addAll(roomsList);
		allRoomsAndSuits.addAll(suitesList);
		return allRoomsAndSuits;
	}
	
	public ArrayList<Reservation> getReservationsList() {
		ArrayList<Reservation> reservationsList = new ArrayList<Reservation> ();
		for (Customer c : customersList)
			reservationsList.addAll(c.getReservationsList());
		return reservationsList;
	}
	
	public ArrayList<Bill> getBillsList() {
		ArrayList<Reservation> reservationsList = getReservationsList();
		ArrayList<Bill> billsList = new ArrayList<Bill>();
		for (Reservation r : reservationsList)
			billsList.add(r.getBill());
		return billsList;
	}
	
	public Room getRoomById(int roomId) {
		for (Room r : roomsList)
			if (r.getRoomId() == roomId)
				return r;
		for (Suite s : suitesList)
			if (s.getRoomId() == roomId)
				return s;
		return null;
	}

	public void defaultHotel() {
		Bed  bed1R1 = new Bed(BedSize.SINGLE);
		Room R1 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 1, RoomSize.MEDIUM, false, true,
				true, false, false);
		
		Bed  bed1R2 = new Bed(BedSize.DOUBLE);
		Room R2 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R2)), RoomLocation.KALMAR, 2, RoomSize.MEDIUM, false, true,
				true, false, false);
		
		Bed  bed1S1Ra = new Bed(BedSize.DOUBLE);
		Room S1Ra = new Room (new ArrayList<Bed>(Arrays.asList(bed1S1Ra)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM, false, true,
				true, false, false);
		Bed  bed1S1Rb = new Bed(BedSize.SINGLE);
		Room S1Rb = new Room (new ArrayList<Bed>(Arrays.asList(bed1S1Rb)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM, false, true,
				true, false, false);
		Suite suite1 = new Suite(3, new ArrayList<Room>(Arrays.asList(S1Ra, S1Rb)));
		
		Bed  bed1S2Ra = new Bed(BedSize.DOUBLE);
		Room S2Ra = new Room (new ArrayList<Bed>(Arrays.asList(bed1S2Ra)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM, false, true,
				true, false, false);
		Bed  bed1S2Rb = new Bed(BedSize.SINGLE);
		Bed  bed2S2Rb = new Bed(BedSize.SINGLE);
		Room S2Rb = new Room (new ArrayList<Bed>(Arrays.asList(bed1S2Rb, bed2S2Rb)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM, false, true,
				true, false, false);
		Suite suite2 = new Suite(4, new ArrayList<Room>(Arrays.asList(S2Ra, S2Rb)));
		
		Customer customer1 = new Customer("Alaa Al", LocalDate.of(1982, 01, 01), Gender.MALE, "076970", IdentificationType.PASS_NUMBER, "1111111", "Vaxjo", "Syr",
				"a@a.a", "student");
		
		Customer customer2 = new Customer("Basem mo", LocalDate.of(1992, 05, 05), Gender.MALE,"0732323", IdentificationType.PASS_NUMBER, "2222222", "Vaxjo", "Syr",
				"b@b.b", "student");
		
		Customer customer3 = new Customer("Paul", LocalDate.of(1987, 10, 10), Gender.MALE,"0732323", IdentificationType.PASS_NUMBER, "3333333", "Vaxjo", "???",
				"p@p.p", "student");
		
		customer1.addReservation(R1.getRoomId(), R1.getRoomNum(), R1.getRoomLocation(),R1.getRate(), 0, "Al reservation");
		customer2.addReservation(R2.getRoomId(), R2.getRoomNum(), R2.getRoomLocation(), R2.getRate(), 0, "Ba reservation");
		customer3.addReservation(suite1.getRoomId(), suite1.getRoomNum(), suite1.getRoomLocation(), suite1.getRate(), 0, "Pa reservation");
		
		Room R3 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 3, RoomSize.MEDIUM, false, true,
				true, false, false);
		Room R4 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 4, RoomSize.MEDIUM, false, true,
				true, false, false);
		Room R5 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 5, RoomSize.MEDIUM, false, true,
				true, false, false);
		Room R6 = new Room (new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 6, RoomSize.MEDIUM, false, true,
				true, false, false);
		customersList.addAll(new ArrayList<Customer> (Arrays.asList(customer1, customer1, customer1)));
		roomsList.addAll(new ArrayList<Room> (Arrays.asList(R1, R2,R3,R4,R5,R6)));
		suitesList.addAll(new ArrayList<Suite> (Arrays.asList(suite1, suite2)));
		R1.setAvailable(true);
	}

	
}
