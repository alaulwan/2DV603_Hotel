package model.server;


import model.shared.Hotel;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.*;

public class HotelServer {
	public HotelDAO DAO = new HotelDAO();
	public static Hotel hotel = new Hotel();
	public static RoomsStatusUpdaterThread RoomsStatusUpdaterThread = new RoomsStatusUpdaterThread();
	
	public static final int BUFSIZE = 1024;
	public static final int MYPORT = 4444;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException{
		HotelServer hotelServer = new HotelServer();
		
		
		/* Create Server Socket */
		ServerSocket serverSocket=null;
		try {
			serverSocket = new ServerSocket(MYPORT);
		}catch (Exception e) {
			System.err.println("The port is token.");
			System.exit(0);
		}
		hotelServer.test();
		
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
	
	
	
	public void test (){
		hotel.defaultHotel();
		DAO.xmlSave (hotel);
		
		Gson gson = new GsonBuilder().create();
		String j = gson.toJson(hotel);
		//System.out.println(j);
		
		Hotel hotel2 = DAO.xmlLoad ();
		String j2 = gson.toJson(hotel2);
		//System.out.println(j2);
		
		Hotel hotel3 = gson.fromJson(j, Hotel.class);
		String j3 = gson.toJson(hotel3);
		
		System.out.println(j2.equals(j));
		System.out.println(j3.equals(j));
		
		String jArr = gson.toJson(hotel3.getCustomersList());
		
		String jArr2 = gson.toJson(hotel3.getCustomersList());
		System.out.println(jArr.equals(jArr2));
		
		
		RoomsStatusUpdaterThread.start();
	}
	
}
