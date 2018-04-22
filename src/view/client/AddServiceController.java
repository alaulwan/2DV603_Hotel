package view.client;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.shared.Service;
import model.shared.Service.ServiceType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ChoiceBox;

public class AddServiceController extends Controller {
	@FXML
	private ChoiceBox<ServiceType> type;
	@FXML
	private Spinner<Integer> quantity;
	@FXML
	private TextField totalPrice;
	@FXML
	private Button addButton;
	@FXML
	private Button cancelButton;

	private final String ADD_SERVICE_LAYOUT = "res/view/AddService.fxml";

	public AddServiceController() {
		super.fxmlPath = ADD_SERVICE_LAYOUT;
	}
	
	@FXML
	public void initialize() throws IOException {
		type.setValue(Service.ServiceType.BREAKFAST);
		ObservableList<ServiceType> TypeArr = FXCollections.observableArrayList(Service.ServiceType.values()) ;
		TypeArr.remove(ServiceType.RESERVATION);
		type.setItems(TypeArr);
				
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1);
		quantity.setValueFactory(valueFactory);
		
		totalPrice.setDisable(true);
		
		cancelButton.setOnMouseClicked(event -> {
			((Stage) cancelButton.getScene().getWindow()).close();
		});
	}

	@FXML
	public void setTotalPrice() {
		Service service = new Service(type.getValue(), quantity.getValue() , 0 );
		totalPrice.setText(Float.toString(service.getPrice()));
	}
	

}
