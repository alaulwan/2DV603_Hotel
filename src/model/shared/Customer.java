package model.shared;

import java.time.LocalDate;
import java.util.ArrayList;
import model.shared.Reservation.ReservationStatus;

public class Customer {
	public enum IdentificationType { PERSONAL_NUMBER, PASS_NUMBER }
	
	private static int count = 0;
	private int costomerId;
	private String name;
	private String birthDate;
	private String mobileNum;
	private IdentificationType identificationType;
	private String identificationNumber;
	private String address;
	private String nationality;
	private String email;
	private String description;
	private ArrayList <Reservation> reservationsList = new ArrayList<Reservation>();
	//private ArrayList <Bill> billsList = new ArrayList<Bill>();
	
	public Customer() {
		
	}

	public Customer(String name, LocalDate birthDate, String mobileNum, IdentificationType identificationType,String identificationNumber, String address, String nationality,
			String email, String description) {
		this.setCostomerId(++count);
		this.name = name;
		this.setBirthDate(birthDate);
		this.mobileNum = mobileNum;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.address = address;
		this.nationality = nationality;
		this.email = email;
		this.description = description;
	}
	
	public void addReservation(ArrayList<Room> roomList, float discount, String description) {
		Reservation reservation = new Reservation(ReservationStatus.PENDING, this.getCostomerId(), roomList, discount, description );
		reservationsList.add(reservation);
		for (Room room : roomList) {
			room.setAvailable(false);
		}
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Customer.count = count;
	}

	public int getCostomerId() {
		return costomerId;
	}

	public void setCostomerId(int costomerId) {
		this.costomerId = costomerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
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

	/*public ArrayList<Bill> getBillsList() {
		return billsList;
	}

	public void setBillsList(ArrayList<Bill> billsList) {
		this.billsList = billsList;
	}*/

}


