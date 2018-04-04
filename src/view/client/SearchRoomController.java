package view.client;

import javafx.fxml.FXML;

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

}
