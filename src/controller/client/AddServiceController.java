package controller.client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.client.ServerAPI;
import model.shared.Reservation;
import model.shared.Service;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ChoiceBox;

public class AddServiceController extends Controller {
	@FXML
	private ChoiceBox<Service> servicesBox;
	@FXML
	private TextField serviceTypeField;
	@FXML
	private Spinner<Integer> quantity;
	@FXML
	private TextField totalPriceField;
	@FXML
	private Button addButton;
	@FXML
	private Button cancelButton;

	private final String ADD_SERVICE_LAYOUT = "/view/client/AddService.fxml";
	public ArrayList <Service> serviceList;
	public Service selectedService;
	public Reservation selectedReservation;
	public ServerAPI serverAPI;
	
	public AddServiceController(ServerAPI serverAPI) {
		this.serverAPI = serverAPI;
		super.urlPath = getClass().getResource(ADD_SERVICE_LAYOUT);
	}
	
	@FXML
	public void initialize() throws IOException {
		serviceList = serverAPI.getServicesList();
		ObservableList<Service> TypeArr = FXCollections.observableArrayList(serviceList) ;
		servicesBox.setItems(TypeArr);
				
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1);
		quantity.setValueFactory(valueFactory);
		quantity.getEditor().setDisable(true);
		
		serviceTypeField.setDisable(true);
		totalPriceField.setDisable(true);
	}
	
	@FXML
	public void servicesBoxChange(){
		selectedService = servicesBox.getValue();
		quantity.getValueFactory().setValue(1);
		serviceTypeField.setText(selectedService.getDescraption());
		totalPriceField.setText(selectedService.getPrice()*quantity.getValue()+"");
	}
	
	
	@FXML
	public void quantityChange(){
		if (selectedService!= null)
			totalPriceField.setText(selectedService.getPrice()*quantity.getValue()+"");
	}
	
	
	@FXML
	public void addClicked(){
		if(selectedService != null) {
			selectedService.setPiecesNumber(quantity.getValue());
			selectedReservation.getBill().addService(selectedService.getServiceType(), selectedService.getPrice(), selectedService.getPiecesNumber(), selectedService.getDescraption());
			serverAPI.post(selectedReservation, selectedReservation.getReservationId());
			((Stage) cancelButton.getScene().getWindow()).close();
		}
		else
			alertWindow(AlertType.INFORMATION, "Add Service", "Please select a service", "", null, null);
	}
	
	@FXML
	public void cancelClicked(){
		((Stage) cancelButton.getScene().getWindow()).close();
	}

}
