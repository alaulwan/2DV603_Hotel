package model.server.response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import model.server.HotelServer.SavingThread;
import model.shared.Hotel;

// The respone that the server will perform it to serve an request
public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Hotel hotel;
	public SavingThread savingThread;

	// This object will be sent back to the client after performing the response
	public Object object;

	public Response(Hotel hotel, SavingThread savingThread) {
		this.hotel = hotel;
		this.savingThread = savingThread;
	}

	// method to send an object to the client
	public void sendObject(ObjectOutputStream outputStream) {
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to add request to the saving thread, 
	// and then interrupts the thread if it is sleeping
	public void updateDataBase() {
		savingThread.saveRequestList.add(true);
		savingThread.interrupt();
	}

}
