package view.client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.CheckBox;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;

import javafx.scene.layout.GridPane;

import javafx.scene.control.ChoiceBox;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.client.ServerAPI;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.filters.roomsFilters.ChekInOutDateRoomsFilter;
import model.shared.filters.roomsFilters.LocationRoomsFilter;
import model.shared.filters.roomsFilters.MinCabacityRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;
import model.shared.filters.roomsFilters.SuiteRoomsFilter;
import model.shared.filters.roomsFilters.AirViewBalSmoRoomsFilter;

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
	private ChoiceBox <RoomLocation>locationBox;
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
	private Spinner <Integer> guestNumberBox;
	@FXML
	private CheckBox suiteBox;
	@FXML
	private Spinner <Integer> suiteRoomsNumberBox;
	@FXML
	private Button searchButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button cancelButton;
	
	private final String SEARCH_ROOM_LAYOUT = "res/view/SearchRoom.fxml";
	private ArrayList <Room> roomsList;
	private RoomNode selectedRoomNode;
	
	@FXML
	public void initialize() {
		guestNumberBox.setValueFactory(new IntegerSpinnerValueFactory(1, 12, 1));
		suiteRoomsNumberBox.setValueFactory(new IntegerSpinnerValueFactory(2, 3, 2));
		suiteRoomsNumberBox.setDisable(true);
		locationBox.setItems(FXCollections.observableList(Arrays.asList(RoomLocation.values())));
		locationBox.setValue(ServerAPI.location);
		arrivalDateBox.setValue(LocalDate.now());
		departureDateBox.setValue(LocalDate.now().plusDays(1));
		
	}
	
	
	@FXML
	public void suiteCheckBox() {
		suiteRoomsNumberBox.setDisable(!suiteBox.isSelected());
	}
	
	@FXML
	public void nextToEnterCustomerInformation() {
		try {
			AddCustomerController c = new AddCustomerController();

			Scene mainScene = new Scene(c.getParentPane());

			Stage stage = new Stage();
			stage.setScene(mainScene);
			stage.setTitle("Search for a room...");
			stage.show();
			((Stage) nextButton.getScene().getWindow()).close();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void searchRooms() {
		ArrayList <RoomsFilter> roomsFiltersList = new ArrayList <RoomsFilter>();
		ChekInOutDateRoomsFilter chekInOutDateRoomsFilter = new ChekInOutDateRoomsFilter(this.arrivalDateBox.getValue(), this.departureDateBox.getValue());
		roomsFiltersList.add(chekInOutDateRoomsFilter);
		LocationRoomsFilter locationRoomsFilter = new LocationRoomsFilter(locationBox.getValue());
		roomsFiltersList.add(locationRoomsFilter);
		MinCabacityRoomsFilter minCabacityRoomsFilter = new MinCabacityRoomsFilter(guestNumberBox.getValue());
		roomsFiltersList.add(minCabacityRoomsFilter);
		AirViewBalSmoRoomsFilter airViewBalSmoRoomsFilter = new AirViewBalSmoRoomsFilter(airConBox.isSelected(), viewBox.isSelected(), balconyBox.isSelected(), smokingBox.isSelected());
		roomsFiltersList.add(airViewBalSmoRoomsFilter);
		if (suiteBox.isSelected()) {
			SuiteRoomsFilter suiteRoomsFilter = new SuiteRoomsFilter(suiteRoomsNumberBox.getValue());
			roomsFiltersList.add(suiteRoomsFilter);
		}
		this.roomsList = ServerAPI.getRoomsList(roomsFiltersList);
		viewRooms();
	}
	
	private void viewRooms() {
		roomGrid.getChildren().removeAll(roomGrid.getChildren());
		for (int i=0; i< roomsList.size(); i++) {
			RoomNode roomNode = new RoomNode (roomsList.get(i)) ;
			roomGrid.add(roomNode, i%2 , i/2);
			setRoomNodemouseAction(roomNode);
		}
	}


	@FXML
	public void cancel() {
		((Stage) cancelButton.getScene().getWindow()).close();
	}
	
	private void setRoomNodemouseAction(RoomNode roomNode) {
		Paint paint = roomNode.rectangle.getStroke();
		roomNode.rectangle.setStrokeWidth(roomNode.rectangle.getHeight()/15);
		
		roomNode.setOnMouseEntered((MouseEvent t) -> {
			if (!roomNode.equals(selectedRoomNode))
				roomNode.rectangle.setStroke(Paint.valueOf("LIGHTGRAY"));
		});
		roomNode.setOnMouseExited((MouseEvent t) -> {
			roomNode.rectangle.setStroke(paint);
			if (selectedRoomNode != null)
				selectedRoomNode.rectangle.setStroke(Paint.valueOf("GRAY"));
		});
		
		roomNode.setOnMousePressed((MouseEvent t) -> {
			if (selectedRoomNode != null)
				selectedRoomNode.rectangle.setStroke(paint);
			selectedRoomNode = roomNode;
			selectedRoomNode.rectangle.setStroke(Paint.valueOf("GRAY"));
			
		});
		
	}
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(SEARCH_ROOM_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
