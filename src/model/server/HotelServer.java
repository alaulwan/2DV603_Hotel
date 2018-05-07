package model.server;

import model.shared.Hotel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.*;

public class HotelServer {
	public HotelDAO DAO = new HotelDAO();
	public Hotel hotel = new Hotel();
	public RoomsStatusUpdaterThread RoomsStatusUpdaterThread;
	public SavingThread savingThread;

	public final int BUFSIZE = 1024;
	public final int MYPORT = 4444;
	
	@SuppressWarnings("resource")
	public void startServer() {
		this.test();

		/* Create Server Socket */
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(MYPORT);
		} catch (Exception e) {
			System.err.println("The port is token.");
			System.exit(0);
		}
		System.out.println("Hotel Server is Running on the port: " + MYPORT + ", Buffer Size: " + BUFSIZE);
		while (true) {
			/* Create Socket and wait for a client */
			Socket socket;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			/* Create a thread for each client */
			HotelServerThread httpServerThread = new HotelServerThread(hotel, savingThread, socket, BUFSIZE);
			httpServerThread.start();
		}
	}

	// Method to load the hotel from XML file
	public void load() {
		hotel = DAO.xmlLoad();
	}

	// This method to save the hotel-information silently to the database after each editing to the information
	public class SavingThread extends Thread {
		// Request queue to save hotel-information
		public ArrayList<Boolean> saveRequestList = new ArrayList<Boolean>();

		public SavingThread() {

		}

		@Override
		public void run() {
			// Loop for ever
			while (true) {
				// While there is save-request, do new save
				while (!saveRequestList.isEmpty()) {
					if (saveRequestList.remove(0)) {
						DAO.xmlSave(hotel);
					}
				}
				try {
					// Sleep until an interrupting
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
				}
			}

		}
	}

	public void test() {
		hotel.defaultHotel();
		DAO.xmlSave(hotel);
		

		Gson gson = new GsonBuilder().create();
		String j = gson.toJson(hotel);
		// System.out.println(j);

		Hotel hotel2 = DAO.xmlLoad();
		String j2 = gson.toJson(hotel2);
		// System.out.println(j2);

		System.out.println(j2.equals(j));

		RoomsStatusUpdaterThread = new RoomsStatusUpdaterThread(hotel);
		RoomsStatusUpdaterThread.start();
		savingThread = new SavingThread();
		savingThread.start();
	}

}
