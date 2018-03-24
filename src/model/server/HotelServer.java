package model.server;

import model.shared.Hotel;


public class HotelServer {
	public static HotelDAO DAO;
	private static Hotel hotel = new Hotel();
	
	public static void main(String[] args) {
		DAO = new HotelDAO();
		DAO.xmlSave (hotel);
	}

}
