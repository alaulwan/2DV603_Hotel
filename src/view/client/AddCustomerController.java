package view.client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.shared.Customer;
import model.shared.Customer.Gender;
import model.shared.Customer.IdentificationType;
import javafx.scene.control.ComboBox;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;

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
	private ChoiceBox<IdentificationType> identificationType;
	@FXML
	private TextField descriptionBox;
	@FXML
	private RadioButton maleBox;
	@FXML
	private ToggleGroup genderBox;
	@FXML
	private RadioButton femaleBox;
	@FXML
	private ComboBox <Customer> customersNameList;
	@FXML
	private ToggleButton newCustomerButton;
	@FXML
	private Label existingCustomerLabel;
	@FXML
	private Label guestsNameLabel;
	
	
	private final String ADD_CUSTOMER_LAYOUT = "res/view/AddCustomer.fxml";

	public Customer currentCustomer;
	
	@FXML
	public void initialize() {
		identificationType.setItems(FXCollections.observableList(Arrays.asList(IdentificationType.values())));
		if(currentCustomer != null) {
			loadCustomer();
		}
	}
	
	@FXML
	public void cancel() {
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	private void loadCustomer() {
		existingCustomerLabel.setVisible(false);
		newCustomerButton.setVisible(false);
		customersNameList.setVisible(false);
		
		nationalityBox.setText(currentCustomer.getNationality());
		creditCardNumberBox.setText(currentCustomer.getCreditCardNum());
		nameBox.setText(currentCustomer.getName());
		phoneNumberBox.setText(currentCustomer.getPhoneNum());
		emailBox.setText(currentCustomer.getEmail());
		guestsNameBox.setVisible(false);
		guestsNameLabel.setVisible(false);
		birthDateBox.setValue(currentCustomer.birthDateToLocaleDate());;
		idNumberBox.setText(currentCustomer.getCustomerId()+"");
		idNumberBox.setEditable(false);
		addressBox.setText(currentCustomer.getAddress());
		identificationType.setValue(currentCustomer.getIdentificationType());
		descriptionBox.setText(currentCustomer.getDescription());
		maleBox.setSelected(currentCustomer.getGender()== Gender.MALE);
		femaleBox.setSelected(currentCustomer.getGender()== Gender.FEMALE);
	}

	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(ADD_CUSTOMER_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
