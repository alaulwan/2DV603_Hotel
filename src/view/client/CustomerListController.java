package view.client;

import java.io.IOException;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.filters.customersFilters.CustomersFilter;
import model.shared.filters.customersFilters.NameCustomersFilter;
import model.shared.filters.customersFilters.ReservationLocationCustomersFilter;
import model.shared.filters.customersFilters.ReservationStatusCustumersFilter;
import model.shared.filters.reservationsFilters.CustomerNameReservationsFilter;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;


public class CustomerListController extends Controller{
	@FXML
	private TableView<Customer> customersTableView;
	@FXML
	private TableColumn<Customer, Integer> idCol;
	@FXML
	private TableColumn <Customer, String> nameCol;
	@FXML
	private TableColumn<Customer, String>  phoneNumberCol;
	@FXML
	private TableColumn<Customer, String>  passCol;
	@FXML
	private TableColumn<Customer, String> roomsNumberCol;
	@FXML
	private TableColumn <Customer, Integer> reservationsNumberCol;
	@FXML
	public CheckBox viewAllBox ;
	@FXML
	public TextField searchName ;
	
	private final String CUSTOMER_LIST_LAYOUT = "res/view/CustomerList.fxml";
	
	public ArrayList<Customer> customersArray;
	private Customer selectedCustomer;
	
	public CustomerListController(RootLayoutController rootLayoutController) {
		super.fxmlPath = CUSTOMER_LIST_LAYOUT;
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
	}
	
	@FXML
	public void initialize() {		
		setData () ;
		setContextMenu();
	}
	
	

	private void setData() {
		if (customersArray!= null) {
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
			for (Customer customer : customersArray) {
				locationReservationsFilter.applyReservationsFilter(customer.getReservationsList());
			}
			ObservableList<Customer> data = FXCollections.observableList(customersArray);
			customersTableView.setItems(data);
			
			idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("customerId"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("name"));
			phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("phoneNum"));
			passCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("identificationNumber"));
			
			roomsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("currentReservedRooms"));
			reservationsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("currentReservedNumbers"));	
		}		
	}
	
	@FXML
	public void viewHistoryChecked() {
		ArrayList<CustomersFilter> customersFilterList = new ArrayList<CustomersFilter> ();
		ReservationLocationCustomersFilter reservationLocationCustomersFilter = new ReservationLocationCustomersFilter (serverAPI.location);
		ReservationStatusCustumersFilter reservationStatusCustumersFilter;
		
		if (viewAllBox.isSelected()) {
			reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(true, true, true, true);
		}
		else {
			reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(true, true, false, false);
		}
		
		customersFilterList.add(reservationStatusCustumersFilter);
		customersFilterList.add(reservationLocationCustomersFilter);
		
		this.customersArray = serverAPI.getCustomersList(customersFilterList);
		if (customersArray!= null) {
			LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter(serverAPI.location);
			for (Customer customer : customersArray) {
				locationReservationsFilter.applyReservationsFilter(customer.getReservationsList());
			}
		}
		apllyAllChosenFilters();
	}
	
	@FXML
	public void apllyAllChosenFilters() {
		ArrayList<Customer> customorsArray = new ArrayList<Customer> (this.customersArray);
		applyCustomerNameFilter (customorsArray);
	}
	
	public void applyCustomerNameFilter(ArrayList<Customer> customorsArray){
		String customerName = searchName.getText();
		NameCustomersFilter nameCustomersFilter = new NameCustomersFilter(customerName);
		nameCustomersFilter.applyCustomersFilter(customorsArray);
		ObservableList<Customer> data = FXCollections.observableList(customorsArray);
		customersTableView.setItems(data);
	}
	
	
	
	private ArrayList<Reservation> importReservationsListByCustumer(String customerName) {
		ArrayList<ReservationsFilter> reservationsFilterList = new ArrayList<ReservationsFilter> ();
		LocationReservationsFilter locationReservationsFilter = new LocationReservationsFilter (serverAPI.location);
		StatusReservationsFilter statusReservationsFilter = new StatusReservationsFilter(true, true, false, false);
		CustomerNameReservationsFilter customerNameReservationsFilter = new CustomerNameReservationsFilter (customerName);
		reservationsFilterList.add(locationReservationsFilter);
		reservationsFilterList.add(statusReservationsFilter);
		reservationsFilterList.add(customerNameReservationsFilter);
		
		return serverAPI.getReservationsList(reservationsFilterList);
	}
	
	private void setContextMenu() {
		customersTableView.setRowFactory(
			    new Callback<TableView<Customer>, TableRow<Customer>>() {
			  @Override
			  public TableRow<Customer> call(TableView<Customer> tableView) {
			    final TableRow<Customer> row = new TableRow<>();
			    row.setOnMousePressed((MouseEvent t) -> {
			    	selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
			    });
			    
			    final ContextMenu menu = new ContextMenu();
			    MenuItem mi1 = new MenuItem("Edit");
					mi1.setOnAction((ActionEvent event) -> {
						try {
							AddCustomerController addCustomerController = new AddCustomerController(serverAPI);
							addCustomerController.currentCustomer = selectedCustomer;
							Scene mainScene = new Scene(addCustomerController.getParentPane());
							Stage stage = new Stage();
							stage.setScene(mainScene);
							stage.setMinWidth(650);
							stage.setMinHeight(680);
							stage.setMaxWidth(650);
							stage.setMaxHeight(680);
							stage.setTitle("Edit the customer information");
							stage.showAndWait();
						} catch (IOException e) {
							e.printStackTrace();
						}
						viewHistoryChecked();
					});
				
					MenuItem mi2 = new MenuItem("View reservations");
					mi2.setOnAction((ActionEvent event) -> {
						ReservationsListController reservationsListController = new ReservationsListController(rootLayoutController);
						try {
							reservationsListController.reservationArray = importReservationsListByCustumer(selectedCustomer.getName());
							
							Scene mainScene = new Scene(reservationsListController.getParentPane());
							reservationsListController.searchName.setText(selectedCustomer.getName());
							reservationsListController.searchName.setVisible(false);
							Stage stage = new Stage();
							stage.setScene(mainScene);
							stage.setTitle("Reservations List");
							stage.showAndWait();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					
				MenuItem mi3 = new MenuItem("Delete");
				mi3.setOnAction((ActionEvent event) -> {
					Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
							"Are you sure you want to delete ?");
					deleteConfirmation.setHeaderText("Confirm removal");
					deleteConfirmation.initModality(Modality.APPLICATION_MODAL);
					Optional<ButtonType> result = deleteConfirmation.showAndWait();
					if (result.isPresent() && result.get().equals(ButtonType.OK)) {
						deleteCustomer();
					}
				});
				
			    menu.getItems().addAll(mi1, mi2, mi3);

			    // only display context menu for non-null items:
			    row.contextMenuProperty().bind(
			      Bindings.when(Bindings.isNotNull(row.itemProperty()))
			      .then(menu)
			      .otherwise((ContextMenu)null));
			    return row;
			  }

			
			});
		
	}
	private boolean deleteCustomer() {
		boolean deleteCustomerSuccess = serverAPI.delete(selectedCustomer);
		if (deleteCustomerSuccess) {
			Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Delete Customer", "Are you sure you want to delete this customer",
					"");
			if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			update();
			alertWindow(AlertType.INFORMATION, "Delete Customer", "Delete Customer Successed", "");
			}
		}
		else {
			alertWindow(AlertType.INFORMATION, "Delete Customer", "Delete Customer Failed", "Cannot delete customer with pindding or checked in reservation.");
		}
		return deleteCustomerSuccess;
	}

	private void update() {
		viewHistoryChecked();
		super.rootLayoutController.update();
	}
}
