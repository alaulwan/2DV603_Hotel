package view.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.client.ServerAPI;
import model.shared.Customer;
import model.shared.Customer.Gender;
import model.shared.Customer.IdentificationType;
import model.shared.filters.customersFilters.NameCustomersFilter;
import javafx.scene.control.ComboBox;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

import javafx.scene.control.ChoiceBox;

public class AddCustomerController implements Controller {
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
	private ComboBox<Customer> customersNameList;
	@FXML
	private ToggleButton newCustomerButton;
	@FXML
	private Label existingCustomerLabel;
	@FXML
	private Label guestsNameLabel;

	private final String ADD_CUSTOMER_LAYOUT = "res/view/AddCustomer.fxml";

	public ArrayList <Customer> customersList; 
	public Customer currentCustomer;

	@FXML
	public void initialize() {
		identificationType.setItems(FXCollections.observableList(Arrays.asList(IdentificationType.values())));
		if (currentCustomer == null) {
			setComboBoxFactory();
			importAllCustomers();
			setEditable(false);
		} else {
			existingCustomerLabel.setVisible(false);
			newCustomerButton.setVisible(false);
			customersNameList.setVisible(false);
			guestsNameBox.setVisible(false);
			guestsNameLabel.setVisible(false);
			loadCustomer();
		}
	}

	
	@FXML
	public void newCustomerBtn() {
		clearFields();
		setEditable(newCustomerButton.isSelected());
	}
	
	@FXML
	public void comboBoxCustomerTyped() {
		ArrayList <Customer> customersList = new ArrayList <Customer> (this.customersList);
		String customerName = customersNameList.getEditor().getText();
		NameCustomersFilter nameCustomersFilter = new NameCustomersFilter(customerName);
		nameCustomersFilter.applyCustomersFilter(customersList);
		ObservableList<Customer> data = FXCollections.observableList(customersList);
		customersNameList.setItems(data);
		customersNameList.hide();
		customersNameList.show();
	}
	
	@FXML
	public void comboBoxCustomerSelected() {
		if (customersNameList.getValue()!=null) {
			currentCustomer = customersNameList.getValue();
			loadCustomer();
			//setEditable(false);
			newCustomerButton.setSelected(false);
		}
	}
	
	@FXML
	public void cancel() {
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	private void importAllCustomers() {
		customersList = ServerAPI.getCustomersList(null);
		customersNameList.setItems(FXCollections.observableList(customersList));

	}

	private void loadCustomer() {
		nationalityBox.setText(currentCustomer.getNationality());
		creditCardNumberBox.setText(currentCustomer.getCreditCardNum());
		nameBox.setText(currentCustomer.getName());
		phoneNumberBox.setText(currentCustomer.getPhoneNum());
		emailBox.setText(currentCustomer.getEmail());
		birthDateBox.setValue(currentCustomer.birthDateToLocaleDate());
		idNumberBox.setText(currentCustomer.getCustomerId() + "");
		idNumberBox.setEditable(false);
		addressBox.setText(currentCustomer.getAddress());
		identificationType.setValue(currentCustomer.getIdentificationType());
		descriptionBox.setText(currentCustomer.getDescription());
		maleBox.setSelected(currentCustomer.getGender() == Gender.MALE);
		femaleBox.setSelected(currentCustomer.getGender() == Gender.FEMALE);
	}
	private void clearFields() {
		nationalityBox.setText("");
		creditCardNumberBox.setText("");
		nameBox.setText("");
		phoneNumberBox.setText("");
		emailBox.setText("");
		birthDateBox.setValue(null);
		idNumberBox.setText("");
		idNumberBox.setEditable(false);
		addressBox.setText("");
		identificationType.setValue(null);
		descriptionBox.setText("");
		maleBox.setSelected(true);
	}
	
	private void setEditable(boolean editable) {
		nationalityBox.setDisable(!editable);
		creditCardNumberBox.setDisable(!editable);
		nameBox.setDisable(!editable);
		phoneNumberBox.setDisable(!editable);
		emailBox.setDisable(!editable);
		birthDateBox.setDisable(!editable);
		idNumberBox.setDisable(!editable);
		idNumberBox.setDisable(!editable);
		addressBox.setDisable(!editable);
		identificationType.setDisable(!editable);
		descriptionBox.setDisable(!editable);
		maleBox.setDisable(!editable);
		femaleBox.setDisable(!editable);
	}
	
	private void setComboBoxFactory() {
		// list of values showed in combo box drop down
		Callback<ListView<Customer>, ListCell<Customer>> factory = lv -> new ListCell<Customer>() {

		    @Override
		    protected void updateItem(Customer item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(empty ? "" : item.getName());
		    }

		};
		customersNameList.setCellFactory(factory);
		customersNameList.setButtonCell(factory.call(null));

		// selected value showed in combo box
		customersNameList.setConverter(new StringConverter<Customer>() {
			@Override
			public String toString(Customer user) {
				if (user == null) {
					return null;
				} else {
					return user.getName();
				}
			}

			@Override
			public Customer fromString(String userId) {
				return null;
			}
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
