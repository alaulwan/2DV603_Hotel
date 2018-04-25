package model.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.server.response.Response;
import model.server.response.ResponseFactory;
import model.shared.requests.Request;

public class HotelServerThread extends Thread {
	private Socket socket;
	public static int counter = 0;
	private final int clientId;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public HotelServerThread(Socket socket, int BUFSIZE) {

		this.socket = socket;
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
		
		if (recievedRequest!=null) {

			// Print information about the client and the request's header in the console
			//printRequestSummary(recievedRequest.getRequestAsString());

			 // Create a response factory to generate a suitable response according to the
			 // request
			ResponseFactory responseFactory = new ResponseFactory(recievedRequest);

			// Generate the response. ()
			Response response = responseFactory.getResponse();

			// Send the response to the client
			//response.send(objectOutputStream);
			response.sendObject(objectOutputStream);
		}

		// close the socket
		closeSoket();

	}
	
	// Method to print a request-summary
	@SuppressWarnings("unused")
	private void printRequestSummary(String recievedHeader) {
		if (recievedHeader != null && !recievedHeader.isEmpty()) {
			System.out.printf("\n[Client " + clientId + "] TCP echo request from %s",
					socket.getInetAddress().getHostName());
			System.out.printf(" using port %d\n", socket.getPort());
			System.out.println("Recieved(" + recievedHeader.length() + " bytes):\n" + "Header:\n" + recievedHeader);

		}
	}

	// Method to close the socket and print a tip in the console
	private void closeSoket() {
		try {
			socket.close();
			System.out.println("\n******* Client " + clientId + ": connection is closed *******\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

