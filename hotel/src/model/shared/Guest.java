package model.shared;

import java.time.LocalDate;

public class Guest {
	private String id;
	private String name;
	private LocalDate birthDate;
	private String mobileNum;
	private String passNum;
	private String address;
	private String nationality;
	private String email;

	public Guest() {
	}

	public Guest(String name, LocalDate birthDate, String mobileNum, String passNum, String address, String nationality,
			String email) {
		this.name = name;
		this.birthDate = birthDate;
		this.mobileNum = mobileNum;
		this.passNum = passNum;
		this.address = address;
		this.nationality = nationality;
		this.email = email;
		setId();
	}

	public String getId() {
		return id;
	}

	public void setId() {
		this.id = this.name.substring(0, 2) + "-" + this.birthDate.toString() + "-" + this.passNum.substring(0, 4);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setId();
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		setId();
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getPassNum() {
		return passNum;
	}

	public void setPassNum(String passNum) {
		this.passNum = passNum;
		setId();
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

}
