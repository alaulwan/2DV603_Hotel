package model.server;

import model.shared.Hotel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HotelServer {
	public HotelDAO DAO = new HotelDAO();
	public Hotel hotel = new Hotel();
	public RoomsStatusUpdaterThread RoomsStatusUpdaterThread;
	public SavingThread savingThread;

	public final int MYPORT = 4444;
	
	@SuppressWarnings("resource")
	public void startServer() {
		load();
		RoomsStatusUpdaterThread = new RoomsStatusUpdaterThread(hotel);
		RoomsStatusUpdaterThread.start();
		savingThread = new SavingThread();
		savingThread.start();

		/* Create Server Socket */
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(MYPORT);
		} catch (Exception e) {
			System.err.println("The port is token.");
			System.exit(0);
		}
		System.out.println("Hotel Server is Running on the port: " + MYPORT);
		while (true) {
			/* Create Socket and wait for a client */
			Socket socket;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			/* Create a thread for each client */
			HotelServerThread httpServerThread = new HotelServerThread(hotel, savingThread, socket);
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
}
