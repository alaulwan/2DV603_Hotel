package view.client;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;

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
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(RESERVATION_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
