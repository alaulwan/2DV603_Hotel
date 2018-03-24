package model.shared;

import java.util.ArrayList;

import model.shared.Bed.BedSize;

public class Room {
	private static int count = 0;
	public enum RoomSize { SMALL, MEDIUM, BIG }
	public enum RoomLocation { VAXJO, KALMAR }
	private int roomId;
	private RoomLocation roomLocation;
	private int roomNum;
	private int maxGuestCapacity;
	private RoomSize roomSize;
	private int qualityLev = 3; // the minimal quality level is 3, the costs will be calculated in the
								// reservation class
	private float rate;
	private float discount;
	private boolean airCon;
	private boolean balcony;
	private boolean view;
	private boolean smoking;
	private boolean adjoinable;
	private boolean available;
	private ArrayList<Bed> beds = new ArrayList<Bed>();
	

	public Room() {

	}

	public Room(ArrayList<Bed> beds, RoomLocation roomLocation, int roomNum, RoomSize roomSize, boolean airCon, boolean balcony,
			boolean view, boolean smoking, boolean adjoinable) {

		this.roomId = ++count;
		this.beds = beds;
		this.roomLocation = roomLocation;
		this.roomNum = roomNum;
		this.roomSize = roomSize;
		this.airCon = airCon;
		this.balcony = balcony;
		this.view = view;
		this.smoking = smoking;
		this.adjoinable = adjoinable;
		setQualityLev();
		calculateMaxGuestCapacity();

	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId (int roomId) {
		this.roomId = roomId;
	}
	
	public RoomLocation getAddress() {
		return roomLocation;
	}

	public void setAddress(RoomLocation roomLocation) {
		this.roomLocation = roomLocation;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getMaxGuestCapacity() {
		return maxGuestCapacity;
	}
	
	public void setMaxGuestCapacity(int maxGuestCapacity) {
		this.maxGuestCapacity = maxGuestCapacity;
	}

	// set the max guest capacity of the room depends on the beds size
	public void calculateMaxGuestCapacity() {
		for (Bed bed : beds) {
			if (bed.getSize() == BedSize.SINGLE)
				maxGuestCapacity++;
			else if (bed.getSize() == BedSize.DOUBLE)
				maxGuestCapacity += 2;
		}
	}

	public RoomSize getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(RoomSize roomSize) {
		this.roomSize = roomSize;
		setQualityLev();
	}

	public int getQualityLev() {
		return qualityLev;
	}
	
	public void setQualityLev(int qualityLev) {
		this.qualityLev = qualityLev;
	}

	// set the quality level depends on the room size , air conditioner, balcony,
	// and view
	// TODO should modify it for the adjoin ability
	public void setQualityLev() {
		if (roomSize == RoomSize.MEDIUM)
			qualityLev++;
		if (roomSize == RoomSize.BIG)
			qualityLev += 2;
		if (hasAirCon())
			qualityLev++;
		if (hasBalcony())
			qualityLev++;
		if (hasView())
			qualityLev++;
	}
	
	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		if (discount < 0) {
			this.discount = 0;
		}
		else if (discount > 100) {
			this.discount = 100;
		}
		else {
			this.discount = discount;
		}
		
	}

	public boolean hasAirCon() {
		return airCon;
	}

	public void setAirCon(boolean airCon) {
		this.airCon = airCon;
		setQualityLev();
	}

	public boolean hasBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
		setQualityLev();
	}

	public boolean hasView() {
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
		calculateMaxGuestCapacity();
	}

	public void addBed(Bed bed) {
		this.beds.add(bed);
		calculateMaxGuestCapacity();
	}

	public void removeBed(Bed bed) {
		this.beds.remove(bed);
		calculateMaxGuestCapacity();
	}

}
