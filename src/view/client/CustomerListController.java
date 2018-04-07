package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.shared.Customer;
import model.shared.Hotel;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;


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
	private TableColumn<Customer, Integer> roomsNumberCol;
	@FXML
	private TableColumn <Customer, Integer> reservationsNumberCol;
	
	private final String CUSTOMER_LIST_LAYOUT = "res/view/CustomerList.fxml";
	
	@FXML
	public void initialize() {
		Hotel hotel = new Hotel ();
		hotel.defaultHotel();
		ArrayList<Customer> customersArray = hotel.getCustomersList();
		
		ObservableList<Customer> data = FXCollections.observableList(customersArray);
		customersTableView.setItems(data);
		
		idCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("customerId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("name"));
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("mobileNum"));
		passCol.setCellValueFactory(new PropertyValueFactory<Customer, String> ("identificationNumber"));
		
//		ArrayList <String> roomsNum = new ArrayList <String>() ;
//		ArrayList <String> reservationsNum = new ArrayList <String>() ;

//		for(int c=0 ; c<customersArray.size(); c++) {
//			ArrayList <Reservation> reservationsList = customersArray.get(c).getReservationsList();
//			String rooms ="";
////			String reservations = Integer.toString(reservationsList.size()) ;
//			for (int r = 0 ; r<reservationsList.size() ; r++ ) {
//				rooms += Integer.toString(reservationsList.get(r).getRoomNumber()) + ", ";
//			}
//			roomsNum.add(rooms);
////			reservationsNum.add(reservations);
//		}
			
//		ObservableList data1 = FXCollections.observableList(roomsNum);
//		customersTableView.setItems(data1);
//		ObservableList data2 = FXCollections.observableList(reservationsNum);
//		customersTableView.setItems(data2);

			
		
		roomsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("rooms"));
		reservationsNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer> ("reservationsCounter"));
		

		
	}
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(CUSTOMER_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
