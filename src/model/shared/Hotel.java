package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import model.shared.Bed.BedSize;
import model.shared.Bill.PayStatus;
import model.shared.Customer.Gender;
import model.shared.Customer.IdentificationType;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomLocation;
import model.shared.Room.RoomSize;
import model.shared.Room.RoomStatus;
import model.shared.Service.ServiceType;

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

	public ArrayList<Room> getRoomsAndSuitesList() {
		ArrayList<Room> allRoomsAndSuits = new ArrayList<Room>();
		allRoomsAndSuits.addAll(roomsList);
		allRoomsAndSuits.addAll(suitesList);
		return allRoomsAndSuits;
	}

	public ArrayList<Reservation> getReservationsList() {
		ArrayList<Reservation> reservationsList = new ArrayList<Reservation>();
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

	public ArrayList<Service> getServicesList() {
		return servicesList;
	}

	public void setServicesList(ArrayList<Service> servicesList) {
		this.servicesList = servicesList;
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
		{
		Bed bed1R1 = new Bed(BedSize.SINGLE);
		Room R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 101, RoomSize.SMALL, false,
				false, false, false, false);
		
		Bed bed1R2 = new Bed(BedSize.SINGLE);
		Room R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R2)), RoomLocation.VAXJO, 102, RoomSize.SMALL, true,
				true, true, false, false);
		
		Bed bed1R3 = new Bed(BedSize.SINGLE);
		Room R3 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R3)), RoomLocation.VAXJO, 103, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R4 = new Bed(BedSize.SINGLE);
		Room R4 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R4)), RoomLocation.VAXJO, 104, RoomSize.MEDIUM, true,
				true, true, true, false);
		
		Bed bed1R5 = new Bed(BedSize.DOUBLE);
		Room R5 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R5)), RoomLocation.VAXJO, 105, RoomSize.SMALL, false,
				false, false, false, false);
		
		Bed bed1R6 = new Bed(BedSize.DOUBLE);
		Room R6 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R6)), RoomLocation.VAXJO, 106, RoomSize.SMALL, true,
				true, true, false, false);
		
		Bed bed1R7 = new Bed(BedSize.DOUBLE);
		Room R7 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R7)), RoomLocation.VAXJO, 107, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R8 = new Bed(BedSize.DOUBLE);
		Room R8 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R8)), RoomLocation.VAXJO, 108, RoomSize.BIG,  true,
				true, true, true, false);
		
		Bed bed1R9 = new Bed(BedSize.SINGLE);
		Bed bed2R9 = new Bed(BedSize.DOUBLE);
		Room R9 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R9, bed2R9)), RoomLocation.VAXJO, 109, RoomSize.MEDIUM, false,
				false, false, false, false);
		
		Bed bed1R10 = new Bed(BedSize.SINGLE);
		Bed bed2R10 = new Bed(BedSize.DOUBLE);
		Room R10 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R10, bed2R10)), RoomLocation.VAXJO, 110, RoomSize.MEDIUM, true,
				true, true, false, false);
		
		Bed bed1R11 = new Bed(BedSize.SINGLE);
		Bed bed2R11 = new Bed(BedSize.DOUBLE);
		Room R11 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R11, bed2R11)), RoomLocation.VAXJO, 111, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R12 = new Bed(BedSize.SINGLE);
		Bed bed2R12 = new Bed(BedSize.DOUBLE);
		Room R12 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R12, bed2R12)), RoomLocation.VAXJO, 112, RoomSize.BIG, true,
				true, true, true, false);
		
		Bed bed1R13 = new Bed(BedSize.DOUBLE);
		Bed bed2R13 = new Bed(BedSize.DOUBLE);
		Room R13 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R13, bed2R13)), RoomLocation.VAXJO, 113, RoomSize.MEDIUM, false,
				false, false, false, false);

		Bed bed1R14 = new Bed(BedSize.DOUBLE);
		Bed bed2R14 = new Bed(BedSize.DOUBLE);
		Room R14 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R14, bed2R14)), RoomLocation.VAXJO, 114, RoomSize.MEDIUM, true,
				true, true, false, false);
		
		Bed bed1R15 = new Bed(BedSize.DOUBLE);
		Bed bed2R15 = new Bed(BedSize.DOUBLE);
		Room R15 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R15, bed2R15)), RoomLocation.VAXJO, 115, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R16 = new Bed(BedSize.DOUBLE);
		Bed bed2R16 = new Bed(BedSize.DOUBLE);
		Room R16 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R16, bed2R16)), RoomLocation.VAXJO, 116, RoomSize.BIG, true,
				true, true, true, false);
		
		Bed bed1S1R1 = new Bed(BedSize.SINGLE);
		Room S1R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1R1)), RoomLocation.VAXJO, 0, RoomSize.SMALL,
				true, false, false, false, true);
		Bed bed1S1R2 = new Bed(BedSize.SINGLE);
		Bed bed2S1R2 = new Bed(BedSize.SINGLE);
		Room S1R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1R2, bed2S1R2)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM,
				true, false, false, false, true);
		Suite suite1 = new Suite(117, new ArrayList<Room>(Arrays.asList(S1R1, S1R2)));

		Bed bed1S2R1 = new Bed(BedSize.DOUBLE);
		Room S2R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2R1)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM,
				false, false, true, true, true);
		Bed bed1S2R2 = new Bed(BedSize.SINGLE);
		Bed bed2S2R2 = new Bed(BedSize.DOUBLE);
		Room S2R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2R2, bed2S2R2)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM,
				false, true, true, true, true);
		Suite suite2 = new Suite(118, new ArrayList<Room>(Arrays.asList(S2R1, S2R2)));

		Bed bed1S3R1 = new Bed(BedSize.DOUBLE);
		Room S3R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S3R1)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM,
				true, false, true, false, true);
		Bed bed1S3R2 = new Bed(BedSize.SINGLE);
		Bed bed2S3R2 = new Bed(BedSize.DOUBLE);
		Room S3R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S3R2, bed2S3R2)), RoomLocation.VAXJO, 0, RoomSize.BIG,
				true, true, true, false, true);
		Suite suite3 = new Suite(119, new ArrayList<Room>(Arrays.asList(S3R1, S3R2)));

		Bed bed1S4R1 = new Bed(BedSize.DOUBLE);
		Room S4R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S4R1)), RoomLocation.VAXJO, 0, RoomSize.BIG,
				true, true, true, true, true);
		Bed bed1S4R2 = new Bed(BedSize.DOUBLE);
		Bed bed2S4R2 = new Bed(BedSize.DOUBLE);
		Room S4R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S4R2, bed2S4R2)), RoomLocation.VAXJO, 0, RoomSize.BIG,
				true, true, true, true, true);
		Suite suite4 = new Suite(120, new ArrayList<Room>(Arrays.asList(S4R1, S4R2)));
		
		Customer customer1 = new Customer("Alaa Al", LocalDate.of(1982, 01, 01), Gender.MALE, "076970",
				IdentificationType.PASS_NUMBER, "1111111", "Card5555555", "Vaxjo", "Syr", "a@a.a", "student");

		Customer customer2 = new Customer("Basem mo", LocalDate.of(1992, 05, 05), Gender.MALE, "0732323",
				IdentificationType.PASS_NUMBER, "2222222", "Card22222", "Vaxjo", "Syr", "b@b.b", "student");

		Customer customer3 = new Customer("Paul", LocalDate.of(1987, 10, 10), Gender.MALE, "0732323",
				IdentificationType.PASS_NUMBER, "3333333", "Card11111111", "Vaxjo", "???", "p@p.p", "student");

		customer1.addReservation(R1.getRoomId(), R1.getRoomNum(), R1.getRoomLocation(), LocalDate.now(),
				LocalDate.now().plusDays(5), R1.getRate(), 0, 2, "Al reservation");
		customer1.addReservation(R1.getRoomId(), R1.getRoomNum(), R1.getRoomLocation(), LocalDate.now().minusDays(10),
				LocalDate.now().minusDays(5), R1.getRate(), 0, 2, "Old Al reservation");
		customer1.getReservationsList().get(1).setReservationStatus(ReservationStatus.CHECKED_OUT);
		customer1.getReservationsList().get(0).getBill().addService(ServiceType.ITEM, 1, 2, "Coca cola");
		;
		customer1.getReservationsList().get(1).getBill().addService(ServiceType.ITEM, 2, 2, "Pepsi");
		;
		customer1.getReservationsList().get(1).getBill().setPayStatus(PayStatus.PAID);
		customer2.addReservation(R2.getRoomId(), R2.getRoomNum(), R2.getRoomLocation(), LocalDate.now().minusDays(1),
				LocalDate.now().plusDays(5), R2.getRate(), 0, 1, "Ba reservation");
		customer2.getReservationsList().get(0).setReservationStatus(ReservationStatus.CHECKED_IN);
		customer3.addReservation(R3.getRoomId(), R3.getRoomNum(), R3.getRoomLocation(), LocalDate.now().minusDays(1),
				LocalDate.now(), R3.getRate(), 0, 4, "Pa reservation vaxjo");
		customer3.addReservation(suite1.getRoomId(), suite1.getRoomNum(), suite1.getRoomLocation(),
				LocalDate.now().minusDays(1), LocalDate.now(), suite1.getRate(), 0, 4, "Pa reservation kalmar");
		customer3.addReservation(suite2.getRoomId(), suite2.getRoomNum(), suite2.getRoomLocation(),
				LocalDate.now().minusDays(1), LocalDate.now(), suite2.getRate(), 0, 4, "Pa reservation vaxjo");
		customer3.addReservation(suite2.getRoomId(), suite2.getRoomNum(), suite2.getRoomLocation(), LocalDate.now(),
				LocalDate.now().plusDays(2), suite2.getRate(), 0, 4, "Pa reservation vaxjo");
		customer3.getReservationsList().get(0).setReservationStatus(ReservationStatus.CHECKED_IN);
		customer3.getReservationsList().get(2).setReservationStatus(ReservationStatus.CHECKED_IN);
		
		customersList.addAll(new ArrayList<Customer>(Arrays.asList(customer1, customer2, customer3)));
		roomsList.addAll(new ArrayList<Room>(Arrays.asList(R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16)));
		suitesList.addAll(new ArrayList<Suite>(Arrays.asList(suite1, suite2, suite3, suite4)));

	}
		{
		Bed bed1R1 = new Bed(BedSize.SINGLE);
		Room R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.KALMAR, 101, RoomSize.SMALL, false,
				false, false, false, false);
		
		Bed bed1R2 = new Bed(BedSize.SINGLE);
		Room R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R2)), RoomLocation.KALMAR, 102, RoomSize.SMALL, true,
				true, true, false, false);
		
		Bed bed1R3 = new Bed(BedSize.SINGLE);
		Room R3 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R3)), RoomLocation.KALMAR, 103, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R4 = new Bed(BedSize.SINGLE);
		Room R4 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R4)), RoomLocation.KALMAR, 104, RoomSize.MEDIUM, true,
				true, true, true, false);
		
		Bed bed1R5 = new Bed(BedSize.DOUBLE);
		Room R5 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R5)), RoomLocation.KALMAR, 105, RoomSize.SMALL, false,
				false, false, false, false);
		
		Bed bed1R6 = new Bed(BedSize.DOUBLE);
		Room R6 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R6)), RoomLocation.KALMAR, 106, RoomSize.SMALL, true,
				true, true, false, false);
		
		Bed bed1R7 = new Bed(BedSize.DOUBLE);
		Room R7 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R7)), RoomLocation.KALMAR, 107, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R8 = new Bed(BedSize.DOUBLE);
		Room R8 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R8)), RoomLocation.KALMAR, 108, RoomSize.BIG,  true,
				true, true, true, false);
		
		Bed bed1R9 = new Bed(BedSize.SINGLE);
		Bed bed2R9 = new Bed(BedSize.DOUBLE);
		Room R9 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R9, bed2R9)), RoomLocation.KALMAR, 109, RoomSize.MEDIUM, false,
				false, false, false, false);
		
		Bed bed1R10 = new Bed(BedSize.SINGLE);
		Bed bed2R10 = new Bed(BedSize.DOUBLE);
		Room R10 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R10, bed2R10)), RoomLocation.KALMAR, 110, RoomSize.MEDIUM, true,
				true, true, false, false);
		
		Bed bed1R11 = new Bed(BedSize.SINGLE);
		Bed bed2R11 = new Bed(BedSize.DOUBLE);
		Room R11 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R11, bed2R11)), RoomLocation.KALMAR, 111, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R12 = new Bed(BedSize.SINGLE);
		Bed bed2R12 = new Bed(BedSize.DOUBLE);
		Room R12 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R12, bed2R12)), RoomLocation.KALMAR, 112, RoomSize.BIG, true,
				true, true, true, false);
		
		Bed bed1R13 = new Bed(BedSize.DOUBLE);
		Bed bed2R13 = new Bed(BedSize.DOUBLE);
		Room R13 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R13, bed2R13)), RoomLocation.KALMAR, 113, RoomSize.MEDIUM, false,
				false, false, false, false);

		Bed bed1R14 = new Bed(BedSize.DOUBLE);
		Bed bed2R14 = new Bed(BedSize.DOUBLE);
		Room R14 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R14, bed2R14)), RoomLocation.KALMAR, 114, RoomSize.MEDIUM, true,
				true, true, false, false);
		
		Bed bed1R15 = new Bed(BedSize.DOUBLE);
		Bed bed2R15 = new Bed(BedSize.DOUBLE);
		Room R15 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R15, bed2R15)), RoomLocation.KALMAR, 115, RoomSize.MEDIUM, false,
				true, true, true, false);
		
		Bed bed1R16 = new Bed(BedSize.DOUBLE);
		Bed bed2R16 = new Bed(BedSize.DOUBLE);
		Room R16 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R16, bed2R16)), RoomLocation.KALMAR, 116, RoomSize.BIG, true,
				true, true, true, false);
		
		Bed bed1S1R1 = new Bed(BedSize.SINGLE);
		Room S1R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1R1)), RoomLocation.KALMAR, 0, RoomSize.SMALL,
				true, false, false, false, true);
		Bed bed1S1R2 = new Bed(BedSize.SINGLE);
		Bed bed2S1R2 = new Bed(BedSize.SINGLE);
		Room S1R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1R2, bed2S1R2)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				true, false, false, false, true);
		Suite suite1 = new Suite(117, new ArrayList<Room>(Arrays.asList(S1R1, S1R2)));

		Bed bed1S2R1 = new Bed(BedSize.DOUBLE);
		Room S2R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2R1)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				false, false, true, true, true);
		Bed bed1S2R2 = new Bed(BedSize.SINGLE);
		Bed bed2S2R2 = new Bed(BedSize.DOUBLE);
		Room S2R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2R2, bed2S2R2)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				false, true, true, true, true);
		Suite suite2 = new Suite(118, new ArrayList<Room>(Arrays.asList(S2R1, S2R2)));

		Bed bed1S3R1 = new Bed(BedSize.DOUBLE);
		Room S3R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S3R1)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				true, false, true, false, true);
		Bed bed1S3R2 = new Bed(BedSize.SINGLE);
		Bed bed2S3R2 = new Bed(BedSize.DOUBLE);
		Room S3R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S3R2, bed2S3R2)), RoomLocation.KALMAR, 0, RoomSize.BIG,
				true, true, true, false, true);
		Suite suite3 = new Suite(119, new ArrayList<Room>(Arrays.asList(S3R1, S3R2)));

		Bed bed1S4R1 = new Bed(BedSize.DOUBLE);
		Room S4R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S4R1)), RoomLocation.KALMAR, 0, RoomSize.BIG,
				true, true, true, true, true);
		Bed bed1S4R2 = new Bed(BedSize.DOUBLE);
		Bed bed2S4R2 = new Bed(BedSize.DOUBLE);
		Room S4R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1S4R2, bed2S4R2)), RoomLocation.KALMAR, 0, RoomSize.BIG,
				true, true, true, true, true);
		Suite suite4 = new Suite(120, new ArrayList<Room>(Arrays.asList(S4R1, S4R2)));
		roomsList.addAll(new ArrayList<Room>(Arrays.asList(R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16)));
		suitesList.addAll(new ArrayList<Suite>(Arrays.asList(suite1, suite2, suite3, suite4)));

		}
		

		
		servicesList.add(new Service(ServiceType.ITEM, 1, 2, "Coca cola"));
		servicesList.add(new Service(ServiceType.ITEM, 2, 2, "Pepsi"));
	
	}

}
