package model.server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;

public class RoomsStatusUpdaterThread extends Thread {
	public RoomsStatusUpdaterThread (){
		
	}
	
	@Override
	public void run() {
		while (true) {
			updateAllRoomsStatus();
			LocalDateTime timeNow = LocalDateTime.now();
			LocalDateTime timetomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0, 0));
			Duration duration = Duration.between(timeNow, timetomorrow);

			try {
				Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
				
			}
		}
	}

	public void updateAllRoomsStatus() {
		LocalDate todayDate = LocalDate.now();
		ArrayList<Room> roomsList = new ArrayList<Room> (HotelServer.hotel.getRoomsAndSuitesList());
		for (Room r : roomsList) {
			r.setRoomStatus(RoomStatus.AVAILABLE);
		}
		
		ArrayList<Reservation> pindingCheckedInReservationsList = new ArrayList <Reservation> (HotelServer.hotel.getReservationsList());
		ReservationsFilter reservationsFilter = new StatusReservationsFilter (true, true, false, false);
		reservationsFilter.applyReservationsFilter(pindingCheckedInReservationsList);
		
		for (Reservation res : pindingCheckedInReservationsList) {
			Room r = HotelServer.hotel.getRoomById(res.getRoomId());
			 if (todayDate.equals(res.checkInDateAsLocalDate())) {
				 if (r.getRoomStatus().equals(RoomStatus.CHECKOUT_TODAY))
					 r.setRoomStatus(RoomStatus.CHECK_OUT_IN);
				 else
					 r.setRoomStatus(RoomStatus.CHEKIN_TODAY);
			 }
			 else if (todayDate.equals(res.checkOutDateAsLocalDate())) {
				 if (r.getRoomStatus().equals(RoomStatus.CHEKIN_TODAY))
					 r.setRoomStatus(RoomStatus.CHECK_OUT_IN);
				 else
					 r.setRoomStatus(RoomStatus.CHECKOUT_TODAY);
			 }
			 else if (todayDate.isAfter(res.checkInDateAsLocalDate()) && todayDate.isBefore(res.checkOutDateAsLocalDate()))
				 r.setRoomStatus(RoomStatus.OCCUPIED);
		}		
	}
	
}
