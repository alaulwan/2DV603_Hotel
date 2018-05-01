package controller.client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.client.ServerAPI;
import model.shared.Reservation;
import model.shared.Bill.PayStatus;
import model.shared.filters.billsFilters.BillsFilter;
import model.shared.filters.billsFilters.PayStatusBillsFilter;
import model.shared.filters.customersFilters.CustomersFilter;
import model.shared.filters.customersFilters.ReservationLocationCustomersFilter;
import model.shared.filters.customersFilters.ReservationStatusCustumersFilter;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomIdReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Tab;

public class RootLayoutController extends Controller {
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab availableRoomsTab;
	@FXML
	private Tab reservationsListTab;
	@FXML
	private Tab customerListTab;
	@FXML
	private Tab billsListTab;
	@FXML
	private HBox buttonBox;
	@FXML
	private Button newReservationButton;
	@FXML
	public Button checkinButton;
	@FXML
	public Button checkoutButton;
	@FXML
	private Button exitButton;

	private final String ROOT_LAYOUT = "/view/client/RootLayout.fxml";
	private AvailableRoomController availableRoomController;
	private ReservationsListController reservationsListController;
	private CustomerListController customerListController;
	private BillsListController billsListController;
	private SearchRoomController searchRoomController;

	public RootLayoutController(ServerAPI API) {
		super.serverAPI = API;
		super.urlPath = getClass().getResource(ROOT_LAYOUT);
		availableRoomController = new AvailableRoomController(this);
		reservationsListController = new ReservationsListController(this);
		customerListController = new CustomerListController(this);
		billsListController = new BillsListController(this);
		searchRoomController = new SearchRoomController(this);
	}

	@FXML
	public void initialize() throws IOException {
		availableRoomsTab.setContent(availableRoomController.getParentPane());
		reservationsListTab.setContent(reservationsListController.getParentPane());
		customerListTab.setContent(customerListController.getParentPane());
		billsListTab.setContent(billsListController.getParentPane());

	}

	@FXML
	public void newReservation() {
		try {
			Scene mainScene = new Scene(searchRoomController.getParentPane());
			Stage stage = new Stage();
			stage.setMinWidth(720);
			stage.setMinHeight(620);
			stage.setMaxWidth(720);
			stage.setMaxHeight(620);
			stage.setScene(mainScene);
			stage.setTitle("Search for a room...");
			stage.getIcons().add(new Image("file:res/icons/search_room.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		update();
	}

	@FXML
	public void checkIn() {
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Check-in",
				"Are you sure you want to check in this room", "", "warning.png", "check-in.png");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {

			boolean checkInSuccess = false;
			ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter>();
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
			StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, false, false, false);
			RoomIdReservationsFilter roomIdReservationsFilter = new RoomIdReservationsFilter(
					availableRoomController.selectedRoomNode.room.getRoomId());
			reservationsFilterList.add(locationReservationsFilter);
			reservationsFilterList.add(statusReservationsFilter);
			reservationsFilterList.add(roomIdReservationsFilter);
			ArrayList<Reservation> reservationArray = serverAPI.getReservationsList(reservationsFilterList);
			for (Reservation rs : reservationArray) {
				if (rs.checkInDateAsLocalDate().equals(LocalDate.now())) {
					checkInSuccess = serverAPI.checkIn(rs.getReservationId());
					break;
				}
			}
			if (checkInSuccess) {
				update();
				alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Successed", "", "ok.png", "check-in.png");

			} else {
				alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Failed", "", "error.png", "check-in.png");
			}
		}
	}

	@FXML
	public void checkOut() {
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Check-out",
				"Are you sure you want to check out this room", "", "warning.png", "check-out.png");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter>();
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
			StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(false, true, false, false);
			RoomIdReservationsFilter roomIdReservationsFilter = new RoomIdReservationsFilter(
					availableRoomController.selectedRoomNode.room.getRoomId());
			reservationsFilterList.add(locationReservationsFilter);
			reservationsFilterList.add(statusReservationsFilter);
			reservationsFilterList.add(roomIdReservationsFilter);
			ArrayList<Reservation> reservationArray = serverAPI.getReservationsList(reservationsFilterList);
			for (Reservation rs : reservationArray) {
				reservationsListController.selectedReservation = rs;
				reservationsListController.chekOutReservation(rs.getReservationId());
			}
		}
	}

	@FXML
	public void exit() {
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Exit", "Confirm Exit", "Are you sure you want to exit?", "warning.png", "exit.png");
		if (ButtonType.OK.equals(result.get())) {
			System.exit(0);
		}
	}

	@FXML
	private void imprtRoomsList() {
		if (availableRoomController.roomsList != null && availableRoomsTab.isSelected()) {
			availableRoomController.initialize();
		}
	}

	@FXML
	private void imprtReservationsList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (reservationsListTab.isSelected()) {
			ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter>();
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
			StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
			reservationsFilterList.add(locationReservationsFilter);
			reservationsFilterList.add(statusReservationsFilter);
			reservationsListController.reservationArray = serverAPI.getReservationsList(reservationsFilterList);
			reservationsListController.roomsList = this.availableRoomController.roomsList;
			reservationsListController.initialize();
		}
	}

	@FXML
	private void imprtCustomersList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (customerListTab.isSelected()) {
			ArrayList<CustomersFilter> customersFilterList = new ArrayList<CustomersFilter>();
			ReservationLocationCustomersFilter reservationLocationCustomersFilter = new ReservationLocationCustomersFilter(
					serverAPI.location);
			ReservationStatusCustumersFilter reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(
					true, true, false, false);
			customersFilterList.add(reservationStatusCustumersFilter);
			customersFilterList.add(reservationLocationCustomersFilter);

			customerListController.customersArray = serverAPI.getCustomersList(customersFilterList);
			customerListController.initialize();
		}
	}

	@FXML
	private void importBillsList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (billsListTab.isSelected()) {
			ArrayList<BillsFilter> billsFilterList = new ArrayList<BillsFilter>();
			PayStatusBillsFilter payStatusBillsFilter = new PayStatusBillsFilter(PayStatus.UNPAID);
			billsFilterList.add(payStatusBillsFilter);
			billsListController.billsArray = serverAPI.getBillsList(billsFilterList);
			billsListController.initialize();
		}
	}

	public void update() {
		imprtRoomsList();
		imprtReservationsList();
		imprtCustomersList();
		importBillsList();
	}
}
