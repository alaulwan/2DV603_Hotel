package view.client;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import javafx.scene.control.ComboBox;

import javafx.scene.control.ToggleButton;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

import javafx.scene.control.ChoiceBox;

public class AddCoustomerController {
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

}
