package view.client;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.shared.Bed;
import model.shared.Room;
import model.shared.Room.RoomLocation;
import model.shared.Room.RoomSize;
import model.shared.Suite;

public class RoomNode extends StackPane {

	Room room = new Room();
	private Tooltip tooltip = new Tooltip();
	ReservationsListController reservationsListController = new ReservationsListController();

	public RoomNode(Room room) {
		this.room = room;
		setRoomLayout();

	}

	private void setRoomLayout() {

		Text text;
		Rectangle rectangle;
		String roomType = (room.isSuite() == true ? "Suite " : "Room ");

		if (room.isAvailable()) {
			rectangle = new Rectangle(125, 125, Color.GREEN);
			StackPane.setAlignment(rectangle, Pos.CENTER);
			text = new Text("\n Available \n \n  " + roomType + room.getRoomNum());

		} else {
			rectangle = new Rectangle(125, 125, Color.RED);
			StackPane.setAlignment(rectangle, Pos.CENTER);

			text = new Text("\n Occupied \n \n  " + roomType + room.getRoomNum());
		}

		Font font = new Font("SansSerif", 16);
		text.setFont(font);
		text.setFill(Color.BLACK);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		this.getChildren().addAll(rectangle, text);

		setTooltip();
		Tooltip.install(this, tooltip);

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					try {
						Scene mainScene = new Scene(reservationsListController.getParentPane());
						Stage stage = new Stage();
						stage.setScene(mainScene);
						stage.setTitle("Reservations List");
						stage.showAndWait();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	private Tooltip setTooltip() {
		String inf_airCon = "Air Condition: " + (room.isAirCon() == true ? "Yes" : "No");
		String inf_balcony = "Balcony: " + (room.isBalcony() == true ? "Yes" : "No");
		String inf_view = "View: " + (room.isView() == true ? "Yes" : "No");
		String inf_smoking = "Smoking: " + (room.isSmoking() == true ? "Yes" : "No");

		String roomType = (room.isSuite() == true ? "\nSuite " : "\nRoom ");
		String suitRoomNum = "";
		
		if (room.isSuite())
			suitRoomNum = "\nSuite Rooms No.: " + ((Suite) room).getNumberOfRooms() ; 
		
		String inf_string = roomType + " Number: " + room.getRoomNum()  + suitRoomNum + roomType + " Size: "
				+ room.getRoomSize().toString() + "\nBeds No.: " + room.getBedsNum() + "\nMax Guests No.: "
				+ room.getMaxGuestCapacity() + "\nQuality Level: " + room.getQualityLev() + "\n" + inf_smoking + "\n"
				+ inf_airCon + "\n" + inf_view + "\n" + inf_balcony;
		
			

		tooltip.setWrapText(true);
		tooltip.setText(inf_string);
		tooltip.setMaxWidth(300);

		return tooltip;

	}

}
