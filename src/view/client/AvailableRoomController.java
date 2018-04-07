package view.client;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
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
	private RoomNode selectedRoomNode;
	
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
			
			Paint paint = rM.rectangle.getStroke();
			rM.rectangle.setStrokeWidth(rM.rectangle.getHeight()/15);
			
			rM.setOnMouseEntered((MouseEvent t) -> {
				if (!rM.equals(selectedRoomNode))
					rM.rectangle.setStroke(Paint.valueOf("LIGHTGRAY"));
			});
			rM.setOnMouseExited((MouseEvent t) -> {
				rM.rectangle.setStroke(paint);
				if (selectedRoomNode != null)
					selectedRoomNode.rectangle.setStroke(Paint.valueOf("GRAY"));
			});
			
			rM.setOnMouseClicked((MouseEvent t) -> {
				if (selectedRoomNode != null)
					selectedRoomNode.rectangle.setStroke(paint);
				selectedRoomNode = rM;
				selectedRoomNode.rectangle.setStroke(Paint.valueOf("GRAY"));
				if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2) {
					ReservationsListController reservationsListController = new ReservationsListController();
					try {
						Scene mainScene = new Scene(reservationsListController.getParentPane());
						Stage stage = new Stage();
						stage.setScene(mainScene);
						stage.setTitle("Reservations List");
						stage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
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
