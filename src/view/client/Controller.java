package view.client;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
    public Optional<ButtonType> alertWindow(AlertType type, String title, String headText, String contentText) {
    	Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		
		if(headText.equals("CheckIn Successed")) {
			setAlertImage(alert , "file:res/icons/ok.png");
			setIcon(alert, "file:res/icons/check-in.png");
		}
		else if(headText.equals("CheckIn Failed")) {
			setAlertImage(alert , "file:res/icons/error.png");
			setIcon(alert, "file:res/icons/check-in.png");
		}
		else if(headText.equals("CheckOut Successed")) {
			setAlertImage(alert , "file:res/icons/ok.png");
			setIcon(alert, "file:res/icons/check-out.png");
		}
		else if(headText.equals("CheckOut Failed")) {
			setAlertImage(alert , "file:res/icons/error.png");
			setIcon(alert, "file:res/icons/check-out.png");
		}
		else if(headText.equals("Delete Customer Successed")) {
			setAlertImage(alert , "file:res/icons/ok.png");
			setIcon(alert, "file:res/icons/customer.png");
		}
		else if(headText.equals("Delete Customer Failed")) {
			setAlertImage(alert , "file:res/icons/error.png");
			setIcon(alert, "file:res/icons/customer.png");
		}
		else if(title.equals("Pay Bill")) {
			setAlertImage(alert , "file:res/icons/question.png");
			setIcon(alert, "file:res/icons/bill.png");
		}
		else if(headText.equals("Cancel Reservation Successed")) {
			setAlertImage(alert , "file:res/icons/ok.png");
			setIcon(alert, "file:res/icons/reservations.png");
		}
		else if(headText.equals("Cancel Reservation Failed")) {
			setAlertImage(alert , "file:res/icons/error.png");
			setIcon(alert, "file:res/icons/reservations.png");
		}
		else if (type == AlertType.CONFIRMATION && title.equals("Cancel Reservation")) {
			setAlertImage(alert , "file:res/icons/warning.png");
			setIcon(alert, "file:res/icons/reservations.png");
		}
		else if (type == AlertType.CONFIRMATION && title.equals("Delete Reservation")) {
			setAlertImage(alert , "file:res/icons/warning.png");
			setIcon(alert, "file:res/icons/reservations.png");
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			return result;
		}
		return null;
    }
    
    public void setIcon (Alert alert , String url) {
		Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(new Image(url));

    }

    public void setAlertImage (Alert alert , String url) {
    	ImageView iv = new ImageView(new Image(url));
		iv.setFitWidth(60);
		iv.setFitHeight(60);
		alert.setGraphic(iv);
    }
    
}
