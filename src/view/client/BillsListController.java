package view.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

import javafx.scene.input.KeyEvent;
import model.shared.Bill;
import model.shared.Bill.PayStatus;
import model.shared.Reservation;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class BillsListController implements Controller{
	@FXML
	private TableView<Bill> billsTable;
	@FXML
	private TableColumn<Bill,Integer> idCol;
	@FXML
	private TableColumn<Reservation, Integer> customerIdCol;
	@FXML
	private TableColumn<Reservation, String> customerNameCol;
	@FXML
	private TableColumn<Reservation, Integer> totlaPriceCol;
	@FXML
	private TableColumn<Reservation, PayStatus> statusCol;
	@FXML
	private TextField searchName;
	@FXML
	private CheckBox viewAllBox;
	
	private final String BILLS_LIST_LAYOUT = "res/view/BillsList.fxml";


	// Event Listener on TextField[#searchName].onKeyReleased
	@FXML
	public void apllyChosenFilters(KeyEvent event) {
	}
	
	// Event Listener on CheckBox[#viewAllBox].onAction
	@FXML
	public void viewPaidChecked(ActionEvent event) {

	}
	@Override
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(BILLS_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
		}
}
