package model.server.response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import model.server.HotelServer;

public class Response implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String textTSend="";
	public Object object;
	
	
	public Response() {
		
	}
	
	public void sendObject(ObjectOutputStream outputStream) {
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(OutputStream outputStream) {
		try {
			outputStream.write(textTSend.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateDataBase() {
		HotelServer.savingThread.saveRequestList.add(true);
		HotelServer.savingThread.interrupt();
	}
	

}
