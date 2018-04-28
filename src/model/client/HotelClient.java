package model.client;

import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.shared.Room.RoomLocation;
import view.client.RootLayoutController;

public class HotelClient extends Application{
	public ServerAPI serverAPI;
	public HotelClient() {

	}

	public void startClient(String[] args) {
		launch(args);		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<String> argsList = getParameters().getRaw();
		if(argsList.size()>0 && isCurrectIP(argsList.get(0)))
			serverAPI = new ServerAPI(argsList.get(0));
		else
			serverAPI = new ServerAPI("127.0.0.1");
		
		primaryStage.setTitle("Linnaeus Hotel");

		ChoiceDialog<RoomLocation> choiceDialog = new ChoiceDialog<RoomLocation>(RoomLocation.VAXJO,
				RoomLocation.values());
		choiceDialog.setHeaderText("Welcome to Linnause Hotel");
		choiceDialog.setTitle("Choose Your Location!");
		choiceDialog.setContentText("Choose Your Location:");
		ImageView iv = new ImageView(new Image("file:res/icons/hotel.png"));
		iv.setFitWidth(140);
		iv.setFitHeight(160);
		choiceDialog.setGraphic(iv);
		
		Stage dialogStage = (Stage) choiceDialog.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image("file:res/icons/location.png"));

		Optional<RoomLocation> result = choiceDialog.showAndWait();
		if (!result.isPresent()) {
			System.exit(0);
		} else {
			serverAPI.location = result.get();
		}

		
		RootLayoutController root = new RootLayoutController(serverAPI);
		Scene mainScene = new Scene(root.getParentPane());
		primaryStage.setOnCloseRequest(onExitCloseEverything -> System.exit(0));
		primaryStage.setScene(mainScene);
		primaryStage.getIcons().add(new Image("file:res/icons/hotel1.png"));

		// Locking window size
		// primaryStage.setMinWidth(1280);
		// primaryStage.setMinHeight(600);
		// primaryStage.setMaxWidth(1280);
		// primaryStage.setMaxHeight(600);

		primaryStage.show();

	}

	private boolean isCurrectIP(String IP) {
		String[] IpParts = IP.split("\\.");
		if (IpParts.length == 4) {
			for (int i = 0; i < 4; i++) {
				try {
					int intIpParts = Integer.parseInt(IpParts[i]);
					if (intIpParts < 0 || intIpParts > 255) {
						return false;
					}

				} catch (NumberFormatException e) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}
}