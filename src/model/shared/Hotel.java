package model.shared;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.shared.Bed.BedSize;
import model.shared.Customer.IdentificationType;
import model.shared.Room.RoomLocation;
import model.shared.Room.RoomSize;

@XmlRootElement(name = "Hotel")
public class Hotel {
	@XmlElement(name = "customersList")
	private ArrayList <Customer> customersList = new ArrayList <Customer>(); 
	
	@XmlElement(name = "roomsList")
	private ArrayList <Room> roomsList = new ArrayList <Room>();
	
	// private ArrayList <Reservation> reservationsList = new ArrayList <Reservation>();
	// private ArrayList <Bill> billsList = new ArrayList <Bill>();
	
	public Hotel() {
		defaultHotel();
	}

	private void defaultHotel() {
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
		Suite suite2 = new Suite(4, new ArrayList<Room>(Arrays.asList(S1Ra, S1Rb)));
		
		Customer customer1 = new Customer("Alaa Al", LocalDate.of(1982, 01, 01), "076970", IdentificationType.PASS_NUMBER, "1111111", "Vaxjo", "Syr",
				"a@a.a", "student");
		
		Customer customer2 = new Customer("Basem mo", LocalDate.of(1992, 05, 05), "0732323", IdentificationType.PASS_NUMBER, "2222222", "Vaxjo", "Syr",
				"b@b.b", "student");
		
		Customer customer3 = new Customer("Paul", LocalDate.of(1987, 10, 10), "0732323", IdentificationType.PASS_NUMBER, "3333333", "Vaxjo", "???",
				"p@p.p", "student");
		
		customer1.addReservation(new ArrayList<Room>(Arrays.asList(R1)), 0, "Al reservation");
		customer2.addReservation(new ArrayList<Room>(Arrays.asList(R2)), 0, "Ba reservation");
		customer3.addReservation(new ArrayList<Room>(Arrays.asList(suite1)), 0, "Pa reservation");
		
		customersList.addAll(new ArrayList<Customer> (Arrays.asList(customer1, customer1, customer1)));
		roomsList.addAll(new ArrayList<Room> (Arrays.asList(R1, R2, suite1, suite2)));
	}
}
