package model.shared;

import java.util.ArrayList;

public class Suite extends Room {
	private static final long serialVersionUID = 1L;
	private ArrayList<Room> roomsList = new ArrayList<Room>();
	
	public Suite() {
		
	}
	
	public Suite (int roomNum, ArrayList<Room> roomsList) {
		super(null, null, roomNum, null, false, false,
				false, false, true);
		this.setRoomsList(roomsList);
		CalculateBidsList();
		CalculateRoomLocation();
		CalculateRoomSize();
		CalculateAirCon();
		CalculateBalcony();
		CalculateView();
		CalculateSmoking();
		calculateQualityLev();
		calculateRate();
		super.calculateMaxGuestCapacity();
	}

	public ArrayList<Room> getRoomsList() {
		return roomsList;
	}

	public void setRoomsList(ArrayList<Room> roomsList) {
		this.roomsList = roomsList;
		CalculateBidsList();
		CalculateRoomLocation();
		CalculateRoomSize();
		CalculateAirCon();
		CalculateBalcony();
		CalculateView();
		CalculateSmoking();
		calculateQualityLev();
		calculateRate();
		super.calculateMaxGuestCapacity();
	}

	private void CalculateBidsList() {
		this.setBedsList(new ArrayList <Bed>());
		for (Room room : roomsList) {
			this.getBedsList().addAll(room.getBedsList());
		}
		
	}

	private void CalculateSmoking() {
		super.setSmoking(false);
		for (Room room : roomsList) {
			if (room.isSmoking()) {
				super.setSmoking(true);
				return;
			}
		}
	}

	private void CalculateView() {
		super.setView(false);
		for (Room room : roomsList) {
			if (room.isView()) {
				super.setView(true);
				return;
			}
		}
	}

	private void CalculateBalcony() {
		super.setBalcony(false);
		for (Room room : roomsList) {
			if (room.isBalcony()) {
				super.setBalcony(true);
				return;
			}
		}
	}

	private void CalculateAirCon() {
		super.setAirCon(false);
		for (Room room : roomsList) {
			if (room.isAirCon()) {
				super.setAirCon(true);
				return;
			}
		}
	}

	private void CalculateRoomSize() {
		int size =0;
		for (Room room : roomsList) {
			size += room.getRoomSize().ordinal();
		}
		super.setRoomSize(RoomSize.values()[size/roomsList.size()]);
	}

	private void CalculateRoomLocation() {
		for (Room room : roomsList) {
			super.setRoomLocation(room.getRoomLocation());
			return;
		}
	}

	@Override
	public void calculateQualityLev() {
		if (roomsList.size()>0) {
			int qualityLev=0;
			for (Room room : roomsList)
				qualityLev += room.getQualityLev();
			 super.setQualityLev(qualityLev/roomsList.size());
		}
		
	}

	@Override
	public void calculateRate() {
		float rate=0;
		for (Room room : roomsList)
			rate += room.getRate();
		 super.setRate((float) (rate * 0.75));
	}
	
	public int getNumberOfRooms() {
		return roomsList.size();
	}
}
