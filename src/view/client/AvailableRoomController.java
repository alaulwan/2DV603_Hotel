package view.client;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;

public class AvailableRoomController {
	@FXML
	private GridPane roomsGrid;
	@FXML
	private Pane roomPin;
	@FXML
	private Label roomNumber;
	@FXML
	private Label status;
	@FXML
	private Label totalRoomCounter;
	@FXML
	private Label occupiedRoomCounter;
	@FXML
	private Label availableRoomCounter;
	@FXML
	private Label totalGuestCounter;
	@FXML
	private Label maleGuestCounter;
	@FXML
	private Label femaleGuestCounter;
	@FXML
	private Label checkInCounter;
	@FXML
	private Label checkOutCounter;
	@FXML
	private Label singleBedCounter;
	@FXML
	private Label doubleBedCounter;

}
