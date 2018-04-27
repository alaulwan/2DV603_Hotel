package view.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Room.RoomStatus;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;

public class AvailableRoomController extends Controller {
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
	private Label checkInCounter;
	@FXML
	private Label checkOutCounter;
	
	private final String AVAILABLE_ROOM_LAYOUT = "res/view/AvailableRoom.fxml";
	private ArrayList <RoomsFilter> roomsFilterList;
	public ArrayList <Room> roomsList;
	public RoomNode selectedRoomNode;
	
	public AvailableRoomController(RootLayoutController rootLayoutController) {
		super.fxmlPath = AVAILABLE_ROOM_LAYOUT;
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
	}
	
	@FXML
	public void initialize() {
		super.rootLayoutController.checkinButton.setDisable(true);
		super.rootLayoutController.checkoutButton.setDisable(true);
		if (roomsFilterList == null)
			roomsList = serverAPI.getAllRooms();
		else {
			roomsList = serverAPI.getRoomsList(roomsFilterList);
		}
		if (roomsGrid!=null && roomsGrid.getChildren()!= null) {
			roomsGrid.getChildren().removeAll(roomsGrid.getChildren());
		}
		for (int i=0; i< roomsList.size(); i++) {
			RoomNode roomNode = new RoomNode (roomsList.get(i)) ;
			roomsGrid.add(roomNode, i%4 , i/4);
			setRoomNodemouseAction(roomNode);
		}
		
		fillCounters() ;
	}
	
	private void fillCounters() {
		totalRoomCounter.setText(Integer.toString(roomsList.size()));
		int occupiedRoomCount = 0 ;
		int availableRoomCount = 0 ;
		int checkInTodayCount = 0;
		int checkOutTodayCount = 0;

		for(Room room : roomsList ) {
			if(room.getRoomStatus() == RoomStatus.OCCUPIED)
				occupiedRoomCounter.setText(Integer.toString(++occupiedRoomCount));
			else if (room.getRoomStatus() == RoomStatus.AVAILABLE)
				availableRoomCounter.setText(Integer.toString(++availableRoomCount));
			else if (room.getRoomStatus() == RoomStatus.CHECKIN_TODAY)
				checkInCounter.setText(Integer.toString(++checkInTodayCount));
			else if (room.getRoomStatus() == RoomStatus.CHECKOUT_TODAY)
				checkOutCounter.setText(Integer.toString(++checkOutTodayCount));
			else if (room.getRoomStatus() == RoomStatus.CHECK_OUT_IN) {
				checkInCounter.setText(Integer.toString(++checkInTodayCount));
				checkOutCounter.setText(Integer.toString(++checkOutTodayCount));
			}

				
		}
	}
	
	private ArrayList<Reservation> importReservationsListByRoomNum(int roomNum) {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (serverAPI.location);
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		RoomNumReservationsFilter roomNumReservationsFilter = new RoomNumReservationsFilter (roomNum);
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		reservationsFilterList.add(roomNumReservationsFilter);
		
		return serverAPI.getReservationsList(reservationsFilterList);
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
				ReservationsListController reservationsListController = new ReservationsListController(super.rootLayoutController);
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
		boolean checkInStatus = room.getRoomStatus().equals(RoomStatus.CHECKIN_TODAY);
		boolean checkOutStatus = room.getRoomStatus().equals(RoomStatus.CHECKOUT_TODAY)
				|| room.getRoomStatus().equals(RoomStatus.CHECK_OUT_IN)
				|| room.getRoomStatus().equals(RoomStatus.OCCUPIED);
		super.rootLayoutController.checkinButton.setDisable(!checkInStatus);
		super.rootLayoutController.checkoutButton.setDisable(!checkOutStatus);
	}

}
