package view.client;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;

public class CustomerListController implements Controller{
	@FXML
	private TableView tableView;
	@FXML
	private TableColumn idCol;
	@FXML
	private TableColumn titleCol;
	@FXML
	private TableColumn phoneNumberCol;
	@FXML
	private TableColumn passCol;
	@FXML
	private TableColumn checkInCol;
	@FXML
	private TableColumn checkOutCol;
	@FXML
	private TableColumn totalDaysCol;
	
	private final String CUSTOMER_LIST_LAYOUT = "res/view/CustomerList.fxml";
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(CUSTOMER_LIST_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
