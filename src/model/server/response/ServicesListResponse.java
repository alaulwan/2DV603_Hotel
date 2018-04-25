package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer;
import model.shared.Service;

public class ServicesListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Service> servicesList;

	public ServicesListResponse() {
		servicesList = new ArrayList<Service>(HotelServer.hotel.getServicesList());
		super.object = servicesList;
	}
}
