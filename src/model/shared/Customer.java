package model.shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomLocation;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum IdentificationType {
		PERSONAL_NUMBER, PASS_NUMBER
	}

	public enum Gender {
		MALE, FEMALE
	}

	private static int count = 0;
	private int customerId;
	private String name;
	private String birthDate;
	private Gender gender;
	private String phoneNum;
	private IdentificationType identificationType;
	private String identificationNumber;
	private String creditCardNum;
	private String address;
	private String nationality;
	private String email;
	private String description;

	private ArrayList<Reservation> reservationsList = new ArrayList<Reservation>();

	public Customer() {

	}

	public Customer(String name, LocalDate birthDate, Gender gender, String mobileNum,
			IdentificationType identificationType, String identificationNumber, String creditCardNum, String address,
			String nationality, String email, String description) {
		this.setCustomerId(++count);
		this.name = name;
		this.setBirthDate(birthDate);
		this.gender = gender;
		this.phoneNum = mobileNum;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.setCreditCardNum(creditCardNum);
		this.address = address;
		this.nationality = nationality;
		this.email = email;
		this.description = description;
	}

	// Method to add reservation to the customer
	public void addReservation(int roomId, int roomNumber, RoomLocation roomLocation, LocalDate checkInDate,
			LocalDate checkOutDate, float roomPrice, float discount, int guestsNumber, String description) {
		Reservation reservation = new Reservation(ReservationStatus.PENDING, this.getCustomerId(), this.getName(),
				roomId, roomNumber, roomLocation, checkInDate, checkOutDate, roomPrice, discount, guestsNumber,
				description);
		reservationsList.add(reservation);
	}
	
	// Method to edit the customer info depending on another customer
	public void updateFrom(Customer customer) {
		updateCustomerInfo(customer.getName(), customer.birthDateToLocaleDate(), customer.getGender(), customer.getPhoneNum(),
				customer.getIdentificationType(), customer.getIdentificationNumber(), customer.getCreditCardNum(), customer.getAddress(), customer.getNationality(),
				customer.getEmail(), customer.getDescription());
	}

	// Method to edit the customer info
	public void updateCustomerInfo(String name, LocalDate birthDate, Gender gender, String mobileNum,
			IdentificationType identificationType, String identificationNumber, String creditCardNum, String address, String nationality,
			String email, String description) {
		this.name = name;
		this.setBirthDate(birthDate);
		this.gender = gender;
		this.phoneNum = mobileNum;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.creditCardNum = creditCardNum;
		this.address = address;
		this.nationality = nationality;
		this.email = email;
		this.description = description;

		// Correct the customer-ID and customer-name for customer's reservations
		for (Reservation r : reservationsList) {
			r.setCustomerId(this.customerId);
			r.setCustomerName(this.name);
		}
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Customer.count = count;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
		for (Reservation rs : this.reservationsList)
			rs.setCustomerId(customerId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		for (Reservation rs : this.reservationsList)
			rs.setCustomerName(name);
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate birthDateToLocaleDate() {
		return LocalDate.parse(birthDate);
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate.toString();
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String mobileNum) {
		this.phoneNum = mobileNum;
	}

	public IdentificationType getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(IdentificationType identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Reservation> getReservationsList() {
		return reservationsList;
	}

	public void setReservationsList(ArrayList<Reservation> reservationsList) {
		this.reservationsList = reservationsList;
	}

	// Method to get the pending and checked in reserved rooms-numbers
	public String getCurrentReservedRooms() {
		String rooms = "";
		int reservationsCounter = 0;
		for (Reservation res : reservationsList) {
			if (res.getReservationStatus() == ReservationStatus.PENDING
					|| res.getReservationStatus() == ReservationStatus.CHECKED_IN) {
				reservationsCounter++;
				rooms += (reservationsCounter == 1 ? "" : ", ") + res.getRoomNumber();
			}
		}
		return rooms;
	}

	// Method to get the total number of the pending and checked in reservations
	public int getCurrentReservedNumbers() {
		int reservationsCounter = 0;
		for (Reservation res : reservationsList) {
			if (res.getReservationStatus() == ReservationStatus.PENDING
					|| res.getReservationStatus() == ReservationStatus.CHECKED_IN) {
				reservationsCounter++;
			}
		}
		return reservationsCounter;
	}

}
