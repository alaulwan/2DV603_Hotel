package model.shared;

import java.util.ArrayList;

import model.shared.Bed.Size;

public class Room {

	private String id;
	private String address;
	private int roomNum;
	private int maxGuestCapacity;
	private float size;
	private int qualityLev = 3; // the minimal quality level is 3, the costs will be calculated in the
								// reservation class
	private boolean airCon;
	private boolean balcony;
	private boolean view;
	private boolean smoking;
	private boolean adjoinable;
	private boolean available;
	private ArrayList<Bed> beds = new ArrayList<Bed>();
	

	public Room() {

	}

	public Room(ArrayList<Bed> beds, String address, int roomNum, int size, boolean airCon, boolean balcony,
			boolean view, boolean smoking, boolean adjoinable) {

		this.beds = beds;
		this.address = address;
		this.roomNum = roomNum;
		this.size = size;
		this.airCon = airCon;
		this.balcony = balcony;
		this.view = view;
		this.smoking = smoking;
		this.adjoinable = adjoinable;

		setId();
		setQualityLev();
		setMaxGuestCapacity();

	}

	public String getId() {
		return id;
	}

	public void setId() {
		this.id = this.address + "-" + this.roomNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		setId();
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
		setId();
	}

	public int getMaxGuestCapacity() {
		return maxGuestCapacity;
	}

	// set the max guest capacity of the room depends on the beds size
	public void setMaxGuestCapacity() {
		for (Bed a : beds) {
			if (a.getSize() == Size.SINGLE)
				maxGuestCapacity++;
			else if (a.getSize() == Size.DOUBLE)
				maxGuestCapacity += 2;
		}
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
		setQualityLev();
	}

	public int getQualityLev() {
		return qualityLev;
	}

	// set the quality level depends on the room size , air conditioner, balcony,
	// and view
	// TODO should modify it for the adjoin ability
	public void setQualityLev() {
		if (size > 9 && size <= 16)
			qualityLev++;
		if (size > 16)
			qualityLev += 2;
		if (isAirCon())
			qualityLev++;
		if (isBalcony())
			qualityLev++;
		if (isView())
			qualityLev++;
	}

	public boolean isAirCon() {
		return airCon;
	}

	public void setAirCon(boolean airCon) {
		this.airCon = airCon;
		setQualityLev();
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
		setQualityLev();
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
		setQualityLev();
	}

	public boolean isSmoking() {
		return smoking;
	}

	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}

	public boolean isAdjoinable() {
		return adjoinable;
	}

	public void setAdjoinable(boolean adjoinable) {
		this.adjoinable = adjoinable;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public ArrayList<Bed> getBedsList() {
		return beds;
	}

	public int getBedsNum() {
		return this.beds.size();
	}

	public void setBedsList(ArrayList<Bed> beds) {
		this.beds = beds;
		setMaxGuestCapacity();
	}

	public void addBed(Bed bed) {
		this.beds.add(bed);
		setMaxGuestCapacity();
	}

	public void removeBed(Bed bed) {
		this.beds.remove(bed);
		setMaxGuestCapacity();
	}

}
