import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.shared.Room.RoomLocation;
import view.client.RootLayoutController;

public class HotelMain extends Application{
	
	public RoomLocation location ;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Linnaeus Hotel");

		ChoiceDialog<RoomLocation> choiceDialog = new ChoiceDialog<RoomLocation>(RoomLocation.VAXJO, RoomLocation.values());
		choiceDialog.setHeaderText("Choose Your Location!");
		choiceDialog.initModality(Modality.APPLICATION_MODAL);

		Optional<RoomLocation> result = choiceDialog.showAndWait();
		if (! result.isPresent()) {
			System.exit(0);
		} else {
			location = result.get() ;
		}
		
		RootLayoutController root = new RootLayoutController();
		
		
		Scene mainScene = new Scene(root.showWindow());
		primaryStage.setOnCloseRequest(onExitCloseEverything -> System.exit(0));
		primaryStage.setScene(mainScene);
		
		// Locking window size
//		primaryStage.setMinWidth(1280);
//		primaryStage.setMinHeight(600);
//		primaryStage.setMaxWidth(1280);
//		primaryStage.setMaxHeight(600);
        
		primaryStage.show();
		
	}

}
