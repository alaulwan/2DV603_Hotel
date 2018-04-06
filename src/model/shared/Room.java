package model.shared;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Bed.BedSize;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	public enum RoomSize { SMALL, MEDIUM, BIG }
	public enum RoomLocation { VAXJO, KALMAR }
	public enum RoomStatus { AVAILABLE, OCCUPIED,CHEKIN_TODAY, CHECKOUT_TODAY }
	private RoomStatus RoomStatus;
	private int roomId;
	private RoomLocation roomLocation;
	private int roomNum;
	private int maxGuestCapacity;
	private RoomSize roomSize;
	private int qualityLev = 3; // the minimal quality level is 3, the costs will be calculated in the
								// reservation class
	private boolean suite;
	private float rate;
	private boolean airCon;
	private boolean balcony;
	private boolean view;
	private boolean smoking;
	
	
	private ArrayList<Bed> bedsList = new ArrayList<Bed>();
	
	public Room() {
		
	}

	public Room(ArrayList<Bed> beds, RoomLocation roomLocation, int roomNum, RoomSize roomSize, boolean airCon, boolean balcony,
			boolean view, boolean smoking, boolean isSuite) {

		this.roomId = ++count;
		this.bedsList = beds;
		this.roomLocation = roomLocation;
		this.roomNum = roomNum;
		this.roomSize = roomSize;
		this.airCon = airCon;
		this.balcony = balcony;
		this.view = view;
		this.smoking = smoking;
		this.suite = isSuite;
		if (!isSuite) {
			calculateQualityLev();
			calculateMaxGuestCapacity();
		}
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId (int roomId) {
		this.roomId = roomId;
	}
	
	public RoomStatus getRoomStatus() {
		return RoomStatus;
	}

	public void setRoomStatus(RoomStatus roomStatus) {
		RoomStatus = roomStatus;
	}

	public RoomLocation getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(RoomLocation roomLocation) {
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
		for (Bed bed : bedsList) {
			if (bed.getBedSize() == BedSize.SINGLE)
				maxGuestCapacity++;
			else if (bed.getBedSize() == BedSize.DOUBLE)
				maxGuestCapacity += 2;
		}
	}
	
	public boolean isSuite() {
		return suite;
	}

	public void setSuite(boolean suite) {
		this.suite = suite;
	}

	public RoomSize getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(RoomSize roomSize) {
		this.roomSize = roomSize;
		calculateQualityLev();
	}

	public int getQualityLev() {
		return qualityLev;
	}
	
	public void setQualityLev(int qualityLev) {
		this.qualityLev = qualityLev;
		calculateRate();
	}

	// set the quality level depends on the room size , air conditioner, balcony,
	// and view
	// TODO should modify it for the adjoin ability
	public void calculateQualityLev() {
		qualityLev=3;
		if (roomSize == RoomSize.MEDIUM)
			qualityLev++;
		if (roomSize == RoomSize.BIG)
			qualityLev += 2;
		if (isAirCon())
			qualityLev++;
		if (isBalcony())
			qualityLev++;
		if (isView())
			qualityLev++;
		
		calculateRate();
	}
	
	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public void calculateRate() {
		this.rate = getQualityLev() * 10;
	}

	public boolean isAirCon() {
		return airCon;
	}

	public void setAirCon(boolean airCon) {
		this.airCon = airCon;
		if (!isSuite())
			calculateQualityLev();
	}

	public boolean isBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
		calculateQualityLev();
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
		calculateQualityLev();
	}

	public boolean isSmoking() {
		return smoking;
	}

	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}

	public ArrayList<Bed> getBedsList() {
		return bedsList;
	}

	public int getBedsNum() {
		return this.bedsList.size();
	}

	public void setBedsList(ArrayList<Bed> beds) {
		this.bedsList = beds;
		calculateMaxGuestCapacity();
	}

	public void addBed(Bed bed) {
		this.bedsList.add(bed);
		calculateMaxGuestCapacity();
	}

	public void removeBed(Bed bed) {
		this.bedsList.remove(bed);
		calculateMaxGuestCapacity();
	}

}
