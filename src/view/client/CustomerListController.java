package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.client.ServerAPI;
import model.shared.Customer;
import model.shared.Reservation;
import model.shared.filters.billsFilters.BillsFilter;
import model.shared.filters.customersFilters.CustomersFilter;
import model.shared.filters.customersFilters.NameCustomersFilter;
import model.shared.filters.customersFilters.ReservationLocationCustomersFilter;
import model.shared.filters.customersFilters.ReservationStatusCustumersFilter;
import model.shared.filters.reservationsFilters.CustomerNameReservationsFilter;
import model.shared.filters.reservationsFilters.LocationReservationsFilter;
import model.shared.filters.reservationsFilters.ReservationsFilter;
import model.shared.filters.reservationsFilters.StatusReservationsFilter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;


public class CustomerListController implements Controller{
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
	
	@FXML
	public void initialize() {		
		setData () ;
		setContextMenu();

		
		
	}
	
	

	private void setData() {
		if (customersArray!= null) {
			ObservableList<Customer> data = FXCollections.observableList(customersArray);
			customersTableView.setItems(data);
			
			idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("customerId"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("name"));
			phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("mobileNum"));
			passCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("identificationNumber"));
			roomsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("currentReservedRooms"));
			reservationsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("currentReservedNumbers"));	
		}		
	}
	
	@FXML
	public void viewHistoryChecked() {
		ArrayList<CustomersFilter> customersFilterList = new ArrayList<CustomersFilter> ();
		ReservationLocationCustomersFilter reservationLocationCustomersFilter = new ReservationLocationCustomersFilter (ServerAPI.location);
		ReservationStatusCustumersFilter reservationStatusCustumersFilter;
		
		if (viewAllBox.isSelected()) {
			reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(true, true, true, true);
		}
		else {
			reservationStatusCustumersFilter = new ReservationStatusCustumersFilter(true, true, false, false);
		}
		
		customersFilterList.add(reservationStatusCustumersFilter);
		customersFilterList.add(reservationLocationCustomersFilter);
		
		this.customersArray = ServerAPI.getCustomersList(customersFilterList);
		
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
	
	private void setContextMenu() {
		customersTableView.setRowFactory(
			    new Callback<TableView<Customer>, TableRow<Customer>>() {
			  @Override
			  public TableRow<Customer> call(TableView<Customer> tableView) {
			    final TableRow<Customer> row = new TableRow<>();
			    final ContextMenu menu = new ContextMenu();
			    MenuItem mi1 = new MenuItem("Edit");
					mi1.setOnAction((ActionEvent event) -> {
						Customer selectedItem = customersTableView.getSelectionModel().getSelectedItem();
						try {
							AddCustomerController addCustomerController = new AddCustomerController();
							addCustomerController.currentCustomer = selectedItem;
							Scene mainScene = new Scene(addCustomerController.getParentPane());
							Stage stage = new Stage();
							stage.setScene(mainScene);
							stage.setTitle("Edit the customer information");
							stage.showAndWait();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				
					MenuItem mi2 = new MenuItem("View reservations");
					mi2.setOnAction((ActionEvent event) -> {
						Customer selectedItem = customersTableView.getSelectionModel().getSelectedItem();
						
						try {
							ReservationsListController c = new ReservationsListController();
							Scene mainScene = new Scene(c.getParentPane());
							
							c.searchName.setVisible(false);

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
					Customer selectedItem = customersTableView.getSelectionModel().getSelectedItem();
					
					Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
							"Are you sure you want to delete ?");
					closeConfirmation.setHeaderText("Confirm removal");
					closeConfirmation.initModality(Modality.APPLICATION_MODAL);
					Stage stage = (Stage) closeConfirmation.getDialogPane().getScene().getWindow();
					Optional<ButtonType> result = closeConfirmation.showAndWait();
					if (result.isPresent()) {
						
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

	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(CUSTOMER_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
