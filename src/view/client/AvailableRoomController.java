package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.client.ServerAPI;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
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
	public ArrayList <Room> roomsList;
	public RoomNode selectedRoomNode;
	public Button checkinButton;
	public Button checkoutButton;
	
	@FXML
	public void initialize() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (roomsFilterList == null)
			roomsList = ServerAPI.getAllRooms();
		else {
			roomsList = ServerAPI.getRoomsList(roomsFilterList);
		}
		if (roomsGrid!=null && roomsGrid.getChildren()!= null) {
			roomsGrid.getChildren().removeAll(roomsGrid.getChildren());
		}
		for (int i=0; i< roomsList.size(); i++) {
			RoomNode roomNode = new RoomNode (roomsList.get(i)) ;
			roomsGrid.add(roomNode, i%4 , i/4);
			setRoomNodemouseAction(roomNode);
		}
	}
	
	private ArrayList<Reservation> importReservationsListByRoomNum(int roomNum) {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (ServerAPI.location);
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		RoomNumReservationsFilter roomNumReservationsFilter = new RoomNumReservationsFilter (roomNum);
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		reservationsFilterList.add(roomNumReservationsFilter);
		
		return ServerAPI.getReservationsList(reservationsFilterList);
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
			updateCheckInOutBtnStatus ();
			if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2) {
				ReservationsListController reservationsListController = new ReservationsListController();
				try {
					reservationsListController.reservationArray = importReservationsListByRoomNum(roomNode.room.getRoomNum());
					
					Scene mainScene = new Scene(reservationsListController.getParentPane());
					reservationsListController.searchRoomNumber.setText(roomNode.room.getRoomNum()+"");
					reservationsListController.searchRoomNumber.setVisible(false);
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
	
	private void updateCheckInOutBtnStatus () {
		Room room = selectedRoomNode.room;
		boolean checkInStatus = room.getRoomStatus().equals(RoomStatus.CHEKIN_TODAY);
		boolean checkOutStatus = room.getRoomStatus().equals(RoomStatus.CHECKOUT_TODAY)
				|| room.getRoomStatus().equals(RoomStatus.CHECK_OUT_IN)
				|| room.getRoomStatus().equals(RoomStatus.OCCUPIED);
		this.checkinButton.setDisable(!checkInStatus);
		this.checkoutButton.setDisable(!checkOutStatus);
	}
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(AVAILABLE_ROOM_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
