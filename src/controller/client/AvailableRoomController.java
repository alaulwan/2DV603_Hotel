package controller.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;

public class AvailableRoomController extends Controller {
	@FXML
	private GridPane roomsGrid;
	@FXML
	private StackPane roomPin;
	@FXML
	private Label totalRoomsCounter;
	@FXML
	private Label availableAllDayCounter;
	@FXML
	private Label availableafter12Counter;
	@FXML
	private Label occupiedRoomsCounter;
	@FXML
	private Label occupiedafter12Counter;
	@FXML
	private ImageView logo;


	private final String AVAILABLE_ROOM_LAYOUT = "/view/client/AvailableRoom.fxml";
	public ArrayList<Room> roomsList;
	public RoomNode selectedRoomNode;
	private String imageURL ;

	public AvailableRoomController(RootLayoutController rootLayoutController) {
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
		super.fxmlURL = getClass().getResource(AVAILABLE_ROOM_LAYOUT);
	}

	@FXML
	public void initialize() {
		super.rootLayoutController.checkinButton.setDisable(true);
		super.rootLayoutController.checkoutButton.setDisable(true);
		
		// Import all rooms and suits in the current location from the server
		roomsList = serverAPI.getAllRooms();
		
		// Change the icon in the first window according to the location
		if (serverAPI.location == RoomLocation.VAXJO)
			imageURL = getClass().getResource("/view/client/icons/vaxjologo.png").toString() ;
		else {
			imageURL = getClass().getResource("/view/client/icons/kalmarlogo.png").toString() ;
		}
			
		logo.setImage(new Image(imageURL));
		logo.setFitWidth(260);
		logo.setFitHeight(260);
		if (roomsGrid != null && roomsGrid.getChildren() != null) {
			roomsGrid.getChildren().removeAll(roomsGrid.getChildren());
		}
		for (int i = 0; i < roomsList.size(); i++) {
			RoomNode roomNode = new RoomNode(roomsList.get(i));
			roomsGrid.add(roomNode, i % 4, i / 4);
			setRoomNodemouseAction(roomNode);
		}

		fillCounters();
	}

	// Make a statistic to the rooms-status and view the results in the main window
	private void fillCounters() {
		totalRoomsCounter.setText(Integer.toString(roomsList.size()));
		int occupiedRoomCount = 0;
		int occupiedafter12Count = 0;
		int availablealldayRoomCount = 0;
		int availableafter12Count = 0;
		
		for (Room room : roomsList) {
			if (room.getRoomStatus() == RoomStatus.OCCUPIED)
				occupiedRoomsCounter.setText(Integer.toString(++occupiedRoomCount));
			else if (room.getRoomStatus() == RoomStatus.AVAILABLE)
				availableAllDayCounter.setText(Integer.toString(++availablealldayRoomCount));
			else if (room.getRoomStatus() == RoomStatus.CHECKIN_TODAY)
				occupiedafter12Counter.setText(Integer.toString(++occupiedafter12Count));
			else if (room.getRoomStatus() == RoomStatus.CHECKOUT_TODAY)
				availableafter12Counter.setText(Integer.toString(++availableafter12Count));
			else if (room.getRoomStatus() == RoomStatus.CHECK_OUT_IN) {
				occupiedRoomsCounter.setText(Integer.toString(++occupiedRoomCount));
			}

		}
	}

	// Import reservations from the server depending on the room-ID
	private ArrayList<Reservation> importReservationsListByRoomNum(int roomNum) {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter>();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		RoomNumReservationsFilter roomNumReservationsFilter = new RoomNumReservationsFilter(roomNum);
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		reservationsFilterList.add(roomNumReservationsFilter);

		return serverAPI.getReservationsList(reservationsFilterList);
	}

	private void setRoomNodemouseAction(RoomNode roomNode) {
		Paint paint = roomNode.rectangle.getStroke();
		roomNode.rectangle.setStrokeWidth(roomNode.rectangle.getHeight() / 15);

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
			updateCheckInOutBtnStatus();
			if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2) {
				ReservationsListController reservationsListController = new ReservationsListController(
						super.rootLayoutController);
				try {
					reservationsListController.reservationArray = importReservationsListByRoomNum(
							roomNode.room.getRoomNum());

					Scene mainScene = new Scene(reservationsListController.getParentPane());
					reservationsListController.searchRoomNumber.setText(roomNode.room.getRoomNum() + "");
					reservationsListController.searchRoomNumber.setVisible(false);
					Stage stage = new Stage();
					stage.setScene(mainScene);
					stage.setTitle("Reservations List");
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	// Change the status of checkIn and checkOut buttons according to the selected room
	private void updateCheckInOutBtnStatus() {
		Room room = selectedRoomNode.room;
		boolean checkInStatus = room.getRoomStatus().equals(RoomStatus.CHECKIN_TODAY);
		boolean checkOutStatus = room.getRoomStatus().equals(RoomStatus.CHECKOUT_TODAY)
				|| room.getRoomStatus().equals(RoomStatus.CHECK_OUT_IN)
				|| room.getRoomStatus().equals(RoomStatus.OCCUPIED);
		super.rootLayoutController.checkinButton.setDisable(!checkInStatus);
		super.rootLayoutController.checkoutButton.setDisable(!checkOutStatus);
	}

}
