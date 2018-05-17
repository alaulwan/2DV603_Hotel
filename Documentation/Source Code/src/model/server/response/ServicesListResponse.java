package model.server.response;

import java.util.ArrayList;
import model.server.HotelServer.SavingThread;
import model.shared.Hotel;
import model.shared.Service;

// This response will send all available services in the hotel to the client
public class ServicesListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Service> servicesList;

	public ServicesListResponse(Hotel hotel, SavingThread savingThread) {
		super (hotel, savingThread);
		servicesList = new ArrayList<Service>(hotel.getServicesList());
		super.object = servicesList;
	}
}
