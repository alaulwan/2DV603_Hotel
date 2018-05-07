package controller.client;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.client.ServerAPI;

public class Controller {
	protected URL fxmlURL;
	protected RootLayoutController rootLayoutController;
	public ServerAPI serverAPI;

	public Parent getParentPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);
		loader.setLocation(fxmlURL);

		Parent rootLayout = (Parent) loader.load();
		return rootLayout;
	}

	/**
	 * Warning popUp
	 * 
	 * @param type
	 *            of alert
	 * @param Title
	 * @param headText
	 * @param contentText
	 * @return
	 */
	public Optional<ButtonType> alertWindow(AlertType type, String title, String headText, String contentText, String imageName, String iconName) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		

		if (imageName !=null && !imageName.isEmpty()) {
			URL imgUrl = getClass().getResource("/view/client/icons/"+imageName);
			imageName = imgUrl.toString();
			setAlertImage(alert, imageName);
		}
		if (iconName !=null && !iconName.isEmpty()) {
			URL iconUrl = getClass().getResource("/view/client/icons/"+iconName);
			iconName = iconUrl.toString();
			setIcon(alert, iconName);
		}

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			return result;
		}
		return null;
	}

	public void setIcon(Alert alert, String url) {
		Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(url));

	}

	public void setAlertImage(Alert alert, String url) {
		ImageView iv = new ImageView(new Image(url));
		iv.setFitWidth(60);
		iv.setFitHeight(60);
		alert.setGraphic(iv);
	}

}
