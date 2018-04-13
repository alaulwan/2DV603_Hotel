package view.client;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.shared.Service;
import model.shared.Service.ServiceType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ChoiceBox;

public class AddServiceController implements Controller {
	@FXML
	private ChoiceBox<ServiceType> type;
	@FXML
	private Spinner<Integer> quantity;
	@FXML
	private TextArea description;
	@FXML
	private Button addButton;
	@FXML
	private Button cancelButton;

	private final String ADD_SERVICE_LAYOUT = "res/view/AddService.fxml";

	@FXML
	public void initialize() throws IOException {
		type.setValue(Service.ServiceType.RESTURANT);
		type.setItems(FXCollections.observableArrayList(Service.ServiceType.RESTURANT, Service.ServiceType.CLEANING,
				Service.ServiceType.GYM, Service.ServiceType.PARKING));
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
		quantity.setValueFactory(valueFactory);
		
		cancelButton.setOnMouseClicked(event -> {
			((Stage) cancelButton.getScene().getWindow()).close();
		});
	}

	@Override
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(ADD_SERVICE_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

}
