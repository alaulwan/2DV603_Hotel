package view.client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tab;

public class RootLayoutController {
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
	private Button checkinButton;
	@FXML
	private Button checkoutButton;
	@FXML
	private Button exitButton;

	private final String ROOT_LAYOUT = "res/view/RootLayout.fxml";
	private AvailableRoomController availableRoomController = new AvailableRoomController();
	private ReservationsListController reservationsListController = new ReservationsListController();
	private CustomerListController customerListController = new CustomerListController();
	private BillsListController billsListController = new BillsListController();

	private SearchRoomController searchRoomController = new SearchRoomController();
	

	@FXML
	public void initialize() throws IOException {
		availableRoomController.checkinButton = this.checkinButton;
		availableRoomController.checkoutButton = this.checkoutButton;
		reservationsListController.checkinButton = this.checkinButton;
		reservationsListController.checkoutButton = this.checkoutButton;
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
			stage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		imprtRoomsList();
		imprtReservationsList();
		imprtCustomersList();
		importBillsList();
	}
	
	@FXML
	public void checkIn() {
		boolean checkInSuccess = false;
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (ServerAPI.location);
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, false, false, false);
		RoomIdReservationsFilter roomIdReservationsFilter = new RoomIdReservationsFilter (availableRoomController.selectedRoomNode.room.getRoomId());
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		reservationsFilterList.add(roomIdReservationsFilter);
		ArrayList<Reservation> reservationArray= ServerAPI.getReservationsList(reservationsFilterList);
		for (Reservation rs : reservationArray) {
			if (rs.checkInDateAsLocalDate().equals(LocalDate.now())) {
				checkInSuccess = ServerAPI.CheckIn(rs.getReservationId());
				break;
			}
		}
		if (checkInSuccess) {
			imprtRoomsList();
			imprtReservationsList();
			imprtCustomersList();
			importBillsList();
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Successed", "");
			
		}
		else {
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Failed", "");
		}

	}
	
	@FXML
	public void checkOut() {
		
	}
	
	@FXML
	public void exit() {
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
		closeConfirmation.setHeaderText("Confirm Exit");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		
		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if (ButtonType.OK.equals(closeResponse.get())) {
			System.exit(0);

		} 
	}
	
	
	@FXML
	private void imprtRoomsList() {
		if (availableRoomController.roomsList!=null && availableRoomsTab.isSelected()){
			availableRoomController.initialize();
		}
	}
	
	@FXML
	private void imprtReservationsList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (reservationsListTab.isSelected()){
			ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (ServerAPI.location);
			StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
			reservationsFilterList.add(locationReservationsFilter);
			reservationsFilterList.add(statusReservationsFilter);
			reservationsListController.reservationArray = ServerAPI.getReservationsList(reservationsFilterList);
			reservationsListController.roomsList = this.availableRoomController.roomsList;
			reservationsListController.initialize();
		}
	}
	
	@FXML
	private void imprtCustomersList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (customerListTab.isSelected()){
			ArrayList<CustomersFilter> customersFilterList = new ArrayList<CustomersFilter> ();
			ReservationLocationCustomersFilter reservationLocationCustomersFilter = new ReservationLocationCustomersFilter (ServerAPI.location);
			ReservationStatusCustumersFilter reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(true, true, false, false);
			customersFilterList.add(reservationStatusCustumersFilter);
			customersFilterList.add(reservationLocationCustomersFilter);
			
			customerListController.customersArray = ServerAPI.getCustomersList(customersFilterList);
			customerListController.initialize();
		}
	}
	
	@FXML
	private void importBillsList() {
		this.checkinButton.setDisable(true);
		this.checkoutButton.setDisable(true);
		if (billsListTab.isSelected()){
			ArrayList<BillsFilter> billsFilterList = new ArrayList<BillsFilter> ();
			PayStatusBillsFilter payStatusBillsFilter = new PayStatusBillsFilter (PayStatus.UNPAID);
			billsFilterList.add(payStatusBillsFilter);
			billsListController.billsArray = ServerAPI.getBillsList(billsFilterList);
			billsListController.initialize();
		}
	}

	/**
     * Warning popUp 
     * @param type of alert
     * @param Title 
     * @param headText
     * @param contentText
     */
    public void alertWindow(AlertType type, String Title, String headText, String contentText) {
    	Alert alert = new Alert(type);
		alert.setTitle(Title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		alert.showAndWait();
    }
    
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(ROOT_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}
}
