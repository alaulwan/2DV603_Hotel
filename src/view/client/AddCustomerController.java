package view.client;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.shared.Customer;
import model.shared.Customer.Gender;
import model.shared.Customer.IdentificationType;
import model.shared.Reservation;
import model.shared.filters.customersFilters.NameCustomersFilter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;

public class AddCustomerController extends Controller {
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
	public ArrayList<Customer> customersList;
	public Customer currentCustomer;
	public Reservation reservation;

	public AddCustomerController() {
		super.fxmlPath = ADD_CUSTOMER_LAYOUT;
	}

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
		currentCustomer = null;
		setEditable(newCustomerButton.isSelected());
	}

	@FXML
	public void saveCustomerBtn() {
		InputVerifier inputVerifier = new InputVerifier();
		if (!inputVerifier.isCorrectCustomerInfo(this)) {
			return;
		}
		Customer newCustomer = new Customer(this.nameBox.getText(), this.birthDateBox.getValue(),
				this.maleBox.isSelected() ? Gender.MALE : Gender.FEMALE, this.phoneNumberBox.getText(),
				this.identificationType.getValue(), this.idNumberBox.getText(), this.creditCardNumberBox.getText(),
				this.addressBox.getText(), this.nationalityBox.getText(), this.emailBox.getText(),
				this.descriptionBox.getText());
		if (currentCustomer == null) {
			newCustomer.getReservationsList().add(reservation);
			serverAPI.put(newCustomer);
		} else {
			serverAPI.post(newCustomer, currentCustomer.getCustomerId());
			if (reservation != null) {
				reservation.setCustomerId(currentCustomer.getCustomerId());
				reservation.setCustomerName(this.nameBox.getText());
				serverAPI.put(reservation);
			}
		}
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	@FXML
	public void comboBoxCustomerTyped() {
		ArrayList<Customer> customersList = new ArrayList<Customer>(this.customersList);
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
		if (customersNameList.getValue() != null) {
			currentCustomer = customersNameList.getValue();
			loadCustomer();
			// setEditable(false);
			newCustomerButton.setSelected(false);
			customersNameList.setPromptText(currentCustomer.getName());
			customersNameList.setItems(FXCollections.observableList(customersList));
			setEditable(true);
		}
	}

	@FXML
	public void cancel() {
		((Stage) cancelButton.getScene().getWindow()).close();
	}

	private void importAllCustomers() {
		customersList = serverAPI.getCustomersList(null);
		customersNameList.setItems(FXCollections.observableList(customersList));

	}

	private void loadCustomer() {
		nationalityBox.setText(currentCustomer.getNationality());
		creditCardNumberBox.setText(currentCustomer.getCreditCardNum());
		nameBox.setText(currentCustomer.getName());
		phoneNumberBox.setText(currentCustomer.getPhoneNum());
		emailBox.setText(currentCustomer.getEmail());
		birthDateBox.setValue(currentCustomer.birthDateToLocaleDate());
		idNumberBox.setText(currentCustomer.getIdentificationNumber() + "");
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
				setText(empty ? "" : item.getName() + " ,ID:" + item.getIdentificationNumber());
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
					return user.getName() + " ,ID:" + user.getIdentificationNumber();
				}
			}

			@Override
			public Customer fromString(String userId) {
				return null;
			}
		});
	}

}
