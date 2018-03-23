package model.shared;

import java.time.LocalDate;

public class Customer {
	public enum IdentificationType { PERSONAL_NUMBER, PASS_NUMBER }
	
	private static int count = 0;
	private int costomerId;
	private String name;
	private LocalDate birthDate;
	private String mobileNum;
	private IdentificationType identificationType;
	private String identificationNumber;
	private String address;
	private String nationality;
	private String email;
	private String description;

	public Customer() {
		
	}

	public Customer(String name, LocalDate birthDate, String mobileNum, IdentificationType identificationType,String identificationNumber, String address, String nationality,
			String email, String description) {
		this.setCostomerId(++count);
		this.name = name;
		this.birthDate = birthDate;
		this.mobileNum = mobileNum;
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.address = address;
		this.nationality = nationality;
		this.email = email;
		this.description = description;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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

}
