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
	private ArrayList<Suite> suitesList = new ArrayList<Suite>();
	private ArrayList<Service> servicesList = new ArrayList<Service>();

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

	public boolean chekInReservation(int reservationId) {
		Reservation res = this.getReservationById(reservationId);
		if (res != null && res.checkIn()) {
			this.getRoomById(res.getRoomId()).setRoomStatus(RoomStatus.OCCUPIED);
			return true;
		}
		return false;
	}

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
		Bed bed1R1 = new Bed(BedSize.SINGLE);
		Room R1 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 1, RoomSize.MEDIUM, false,
				true, true, false, false);

		Bed bed1R2 = new Bed(BedSize.DOUBLE);
		Room R2 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R2)), RoomLocation.VAXJO, 2, RoomSize.MEDIUM, false,
				true, true, false, false);

		Bed bed1S1Ra = new Bed(BedSize.DOUBLE);
		Room S1Ra = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1Ra)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				false, true, true, false, false);
		Bed bed1S1Rb = new Bed(BedSize.SINGLE);
		Room S1Rb = new Room(new ArrayList<Bed>(Arrays.asList(bed1S1Rb)), RoomLocation.KALMAR, 0, RoomSize.MEDIUM,
				false, true, true, false, false);
		Suite suite1 = new Suite(3, new ArrayList<Room>(Arrays.asList(S1Ra, S1Rb)));

		Bed bed1S2Ra = new Bed(BedSize.DOUBLE);
		Room S2Ra = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2Ra)), RoomLocation.VAXJO, 0, RoomSize.MEDIUM, false,
				true, true, false, false);
		Bed bed1S2Rb = new Bed(BedSize.SINGLE);
		Bed bed2S2Rb = new Bed(BedSize.SINGLE);
		Room S2Rb = new Room(new ArrayList<Bed>(Arrays.asList(bed1S2Rb, bed2S2Rb)), RoomLocation.VAXJO, 0,
				RoomSize.MEDIUM, false, true, true, false, false);
		Suite suite2 = new Suite(4, new ArrayList<Room>(Arrays.asList(S2Ra, S2Rb)));

		Room R3 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1, bed1R2)), RoomLocation.VAXJO, 3, RoomSize.MEDIUM,
				false, true, true, false, false);
		Room R4 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1, bed1R2)), RoomLocation.VAXJO, 5, RoomSize.MEDIUM,
				false, true, true, false, false);
		Room R5 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 6, RoomSize.MEDIUM, false,
				true, true, false, false);
		Room R6 = new Room(new ArrayList<Bed>(Arrays.asList(bed1R1)), RoomLocation.VAXJO, 7, RoomSize.MEDIUM, false,
				true, true, false, false);

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

		servicesList.add(new Service(ServiceType.ITEM, 1, 2, "Coca cola"));
		servicesList.add(new Service(ServiceType.ITEM, 2, 2, "Pepsi"));
		customersList.addAll(new ArrayList<Customer>(Arrays.asList(customer1, customer2, customer3)));
		roomsList.addAll(new ArrayList<Room>(Arrays.asList(R1, R2, R3, R4, R5, R6)));
		suitesList.addAll(new ArrayList<Suite>(Arrays.asList(suite1, suite2)));

	}

}
