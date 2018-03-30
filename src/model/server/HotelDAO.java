package model.server;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.shared.Hotel;

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
			File file = new File("src/model/server/" + fileName);
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
			Hotel hotel = (Hotel) un.unmarshal(new File("src/model/server/" + fileName));
			return hotel;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
