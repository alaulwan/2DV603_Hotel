package view.client;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.client.ServerAPI;
import model.shared.Reservation;
import model.shared.Room;
import model.shared.Reservation.ReservationStatus;
import model.shared.filters.reservationsFilters.CustomerNameReservationsFilter;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.RoomNumReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class ReservationsListController extends Controller{
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
	public CheckBox viewAllBox ;
	@FXML
	public HBox hbox ;
	@FXML
	public TextField searchName ;
	@FXML
	public TextField searchRoomNumber ;
	
	private final String RESERVATION_LIST_LAYOUT = "res/view/ReservationsList.fxml";
	public ArrayList <Room> roomsList;
	public ArrayList<Reservation> reservationArray;
	public Reservation selectedReservation;

	public ReservationsListController(RootLayoutController rootLayoutController) {
		super.fxmlPath = RESERVATION_LIST_LAYOUT;
		super.rootLayoutController = rootLayoutController;
	}
	
	@FXML
	public void initialize() {		
		setData () ;
		setContextMenu();
	}
	
	

	@FXML
	public void viewHistoryChecked() {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (ServerAPI.location);
		StatusReservationsFilter statusReservationsFilter;
		if (viewAllBox.isSelected()) {
			statusReservationsFilter = new StatusReservationsFilter(true, true, true, true);
		}
		else {
			statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		}
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		this.reservationArray= ServerAPI.getReservationsList(reservationsFilterList);
		apllyAllChosenFilters();
	}
	
	@FXML
	public void apllyAllChosenFilters() {
		ArrayList<Reservation> reservationsArray = new ArrayList<Reservation> (this.reservationArray);
		applyCustomerNameFilter (reservationsArray);
		applyRoomNumFilter (reservationsArray);
	}
	
	public void applyCustomerNameFilter(ArrayList<Reservation> reservationsArray){
		String customerName = searchName.getText();
		CustomerNameReservationsFilter customerNameReservationsFilter = new CustomerNameReservationsFilter(customerName);
		customerNameReservationsFilter.applyReservationsFilter(reservationsArray);
		ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
		reservationsList.setItems(data);
	}
	
	
	public void applyRoomNumFilter(ArrayList<Reservation> reservationsArray){
		if (searchRoomNumber.getText().isEmpty()) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
			reservationsList.setItems(data);
		}
		else {
			int roomNum = 0;
			try {
				roomNum = Integer.parseInt(searchRoomNumber.getText());
			}catch (Exception e){
				return;
			}
			RoomNumReservationsFilter roomNumReservationsFilter = new RoomNumReservationsFilter(roomNum);
			roomNumReservationsFilter.applyReservationsFilter(reservationsArray);
			ObservableList<Reservation> data = FXCollections.observableList(reservationsArray);
			reservationsList.setItems(data);
		}
		
	}
	
	public void setData () {
		
		if (reservationArray!=null) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationArray);
			reservationsList.setItems(data);
			
			idCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
			roomCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("roomNumber"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
			guestNoCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("guestsNumber"));
			checkInCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkInDate"));
			checkOutCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkOutDate"));
			totalDaysCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("totalDays"));
			reservationStatusCol.setCellValueFactory(new PropertyValueFactory<Reservation, ReservationStatus>("reservationStatus"));
		}
	}
	
	private void setContextMenu() {
		reservationsList.setRowFactory(
			    new Callback<TableView<Reservation>, TableRow<Reservation>>() {
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
					chekInReservation (selectedReservation.getReservationId());
				});
				
				MenuItem mi3 = new MenuItem("Check-out");
				mi3.setOnAction((ActionEvent event) -> {
					chekOutReservation (selectedReservation.getReservationId());
				});
				
				MenuItem mi4 = new MenuItem("Add service");
				mi4.setOnAction((ActionEvent event) -> {
					
					try {
						AddServiceController addServiceController = new AddServiceController();
						Scene mainScene = new Scene(addServiceController.getParentPane());
						Stage stage = new Stage();
						stage.setScene(mainScene);
						stage.setTitle("Add Service");
						stage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				MenuItem mi5 = new MenuItem("View bill");
				mi5.setOnAction((ActionEvent event) -> {
					viewBill();
				});
				
				MenuItem mi6 = new MenuItem("Delete");
				mi6.setOnAction((ActionEvent event) -> {
					
					Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
							"Are you sure you want to delete ?");
					closeConfirmation.setHeaderText("Confirm removal");
					closeConfirmation.initModality(Modality.APPLICATION_MODAL);
					Stage stage = (Stage) closeConfirmation.getDialogPane().getScene().getWindow();
					Optional<ButtonType> result = closeConfirmation.showAndWait();
					if (result.isPresent()) {
						deleteReservation();
					}
				});
				
				MenuItem mi7 = new MenuItem("Cancel");
				mi7.setOnAction((ActionEvent event) -> {
					cancelReservation (selectedReservation.getReservationId());
				});
				
			    menu.getItems().addAll(mi1, mi2, mi3, mi4, mi5, mi6, mi7);

			    // only display context menu for non-null items:
			    row.contextMenuProperty().bind(
			      Bindings.when(Bindings.isNotNull(row.itemProperty()))
			      .then(menu)
			      .otherwise((ContextMenu)null));
			    
			    row.setOnMousePressed((MouseEvent t) -> {
			    	selectedReservation= reservationsList.getSelectionModel().getSelectedItem();
			    	if (selectedReservation != null) {
			    		boolean editStatus = selectedReservation.getReservationStatus().equals(ReservationStatus.PENDING);
		    			boolean checkInStatus = selectedReservation.getReservationStatus().equals(ReservationStatus.PENDING) && selectedReservation.checkInDateAsLocalDate().equals(LocalDate.now());
		    			for (Reservation rs : reservationArray) {
		    				if(rs.getRoomId()==selectedReservation.getRoomId() && rs.getReservationStatus().equals(ReservationStatus.CHECKED_IN)) {
		    					checkInStatus = false;
		    					break;
		    				}
		    			}
		    			boolean checkOutStatus = selectedReservation.getReservationStatus().equals(ReservationStatus.CHECKED_IN);
		    			boolean deleteStatus = !selectedReservation.getReservationStatus().equals(ReservationStatus.CHECKED_IN);
		    			boolean cancelStatus = selectedReservation.getReservationStatus().equals(ReservationStatus.PENDING);
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
	
	public boolean chekInReservation (int reservationId){
		boolean checkInSuccess = ServerAPI.checkIn(reservationId);
		if (checkInSuccess) {
			update();
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Successed", "");
		}
		else {
			alertWindow(AlertType.INFORMATION, "CheckIn", "CheckIn Failed", "");
		}
		return checkInSuccess;
	}
	
	public boolean chekOutReservation (int reservationId){
		boolean checkOutSuccess = ServerAPI.checkOut(reservationId);
		if (checkOutSuccess) {
			checkOutAndupdateLocalReservation();
			Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "CheckOut", "CheckOut Successed", "Do you want to view the bill?");
			if (result.isPresent() && result.get().equals(ButtonType.YES)) {
				viewBill();
			}
		}
		else {
			alertWindow(AlertType.INFORMATION, "CheckOut", "CheckIn Failed", "");
		}
		return checkOutSuccess;
	}
	
	public boolean cancelReservation (int reservationId){
		boolean cancelReservationSuccess = ServerAPI.cancelReservation(reservationId);
		if (cancelReservationSuccess) {
			update();
			alertWindow(AlertType.INFORMATION, "Cancel Reservation", "Cancel Reservation Successed", "");
		}
		else {
			alertWindow(AlertType.INFORMATION, "Cancel Reservation", "Cancel Reservation Failed", "");
		}
		return cancelReservationSuccess;
	}
	
	private boolean deleteReservation() {
		boolean deleteReservationSuccess = ServerAPI.delete(selectedReservation);
		if (deleteReservationSuccess) {
			update();
			alertWindow(AlertType.INFORMATION, "Delete Reservation", "Delete Reservation Successed", "");
		}
		else {
			alertWindow(AlertType.INFORMATION, "Delete Reservation", "Delete Reservation Failed", "");
		}
		return deleteReservationSuccess;		
	}
	
	private void checkOutAndupdateLocalReservation() {
		selectedReservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
		if (LocalDate.now().isBefore(selectedReservation.checkOutDateAsLocalDate())) {
			selectedReservation.setCheckOutDate(LocalDate.now().plusDays(1));
			String reservationDescription = selectedReservation.getRoomLocation() + " Room: "+ selectedReservation.getRoomNumber() +  " Days: " + selectedReservation.getTotalDays();
			selectedReservation.getBill().getServiceList().get(0).setDescraption(reservationDescription);
			selectedReservation.getBill().getServiceList().get(0).setPrice(selectedReservation.getPrice()* selectedReservation.getTotalDays());
		}
		reservationsList.refresh();
		super.rootLayoutController.update();		
	}
	
	private void editReservation() {
		try {
			SearchRoomController searchRoomController = new SearchRoomController();
			searchRoomController.selectedReservation =selectedReservation;
			Scene mainScene = new Scene(searchRoomController.getParentPane());
			Stage stage = new Stage();
			stage.setScene(mainScene);
			stage.setTitle("Search for a room...");
			stage.showAndWait();
			update();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	private void update() {
		viewHistoryChecked();
		super.rootLayoutController.update();
	}
	
	private void viewBill() {
		BillsListController BillsListController = new BillsListController();
		BillsListController.selectedBill = this.selectedReservation.getBill();
		BillsListController.billToPdf();
	}

}
