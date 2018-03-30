package model.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import model.server.response.Response;
import model.server.response.ResponseFactory;

public class HotelServerThread extends Thread {
	private Socket socket;
	public static int counter = 0;
	private final int clientId;
	private final int BUFSIZE;
	private InputStream inputStream;
	private OutputStream outputStream;

	public HotelServerThread(Socket socket, int BUFSIZE) {
		this.BUFSIZE = BUFSIZE;
		this.socket = socket;
		this.clientId = ++counter;
		try {
			inputStream = new DataInputStream(this.socket.getInputStream());
			outputStream = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// create an empty message
		String recievedMessage = "";
		int blocksCounter =0;
		int messageLength = 0;
		/*
		 * do loop until the receiving of the current message be done (if the server's
		 * buffer-size is small, then we need more loops)
		 */
		do {
			// create buffer
			byte[] buf = new byte[BUFSIZE];
			int readedBytes = 0;
			try {
				// read and save the received message in the buffer
				readedBytes = inputStream.read(buf);
			} catch (IOException e) {
				closeSoket();
				System.out.println("Error while receiving");
				return;
			}

			// Convert the buffer to string
			if (readedBytes>0)
				recievedMessage += new String(buf,0 , readedBytes);

			blocksCounter++;
			if (blocksCounter == 1) {
				messageLength = calculateMessageLength(recievedMessage);
			}
		} while (recievedMessage.length() < messageLength);
		
		if (recievedMessage != null && !recievedMessage.isEmpty()) {
			// Create new request according to the received message
			Request recievedRequest = new Request(recievedMessage);

			// Print information about the client and the request's header in the console
			printRequestSummary(recievedRequest.getHeader());

			/*
			 * Create a response factory to generate a suitable response according to the
			 * request
			 */
			ResponseFactory responseFactory = new ResponseFactory(recievedRequest);

			// Generate the response. ()
			Response response = responseFactory.getResponse();

			// Send the response to the client
			response.send(outputStream);
		}

		// close the socket
		closeSoket();

	}

	private int calculateMessageLength(String recievedMessage) {
		String lineSeparator = System.getProperty("line.separator");
		if (lineSeparator.equals("\n"))
			lineSeparator="\r\n";
		String [] recievedMessageLines =  recievedMessage.split(lineSeparator);
		
		StringBuilder header = new StringBuilder();
		for (String line : recievedMessageLines) {
			if (line.isEmpty() || line == null || line.equals(lineSeparator))
				break;
			header.append(line + lineSeparator);
		}
		int headerLength= header.toString().length();
		
		int bodyLenhth = 0;
		for (String line : recievedMessageLines) {
			if (line.isEmpty() || line == null || line.equals(lineSeparator))
				break;
			if (line.startsWith("Content-Length")) {
				bodyLenhth = Integer.parseInt(line.substring(16));
				break;
			}
		}
		if (recievedMessage.isEmpty())
			return 0;
		return headerLength+bodyLenhth+2;
	}

	// Method to print a request-summary
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

