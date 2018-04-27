package model.server.response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import model.server.HotelServer.SavingThread;
import model.shared.Hotel;

public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Hotel hotel;
	public SavingThread savingThread;
	public Object object;

	public Response(Hotel hotel, SavingThread savingThread) {
		this.hotel = hotel;
		this.savingThread = savingThread;
	}

	public void sendObject(ObjectOutputStream outputStream) {
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateDataBase() {
		savingThread.saveRequestList.add(true);
		savingThread.interrupt();
	}

}
