package model.server;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.shared.Hotel;

public class HotelDAO {

	public void xmlSave(Hotel hotel) {
		try {
			JAXBContext context = JAXBContext.newInstance(Hotel.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(dao, System.out);
			File file = new File("src/model/server/hotel.xml");
			// Write to File
			// File file = new File(url.toURI());
			m.marshal(hotel, file);
			System.out.println("Saved successfully to: " + file.getAbsolutePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
