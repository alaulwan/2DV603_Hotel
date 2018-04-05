package view.client;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.CheckBox;

import javafx.scene.control.Spinner;

import javafx.scene.control.DatePicker;

import javafx.scene.layout.GridPane;

import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;

public class SearchRoomController {
	@FXML
	private AnchorPane addReservationPane;
	@FXML
	private GridPane roomGrid;
	@FXML
	private Pane roomPin;
	@FXML
	private Label roomNumber;
	@FXML
	private Label status;
	@FXML
	private ChoiceBox locationBox;
	@FXML
	private DatePicker arrivalDateBox;
	@FXML
	private DatePicker departureDateBox;
	@FXML
	private CheckBox viewBox;
	@FXML
	private CheckBox airConBox;
	@FXML
	private CheckBox balconyBox;
	@FXML
	private CheckBox smokingBox;
	@FXML
	private Spinner guestNumberBox;
	@FXML
	private CheckBox suiteBox;
	@FXML
	private Spinner suiteRoomsNumberBox;
	@FXML
	private Button searchButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button cancelButton;
	
	private final String SEARCH_ROOM_LAYOUT = "res/view/SearchRoom.fxml";
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(SEARCH_ROOM_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
