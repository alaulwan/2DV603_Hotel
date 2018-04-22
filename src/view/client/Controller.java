package view.client;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	protected String fxmlPath;
	protected RootLayoutController rootLayoutController;
	
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
     */
    public void alertWindow(AlertType type, String Title, String headText, String contentText) {
    	Alert alert = new Alert(type);
		alert.setTitle(Title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		alert.showAndWait();
    }

}
