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

	public void xmlSave(Hotel hotel) {
		xmlSave(hotel, "hotel.xml");
	}

	public void xmlSave(Hotel hotel, String fileName) {
		try {
			JAXBContext context = JAXBContext.newInstance(Hotel.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(dao, System.out);
			File file = new File("res/DataBase/" + fileName);
			// Write to File
			// File file = new File(url.toURI());
			m.marshal(hotel, file);
			System.out.println("Saved successfully to: " + file.getAbsolutePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public Hotel xmlLoad() {
		return xmlLoad("hotel.xml");
	}

	public Hotel xmlLoad(String fileName) {
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
