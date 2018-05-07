package controller.client;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Reservation.ReservationStatus;
import model.shared.filters.reservationsFilters.CustomerNameReservationsFilter;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class ReservationsListController extends Controller {
	@FXML
	private TableView<Reservation> reservationsList;
	@FXML
	private TableColumn<Reservation, Integer> idCol;
	@FXML
	private TableColumn<Reservation, Integer> roomCol;
	@FXML
	private TableColumn<Reservation, String> nameCol;
	@FXML
	private TableColumn<Reservation, Integer> guestNoCol;
	@FXML
	private TableColumn<Reservation, String> checkInCol;
	@FXML
	private TableColumn<Reservation, String> checkOutCol;
	@FXML
	private TableColumn<Reservation, Integer> totalDaysCol;
	@FXML
	private TableColumn<Reservation, ReservationStatus> reservationStatusCol;
	@FXML
	public CheckBox viewAllBox;
	@FXML
	public HBox hbox;
	@FXML
	public TextField searchName;
	@FXML
	public TextField searchRoomNumber;

	private final String RESERVATION_LIST_LAYOUT = "/view/client/ReservationsList.fxml";
	public ArrayList<Room> roomsList;
	public ArrayList<Reservation> reservationArray;
	public Reservation selectedReservation;

	public ReservationsListController(RootLayoutController rootLayoutController) {
//		super.fxmlPath = RESERVATION_LIST_LAYOUT;
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
		super.fxmlURL = getClass().getResource(RESERVATION_LIST_LAYOUT);
	}

	@FXML
	public void initialize() {
		setData();
		setContextMenu();
	}

	@FXML
	public void viewHistoryChecked() {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter>();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
		StatusReservationsFilter statusReservationsFilter;
		if (viewAllBox.isSelected()) {
			statusReservationsFilter = new StatusReservationsFilter(true, true, true, true);
		} else {
			statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		}
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		this.reservationArray = serverAPI.getReservationsList(reservationsFilterList);
		apllyAllChosenFilters();
	}

	@FXML
	public void apllyAllChosenFilters() {
		ArrayList<Reservation> reservationsArray = new ArrayList<Reservation>(this.reservationArray);
		applyCustomerNameFilter(reservationsArray);
		applyRoomNumFilter(reservationsArray);
	}

	public void applyCustomerNameFilter(ArrayList<Reservation> reservationsArray) {
		String customerName = searchName.getText();
		CustomerNameReservationsFilter customerNameReservationsFilter = new CustomerNameReservationsFilter(
				customerName);
		customerNameReservationsFilter.applyReservationsFilter(reservationsArray);
		ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
		reservationsList.setItems(data);
	}

	public void applyRoomNumFilter(ArrayList<Reservation> reservationsArray) {
		if (searchRoomNumber.getText().isEmpty()) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
			reservationsList.setItems(data);
		} else {
			int roomNum = 0;
			try {
				roomNum = Integer.parseInt(searchRoomNumber.getText());
			} catch (Exception e) {
				return;
			}
			RoomNumReservationsFilter roomNumReservationsFilter = new RoomNumReservationsFilter(roomNum);
			roomNumReservationsFilter.applyReservationsFilter(reservationsArray);
			ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
			reservationsList.setItems(data);
		}

	}

	public void setData() {

		if (reservationArray != null) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationArray);
			reservationsList.setItems(data);

			idCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
			roomCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("roomNumber"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
			guestNoCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("guestsNumber"));
			checkInCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkInDate"));
			checkOutCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkOutDate"));
			totalDaysCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("totalDays"));
			reservationStatusCol
					.setCellValueFactory(new PropertyValueFactory<Reservation, ReservationStatus>("reservationStatus"));
		}
	}

	private void setContextMenu() {
		reservationsList.setRowFactory(new Callback<TableView<Reservation>, TableRow<Reservation>>() {
			@Override
			public TableRow<Reservation> call(TableView<Reservation> tableView) {
				final TableRow<Reservation> row = new TableRow<>();
				final ContextMenu menu = new ContextMenu();
				MenuItem mi1 = new MenuItem("Edit");
				mi1.setOnAction((ActionEvent event) -> {
					editReservation();

				});

				MenuItem mi2 = new MenuItem("Check-in");
				mi2.setOnAction((ActionEvent event) -> {
					chekInReservation(selectedReservation.getReservationId());
				});

				MenuItem mi3 = new MenuItem("Check-out");
				mi3.setOnAction((ActionEvent event) -> {
					chekOutReservation(selectedReservation.getReservationId());
				});

				MenuItem mi4 = new MenuItem("Add service");
				mi4.setOnAction((ActionEvent event) -> {
					addService();
				});

				MenuItem mi5 = new MenuItem("View bill");
				mi5.setOnAction((ActionEvent event) -> {
					viewBill();
				});

				MenuItem mi6 = new MenuItem("Delete");
				mi6.setOnAction((ActionEvent event) -> {
					deleteReservation();
				});

				MenuItem mi7 = new MenuItem("Cancel");
				mi7.setOnAction((ActionEvent event) -> {
					cancelReservation(selectedReservation.getReservationId());
				});

				menu.getItems().addAll(mi1, mi2, mi3, mi4, mi5, mi6, mi7);

				// only display context menu for non-null items:
				row.contextMenuProperty().bind(
						Bindings.when(Bindings.isNotNull(row.itemProperty())).then(menu).otherwise((ContextMenu) null));

				row.setOnMousePressed((MouseEvent t) -> {
					selectedReservation = reservationsList.getSelectionModel().getSelectedItem();
					if (selectedReservation != null) {
						boolean editStatus = selectedReservation.getReservationStatus()
								.equals(ReservationStatus.PENDING);
						boolean checkInStatus = selectedReservation.getReservationStatus()
								.equals(ReservationStatus.PENDING)
								&& selectedReservation.checkInDateAsLocalDate().equals(LocalDate.now());
						for (Reservation rs : reservationArray) {
							if (rs.getRoomId() == selectedReservation.getRoomId()
									&& rs.getReservationStatus().equals(ReservationStatus.CHECKED_IN)) {
								checkInStatus = false;
								break;
							}
						}
						boolean checkOutStatus = selectedReservation.getReservationStatus()
								.equals(ReservationStatus.CHECKED_IN);
						boolean deleteStatus = !selectedReservation.getReservationStatus()
								.equals(ReservationStatus.CHECKED_IN);
						boolean cancelStatus = selectedReservation.getReservationStatus()
								.equals(ReservationStatus.PENDING);
						mi1.setDisable(!editStatus);
						mi2.setDisable(!checkInStatus);
						mi3.setDisable(!checkOutStatus);
						mi6.setDisable(!deleteStatus);
						mi7.setDisable(!cancelStatus);
					}
				});
				return row;
			}
		});
	}

	public boolean chekInReservation(int reservationId) {
		boolean checkInSuccess = serverAPI.checkIn(reservationId);
		if (checkInSuccess) {
			update();
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Successed", "", "ok.png", "check-in.png");
		} else {
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Failed", "", "error.png", "check-in.png");
		}
		return checkInSuccess;
	}

	public boolean chekOutReservation(int reservationId) {
		boolean checkOutSuccess = serverAPI.checkOut(reservationId);
		if (checkOutSuccess) {
			// Update the reservation in the reservations-table in GUI
			checkOutAndupdateLocalReservation();
			Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "CheckOut", "CheckOut Successed",
					"Do you want to view the bill?", "warning.png", "check-out.png");
			if (result.isPresent() && result.get().equals(ButtonType.OK)) {
				viewBill();
				markBillAsPaid();
			}
		} else {
			alertWindow(AlertType.INFORMATION, "CheckOut", "CheckOut Failed", "", "error.png", "check-out.png");
		}
		return checkOutSuccess;
	}
	
	public boolean cancelReservation(int reservationId) {
		boolean cancelReservationSuccess = false;
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Cancel Reservation", "Are you sure you want to cancel this reservation",
				"", "warning.png", "reservations.png");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			cancelReservationSuccess = serverAPI.cancelReservation(reservationId);
			if (cancelReservationSuccess) {
				update();
				alertWindow(AlertType.INFORMATION, "Cancel Reservation", "Cancel Reservation Successed", "", "ok.png", "reservations.png");
				}
			else {
				alertWindow(AlertType.INFORMATION, "Cancel Reservation", "Cancel Reservation Failed", "", "error.png", "reservations.png");
			}
		} 
		return cancelReservationSuccess;
	}
	private boolean deleteReservation() {
		boolean deleteReservationSuccess = false;
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Delete Reservation", "Are you sure you want to delete this reservation",
				"", "warning.png", "reservations.png");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			deleteReservationSuccess = serverAPI.delete(selectedReservation);
			
			if (deleteReservationSuccess) {
				update();
				alertWindow(AlertType.INFORMATION, "Delete Reservation", "Delete Reservation Successed", "", "ok.png", "reservations.png");
			}
			else {
				alertWindow(AlertType.INFORMATION, "Delete Reservation", "Delete Reservation Failed", "", "ok.png", "reservations.png");
			}
		}
		return deleteReservationSuccess;
	}

	// This method will just update the reservation in the reservations-table in GUI
	private void checkOutAndupdateLocalReservation() {
		selectedReservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
		if (LocalDate.now().isBefore(selectedReservation.checkOutDateAsLocalDate())) {
			selectedReservation.setCheckOutDate(LocalDate.now().plusDays(1));
			String reservationDescription = selectedReservation.getRoomLocation() + " Room: "
					+ selectedReservation.getRoomNumber() + " Days: " + selectedReservation.getTotalDays();
			selectedReservation.getBill().getServiceList().get(0).setDescraption(reservationDescription);
			selectedReservation.getBill().getServiceList().get(0)
					.setPrice(selectedReservation.getPrice() * selectedReservation.getTotalDays());
		}
		reservationsList.refresh();
		super.rootLayoutController.update();
	}

	private void editReservation() {
		try {
			SearchRoomController searchRoomController = new SearchRoomController(rootLayoutController);
			searchRoomController.selectedReservation = selectedReservation;
			Scene mainScene = new Scene(searchRoomController.getParentPane());
			Stage stage = new Stage();
			stage.setScene(mainScene);
			stage.setTitle("Edit reservation");
			stage.getIcons().add(new Image("file:res/icons/search_room.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			update();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addService() {
		try {
			AddServiceController addServiceController = new AddServiceController(serverAPI);
			addServiceController.selectedReservation = this.selectedReservation;
			Scene mainScene = new Scene(addServiceController.getParentPane());
			Stage stage = new Stage();
			stage.setMinHeight(330);
			stage.setMinWidth(410);
			stage.setMaxHeight(330);
			stage.setMaxWidth(410);
			stage.setScene(mainScene);
			stage.setTitle("Add Service");
			stage.getIcons().add(new Image("file:res/icons/service.png"));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			reservationsList.refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Update the reservations-table in GUI
	private void update() {
		viewHistoryChecked();
		super.rootLayoutController.update();
	}

	private void viewBill() {
		BillsListController BillsListController = new BillsListController(rootLayoutController);
		BillsListController.selectedBill = this.selectedReservation.getBill();
		BillsListController.billToPdf();
	}

	private void markBillAsPaid() {
		BillsListController BillsListController = new BillsListController(rootLayoutController);
		BillsListController.selectedBill = this.selectedReservation.getBill();
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Pay Bill",
				"Do you want to mark the bill as paid?", "", "warning.png", "bill.png");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			BillsListController.markBillAsPaid();
		}
	}

}
