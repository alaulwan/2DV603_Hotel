package model.server;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.shared.Customer;
import model.shared.Hotel;
import model.shared.Reservation;
import model.shared.Room;

public class HotelDAO {

	// Save all information in the hotel to XML file
	public void xmlSave(Hotel hotel) {
		String fileName = "hotel.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Hotel.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File("res/DataBase/" + fileName);
			m.marshal(hotel, file);
			// System.out.println("Saved successfully to: " + file.getAbsolutePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	// Load all information about the hotel from XML file
	public Hotel xmlLoad() {
		String fileName = "hotel.xml";
		try {
			JAXBContext context = JAXBContext.newInstance(Hotel.class);
			Unmarshaller un = context.createUnmarshaller();
			Hotel hotel = (Hotel) un.unmarshal(new File("res/DataBase/" + fileName));
			setCounter(hotel);
			return hotel;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	// This method will be executed after loading the hotel from the database
	// Set the counters that are responsible to generate the IDs of a new customer, reservation, room
	private void setCounter(Hotel hotel) {
		for (Customer customer : hotel.getCustomersList()) {
			if (customer.getCustomerId() > Customer.getCount())
				Customer.setCount(customer.getCustomerId());
		}
		for (Reservation rs : hotel.getReservationsList()) {
			if (rs.getReservationId() > Reservation.getCount())
				Reservation.setCount(rs.getReservationId());
		}

		for (Room room : hotel.getRoomsAndSuitesList()) {
			if (room.getRoomId() > Room.getCount())
				Room.setCount(room.getRoomId());
		}
	}

}
