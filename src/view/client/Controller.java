package view.client;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.client.ServerAPI;

public class Controller {
	protected String fxmlPath;
	protected RootLayoutController rootLayoutController;
	public ServerAPI serverAPI;
	
	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(new File(fxmlPath).toURI().toURL());
		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}
	
	/**
     * Warning popUp 
     * @param type of alert
     * @param Title 
     * @param headText
     * @param contentText
	 * @return 
     */
    public Optional<ButtonType> alertWindow(AlertType type, String Title, String headText, String contentText) {
    	Alert alert = new Alert(type);
		alert.setTitle(Title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			return result;
		}
		return null;
    }

}
