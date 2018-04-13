package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.shared.Reservation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class ReservationsListController implements Controller{
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
	private CheckBox viewAllBox ;
	@FXML
	private TextField searchName ;
	@FXML
	private TextField searchRoomNumber ;
	
	private final String RESERVATION_LIST_LAYOUT = "res/view/ReservationsList.fxml";
	public ArrayList<Reservation> reservationArray; 

	
	@FXML
	public void initialize() {		
		setData () ;
		
		reservationsList.setRowFactory(
			    new Callback<TableView<Reservation>, TableRow<Reservation>>() {
			  @Override
			  public TableRow<Reservation> call(TableView<Reservation> tableView) {
			    final TableRow<Reservation> row = new TableRow<>();
			    final ContextMenu menu = new ContextMenu();
			    MenuItem mi1 = new MenuItem("Edit");
					mi1.setOnAction((ActionEvent event) -> {
						Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
//						System.out.println(selectedItem.getCustomerName());
						try {
							SearchRoomController searchRoomController = new SearchRoomController();
							Scene mainScene = new Scene(searchRoomController.getParentPane());
							Stage stage = new Stage();
							stage.setScene(mainScene);
							stage.setTitle("Search for a room...");
							stage.showAndWait();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});

				MenuItem mi2 = new MenuItem("Check-in");
				mi2.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
				});
				
				MenuItem mi3 = new MenuItem("Check-out");
				mi3.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
				});
				
				MenuItem mi4 = new MenuItem("Add service");
				mi4.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
				});
				
				MenuItem mi5 = new MenuItem("View bill");
				mi5.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
				});
				
				MenuItem mi6 = new MenuItem("Delete");
				mi6.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
					
					Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
							"Are you sure you want to delete ?");
					closeConfirmation.setHeaderText("Confirm removal");
					closeConfirmation.initModality(Modality.APPLICATION_MODAL);
					Stage stage = (Stage) closeConfirmation.getDialogPane().getScene().getWindow();
					Optional<ButtonType> result = closeConfirmation.showAndWait();
					if (result.isPresent()) {
						
					}
				});
				
				MenuItem mi7 = new MenuItem("Cancel");
				mi7.setOnAction((ActionEvent event) -> {
					Reservation selectedItem = reservationsList.getSelectionModel().getSelectedItem();
				});
				
			    menu.getItems().addAll(mi1, mi2, mi3, mi4, mi5, mi6, mi7);

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
		loader.setLocation(new File(RESERVATION_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}
	
	public void setData () {
		
		if (reservationArray!=null) {
			ObservableList<Reservation> data = FXCollections.observableList(reservationArray);
			reservationsList.setItems(data);
			
			idCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("customerId"));
			roomCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("roomNumber"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("customerName"));
			guestNoCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("guestsNumber"));
			checkInCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkInDate"));
			checkOutCol.setCellValueFactory(new PropertyValueFactory<Reservation, String>("checkOutDate"));
			totalDaysCol.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("totalDays"));
		}
	}

}
