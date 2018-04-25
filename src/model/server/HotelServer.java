package model.server;

import model.shared.Hotel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.*;

public class HotelServer {
	public HotelDAO DAO = new HotelDAO();
	public static Hotel hotel = new Hotel();
	public static RoomsStatusUpdaterThread RoomsStatusUpdaterThread = new RoomsStatusUpdaterThread();
	public static SavingThread savingThread;

	public static final int BUFSIZE = 1024;
	public static final int MYPORT = 4444;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		HotelServer hotelServer = new HotelServer();

		/* Create Server Socket */
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(MYPORT);
		} catch (Exception e) {
			System.err.println("The port is token.");
			System.exit(0);
		}
		hotelServer.test();

		System.out.println("Hotel Server is Running on the port: " + MYPORT + ", Buffer Size: " + BUFSIZE);
		while (true) {
			/* Create Socket and wait for a client */
			Socket socket = serverSocket.accept();
			/* Create a thread for each client */
			HotelServerThread httpServerThread = new HotelServerThread(socket, BUFSIZE);
			httpServerThread.start();

		}
	}

	public void load() {
		hotel = DAO.xmlLoad();
	}

	public class SavingThread extends Thread {
		public ArrayList<Boolean> saveRequestList = new ArrayList<Boolean>();

		public SavingThread() {

		}

		@Override
		public void run() {
			while (true) {
				while (!saveRequestList.isEmpty()) {
					if (saveRequestList.remove(0)) {
						DAO.xmlSave(hotel);
					}
				}
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
				}
			}

		}
	}

	public void test() {
		hotel.defaultHotel();
		DAO.xmlSave(hotel);
		savingThread = new SavingThread();

		Gson gson = new GsonBuilder().create();
		String j = gson.toJson(hotel);
		// System.out.println(j);

		hotel = DAO.xmlLoad();
		String j2 = gson.toJson(hotel);
		// System.out.println(j2);

		System.out.println(j2.equals(j));

		RoomsStatusUpdaterThread.start();
		savingThread.start();
	}

}
