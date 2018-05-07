package model.server;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import model.shared.Hotel;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;

// Thread to update the status of the reservatons and the room everyday at 00:00:00 O'clock
public class RoomsStatusUpdaterThread extends Thread {
	public Hotel hotel;

	public RoomsStatusUpdaterThread(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public void run() {
		while (true) {
			updateAllReservationsInfo();
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
	
	// Update the reservation-status. For example, cancel all pending reservations if the guest did not check within the defined time
	public void updateAllReservationsInfo() {
		ArrayList<Reservation> reservationsList = new ArrayList<Reservation>(hotel.getReservationsList());
		for (Reservation res : reservationsList) {
			if (res.getReservationStatus().equals(ReservationStatus.PENDING) && res.checkInDateAsLocalDate().isBefore(LocalDate.now())) {
				hotel.cancelReservation(res.getReservationId());
			}
			else if (res.getReservationStatus().equals(ReservationStatus.CHECKED_IN) && res.checkOutDateAsLocalDate().isBefore(LocalDate.now())) {
				res.setCheckOutDate(LocalDate.now());
			}
		}
	}

	// Update the reservation-status. For example, define which rooms should checked out today
	public void updateAllRoomsStatus() {
		LocalDate todayDate = LocalDate.now();
		ArrayList<Room> roomsList = new ArrayList<Room>(hotel.getRoomsAndSuitesList());
		for (Room r : roomsList) {
			r.setRoomStatus(RoomStatus.AVAILABLE);
		}

		ArrayList<Reservation> pindingCheckedInReservationsList = new ArrayList<Reservation>(
				hotel.getReservationsList());
		ReservationsFilter reservationsFilter = new StatusReservationsFilter(true, true, false, false);
		reservationsFilter.applyReservationsFilter(pindingCheckedInReservationsList);

		for (Reservation res : pindingCheckedInReservationsList) {
			Room r = hotel.getRoomById(res.getRoomId());
			if (todayDate.equals(res.checkInDateAsLocalDate())) {
				if (r.getRoomStatus().equals(RoomStatus.CHECKOUT_TODAY))
					r.setRoomStatus(RoomStatus.CHECK_OUT_IN);
				else
					r.setRoomStatus(RoomStatus.CHECKIN_TODAY);
			} else if (todayDate.equals(res.checkOutDateAsLocalDate())) {
				if (r.getRoomStatus().equals(RoomStatus.CHECKIN_TODAY))
					r.setRoomStatus(RoomStatus.CHECK_OUT_IN);
				else
					r.setRoomStatus(RoomStatus.CHECKOUT_TODAY);
			} else if (todayDate.isAfter(res.checkInDateAsLocalDate())
					&& todayDate.isBefore(res.checkOutDateAsLocalDate()))
				r.setRoomStatus(RoomStatus.OCCUPIED);
		}
	}

}
