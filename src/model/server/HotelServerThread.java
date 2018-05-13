package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.server.HotelServer.SavingThread;
import model.server.response.Response;
import model.server.response.ResponseFactory;
import model.shared.Hotel;
import model.shared.requests.Request;

public class HotelServerThread extends Thread {
	private Socket socket;
	public Hotel hotel;
	public SavingThread savingThread;
	public static int counter = 0;
	@SuppressWarnings("unused")
	private final int clientId; // This will be used if you enable the printing in the console when close the
								// connection
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	// This thread is to serve an client-request (one thread for each request)
	public HotelServerThread(Hotel hotel, SavingThread savingThread, Socket socket) {
		this.socket = socket;
		this.hotel = hotel;
		this.savingThread = savingThread;
		this.clientId = ++counter;
		try {
			objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
			objectInputStream = new ObjectInputStream(this.socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Request recievedRequest = null;
		try {
			recievedRequest = (Request) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (recievedRequest != null) {
			// Create a response factory to generate a suitable response according to the
			// request
			ResponseFactory responseFactory = new ResponseFactory(hotel, savingThread, recievedRequest);

			// Generate the response.
			Response response = responseFactory.getResponse();

			// Send the response to the client
			response.sendObject(objectOutputStream);
		}

		// close the socket
		closeSoket();

	}

	// Method to close the socket. You can enable the printing client-id at the end
	// of the connection and confirm the closing
	private void closeSoket() {
		try {
			socket.close();
//			System.out.println("\n******* Client " + clientId + ": connection is closed *******\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
