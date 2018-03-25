package model.server;

import model.shared.Hotel;
import com.google.gson.*;


public class HotelServer {
	public static HotelDAO DAO;
	private static Hotel hotel = new Hotel();
	
	public static void main(String[] args) {
		DAO = new HotelDAO();
		DAO.xmlSave (hotel);
		
		Gson gson = new GsonBuilder().create();
		String j = gson.toJson(hotel);
		
		Hotel hotel2 = DAO.xmlSave ();
		String j2 = gson.toJson(hotel2);
		
		System.out.println(j2.equals(j));
		
	}
	
}
