package view.client;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.server.HotelServer;
import model.shared.Hotel;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.control.Tab;

public class RootLayoutController {
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab availableRoomsTab;
	@FXML
	private Tab reservationsListTab;
	@FXML
	private Tab customerListTab;
	@FXML
	private HBox buttonBox;
	@FXML
	private Button newReservationButton;
	@FXML
	private Button checkinButton;
	@FXML
	private Button exitButton;

	private final String ROOT_LAYOUT = "res/view/RootLayout.fxml";
	private AvailableRoomController availableRoomController = new AvailableRoomController();
	private ReservationsListController reservationsListController = new ReservationsListController();
	private CustomerListController customerListController = new CustomerListController();
	private SearchRoomController searchRoomController = new SearchRoomController();
	

	@FXML
	public void initialize() throws IOException {
		availableRoomsTab.setContent(availableRoomController.getParentPane());
		reservationsListTab.setContent(reservationsListController.getParentPane());
		customerListTab.setContent(customerListController.getParentPane());

		newReservationButton.setOnMouseClicked(createEvent -> {
			try {
				Scene mainScene = new Scene(searchRoomController.getParentPane());
				Stage stage = new Stage();
				stage.setScene(mainScene);
				stage.setTitle("Create a new timeline");
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		exitButton.setOnAction(exitAppEvent -> {
			Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
			closeConfirmation.setHeaderText("Confirm Exit");
			closeConfirmation.initModality(Modality.APPLICATION_MODAL);
			
			Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
			if (ButtonType.OK.equals(closeResponse.get())) {
				System.exit(0);

			} 
		});
		
//		Hotel hotel = new Hotel();
//		hotel.defaultHotel();
	
	}

	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(ROOT_LAYOUT).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

	private Parent loadLayout(String url) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(new File(url).toURI().toURL());
		Parent root = (Parent) loader.load();
		return root;
	}
}
