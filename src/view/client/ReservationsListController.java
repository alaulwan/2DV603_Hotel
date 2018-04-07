package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.shared.Hotel;
import model.shared.Reservation;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;

@SuppressWarnings("rawtypes")
public class ReservationsListController implements Controller{
	@FXML
	private TableView reservationsList;
	@FXML
	private TableColumn idCol;
	@FXML
	private TableColumn roomCol;
	@FXML
	private TableColumn nameCol;
	@FXML
	private TableColumn guestNoCol;
	@FXML
	private TableColumn checkInCol;
	@FXML
	private TableColumn checkOutCol;
	@FXML
	private TableColumn totalDaysCol;
	
	private final String RESERVATION_LIST_LAYOUT = "res/view/ReservationsList.fxml";
	
	@FXML
	public void initialize() {
		Hotel hotel = new Hotel ();
		hotel.defaultHotel();
		ArrayList<Reservation> reservationArray = hotel.getReservationsList();
		
		ObservableList data = FXCollections.observableList(reservationArray);
		reservationsList.setItems(data);
		
		idCol.setCellValueFactory(new PropertyValueFactory("customerId"));
		roomCol.setCellValueFactory(new PropertyValueFactory("roomNumber"));
		nameCol.setCellValueFactory(new PropertyValueFactory("customerName"));
		guestNoCol.setCellValueFactory(new PropertyValueFactory("guestsNumber"));
		checkInCol.setCellValueFactory(new PropertyValueFactory("checkInDate"));
		checkOutCol.setCellValueFactory(new PropertyValueFactory("checkOutDate"));
		totalDaysCol.setCellValueFactory(new PropertyValueFactory("totalDays"));
		

		
	}
	
	
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(RESERVATION_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
