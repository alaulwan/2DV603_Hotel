package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.client.ServerAPI;
import model.shared.Room;
import model.shared.filters.roomsFilters.RoomsFilter;

public class AvailableRoomController implements Controller {
	@FXML
	private GridPane roomsGrid;
	@FXML
	private StackPane roomPin;
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
	
	private final String AVAILABLE_ROOM_LAYOUT = "res/view/AvailableRoom.fxml";
	private ArrayList <RoomsFilter> roomsFilterList;
	private ArrayList <Room> roomsList;
	
	@FXML
	public void initialize() {
		if (roomsFilterList == null)
			roomsList = ServerAPI.getAllRooms();
		else {
			roomsList = ServerAPI.getRoomsList(roomsFilterList);
		}
		for (int i=0; i< roomsList.size(); i++) {
			RoomNode rM = new RoomNode (roomsList.get(i)) ;
			System.out.println(roomsList.get(i).getRoomNum());
			roomsGrid.add(rM, i%4 , i/4);
		}
		
	}
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(AVAILABLE_ROOM_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
