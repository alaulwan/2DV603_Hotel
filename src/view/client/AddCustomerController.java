package view.client;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import javafx.scene.control.ToggleButton;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

import javafx.scene.control.ChoiceBox;

public class AddCustomerController implements Controller{
	@FXML
	private Button cancelButton;
	@FXML
	private Button saveButton;
	@FXML
	private TextField nationalityBox;
	@FXML
	private TextField creditCardNumberBox;
	@FXML
	private TextField nameBox;
	@FXML
	private TextField phoneNumberBox;
	@FXML
	private TextField emailBox;
	@FXML
	private TextField guestsNameBox;
	@FXML
	private DatePicker birthDateBox;
	@FXML
	private TextField idNumberBox;
	@FXML
	private TextField addressBox;
	@FXML
	private ChoiceBox identificationType;
	@FXML
	private TextField descriptionBox;
	@FXML
	private RadioButton maleBox;
	@FXML
	private ToggleGroup genderBox;
	@FXML
	private RadioButton femaleBox;
	@FXML
	private ComboBox customersNameList;
	@FXML
	private ToggleButton newCustomerButton;
	
	private final String ADD_CUSTOMER_LAYOUT = "res/view/AddCustomer.fxml";

	@FXML
	public void initialize() {	
			
		cancelButton.setOnMouseClicked(event -> {
			((Stage) cancelButton.getScene().getWindow()).close();
		});
	}
	
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(ADD_CUSTOMER_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
