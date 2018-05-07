package controller.client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Reservation.ReservationStatus;
import model.shared.Room.RoomLocation;
import model.shared.filters.roomsFilters.ChekInOutDateRoomsFilter;
import model.shared.filters.roomsFilters.LocationRoomsFilter;
import model.shared.filters.roomsFilters.MinCabacityRoomsFilter;
import model.shared.filters.roomsFilters.RoomsFilter;
import model.shared.filters.roomsFilters.SuiteRoomsFilter;
import model.shared.filters.roomsFilters.AirViewBalSmoRoomsFilter;

public class SearchRoomController extends Controller {
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
	private ChoiceBox<RoomLocation> locationBox;
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
	private Spinner<Integer> guestNumberBox;
	@FXML
	private CheckBox suiteBox;
	@FXML
	private Spinner<Integer> suiteRoomsNumberBox;
	@FXML
	private Button searchButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button cancelButton;

	private final String SEARCH_ROOM_LAYOUT = "/view/client/SearchRoom.fxml";
	private ArrayList<Room> roomsList;
	private RoomNode selectedRoomNode;
	public Reservation selectedReservation;
	public Reservation tempReservation;

	public SearchRoomController(RootLayoutController rootLayoutController) {
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
		super.fxmlURL = getClass().getResource(SEARCH_ROOM_LAYOUT);
	}

	@FXML
	public void initialize() {
		guestNumberBox.setValueFactory(new IntegerSpinnerValueFactory(1, 12, 1));
		suiteRoomsNumberBox.setValueFactory(new IntegerSpinnerValueFactory(2, 3, 2));
		suiteRoomsNumberBox.setDisable(true);
		nextButton.setDisable(true);
		locationBox.setItems(FXCollections.observableList(Arrays.asList(RoomLocation.values())));
		locationBox.setValue(serverAPI.location);
		arrivalDateBox.setValue(LocalDate.now());
		departureDateBox.setValue(LocalDate.now().plusDays(1));
		// If selectedReservation is null,
		// then this controller to create new reservation
		// Else this controller to update an existing reservation
		if (selectedReservation != null) {
			nextButton.setText("Save");
		}

		setBeginDateBounds();
		setEndDateBounds(departureDateBox, arrivalDateBox);
	}

	@FXML
	public void suiteCheckBox() {
		suiteRoomsNumberBox.setDisable(!suiteBox.isSelected());
	}

	@FXML
	public void arrivalDateBoxClicked() {
		departureDateBox.setValue(arrivalDateBox.getValue().plusDays(1));
		setEndDateBounds(departureDateBox, arrivalDateBox);
		disableNextButton();
	}

	@FXML
	public void nextToEnterCustomerInformation() {
		Reservation newReservation = new Reservation(ReservationStatus.PENDING, 0, "",
				selectedRoomNode.room.getRoomId(), selectedRoomNode.room.getRoomNum(),
				selectedRoomNode.room.getRoomLocation(), arrivalDateBox.getValue(), departureDateBox.getValue(),
				selectedRoomNode.room.getRate(), 0, guestNumberBox.getValue(), "");
		if (selectedReservation == null) {
			try {
				AddCustomerController addCustomer = new AddCustomerController(serverAPI);
				addCustomer.reservation = newReservation;
				Scene mainScene = new Scene(addCustomer.getParentPane());
				Stage stage = new Stage();
				stage.setMinWidth(650);
				stage.setMinHeight(680);
				stage.setMaxWidth(650);
				stage.setMaxHeight(680);
				stage.setScene(mainScene);
				stage.setTitle("Add Customer Info.");
				stage.getIcons().add(new Image("file:res/icons/customer.png"));
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			newReservation.setReservationId(selectedReservation.getReservationId());
			newReservation.setCustomerId(selectedReservation.getCustomerId());
			newReservation.setCustomerName(selectedReservation.getCustomerName());
			serverAPI.post(newReservation, selectedReservation.getReservationId());
		}
		((Stage) nextButton.getScene().getWindow()).close();

	}

	private void setBeginDateBounds() {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {

					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						boolean cond = (item.isBefore(LocalDate.now()));
						if (cond) {
							setDisable(true);
							setStyle("-fx-background-color: #d3d3d3;");
						} else {
							setDisable(false);
							setStyle("-fx-background-color: #CCFFFF;");
							setStyle("-fx-font-fill: black;");
						}
					}
				};
			}
		};
		arrivalDateBox.setDayCellFactory(dayCellFactory);
	}

	private void setEndDateBounds(DatePicker end_date, DatePicker begin_date) {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {

					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						boolean cond = (!item.isAfter(begin_date.getValue()));
						if (cond) {
							setDisable(true);
							setStyle("-fx-background-color: #d3d3d3;");
						} else {
							setDisable(false);
							setStyle("-fx-background-color: #CCFFFF;");
							setStyle("-fx-font-fill: black;");
						}
					}
				};
			}
		};
		end_date.setDayCellFactory(dayCellFactory);
	}

	@FXML
	public void searchRooms() {

		ArrayList<RoomsFilter> roomsFiltersList = new ArrayList<RoomsFilter>();
		ChekInOutDateRoomsFilter chekInOutDateRoomsFilter = new ChekInOutDateRoomsFilter(this.arrivalDateBox.getValue(),
				this.departureDateBox.getValue());
		roomsFiltersList.add(chekInOutDateRoomsFilter);
		LocationRoomsFilter locationRoomsFilter = new LocationRoomsFilter(locationBox.getValue());
		roomsFiltersList.add(locationRoomsFilter);
		MinCabacityRoomsFilter minCabacityRoomsFilter = new MinCabacityRoomsFilter(guestNumberBox.getValue());
		roomsFiltersList.add(minCabacityRoomsFilter);
		AirViewBalSmoRoomsFilter airViewBalSmoRoomsFilter = new AirViewBalSmoRoomsFilter(airConBox.isSelected(),
				viewBox.isSelected(), balconyBox.isSelected(), smokingBox.isSelected());
		roomsFiltersList.add(airViewBalSmoRoomsFilter);
		if (suiteBox.isSelected()) {
			SuiteRoomsFilter suiteRoomsFilter = new SuiteRoomsFilter(suiteRoomsNumberBox.getValue());
			roomsFiltersList.add(suiteRoomsFilter);
		}
		this.roomsList = serverAPI.getRoomsList(roomsFiltersList);
		viewRooms();
		nextButton.setDisable(true);

	}

	private void viewRooms() {
		roomGrid.getChildren().removeAll(roomGrid.getChildren());
		for (int i = 0; i < roomsList.size(); i++) {
			RoomNode roomNode = new RoomNode(roomsList.get(i));
			roomGrid.add(roomNode, i % 2, i / 2);
			setRoomNodemouseAction(roomNode);
		}

	}

	@FXML
	public void cancel() {
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	@FXML
	public void disableNextButton() {
		roomGrid.getChildren().removeAll(roomGrid.getChildren());
		nextButton.setDisable(true);
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
			nextButton.setDisable(false);

		});

	}

}
