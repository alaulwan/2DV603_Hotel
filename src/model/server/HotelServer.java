package model.server;


import model.shared.Hotel;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import com.google.gson.*;

public class HotelServer {
	public HotelDAO DAO = new HotelDAO();
	public static Hotel hotel = new Hotel();
	
	public static final int BUFSIZE = 1024;
	public static final int MYPORT = 4444;
	
	public static void main(String[] args) throws IOException {
		HotelServer hotelServer = new HotelServer();
		hotelServer.test();
		
		/* Create Server Socket */
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(MYPORT);
		System.out.println("Hotel Server is Running on the port: " + MYPORT + ", Buffer Size: " + BUFSIZE);
		while (true)
		 {
			/* Create Socket and wait for a client */
			Socket socket = serverSocket.accept();
			/* Create a thread for each client */
			HotelServerThread httpServerThread = new HotelServerThread(socket, BUFSIZE);
			httpServerThread.start();
			
		}
	}
	
	public void load () {
		hotel = DAO.xmlLoad ();
	}
	
	public class RoomsStatusUpdater extends Thread {		
		public RoomsStatusUpdater (){
			
		}
		
		@Override
		public void run() {
			while (true) {
				LocalDateTime timeNow = LocalDateTime.now();
				LocalDateTime timetomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0, 0));
				Duration duration = Duration.between(timeNow, timetomorrow);
				LocalDate todayDate = LocalDate.now();
				ArrayList<Room> roomsList = new ArrayList<Room> (hotel.getRoomsAndSuitesList());
				for (Room r : roomsList) {
					r.setRoomStatus(RoomStatus.AVAILABLE);
				}
				
				ArrayList<Reservation> pindingCheckedInReservationsList = new ArrayList <Reservation> (hotel.getReservationsList());
				ReservationsFilter reservationsFilter = new StatusReservationsFilter (true, true, false, false);
				reservationsFilter.applyReservationsFilter(pindingCheckedInReservationsList);
				
				for (Reservation res : pindingCheckedInReservationsList) {
					Room r = hotel.getRoomById(res.getRoomId());
					 if (todayDate.equals(res.checkInDateAsLocalDate()))
						 r.setRoomStatus(RoomStatus.CHEKIN_TODAY);
					 else if (todayDate.equals(res.checkOutDateAsLocalDate()))
						 r.setRoomStatus(RoomStatus.CHECKOUT_TODAY);
					 else if (todayDate.isAfter(res.checkInDateAsLocalDate()) && todayDate.isBefore(res.checkOutDateAsLocalDate()))
						 r.setRoomStatus(RoomStatus.OCCUPIED);
				}
				
				try {
					Thread.sleep(duration.toMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void test (){
		hotel.defaultHotel();
		
		RoomsStatusUpdater RoomsStatusUpdater = new RoomsStatusUpdater();
		RoomsStatusUpdater.start();
		
		DAO.xmlSave (hotel);
		
		Gson gson = new GsonBuilder().create();
		String j = gson.toJson(hotel);
		
		Hotel hotel2 = DAO.xmlLoad ();
		String j2 = gson.toJson(hotel2);
		
		Hotel hotel3 = gson.fromJson(j, Hotel.class);
		String j3 = gson.toJson(hotel3);
		
		System.out.println(j2.equals(j));
		System.out.println(j3.equals(j));
		
		String jArr = gson.toJson(hotel3.getCustomersList());
		
		String jArr2 = gson.toJson(hotel3.getCustomersList());
		System.out.println(jArr.equals(jArr2));
	}
	
}
