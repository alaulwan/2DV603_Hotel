package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.shared.Reservation;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;

public class ReservationsListController implements Controller{
	@FXML
	private TableView<Reservation> reservationsList;
	@FXML
	private TableColumn<Reservation, Integer> idCol;
	@FXML
	private TableColumn<Reservation, Integer> roomCol;
	@FXML
	private TableColumn<Reservation, String> nameCol;
	@FXML
	private TableColumn<Reservation, Integer> guestNoCol;
	@FXML
	private TableColumn<Reservation, String> checkInCol;
	@FXML
	private TableColumn<Reservation, String> checkOutCol;
	@FXML
	private TableColumn<Reservation, Integer> totalDaysCol;
	
	private final String RESERVATION_LIST_LAYOUT = "res/view/ReservationsList.fxml";
	public ArrayList<Reservation> reservationArray;
	
	@FXML
	public void initialize() {		
		if (reservationArray!=null) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationArray);
			reservationsList.setItems(data);
			
			idCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("customerId"));
			roomCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("roomNumber"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
			guestNoCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("guestsNumber"));
			checkInCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkInDate"));
			checkOutCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkOutDate"));
			totalDaysCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("totalDays"));
		}
	}
	
	
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(RESERVATION_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
