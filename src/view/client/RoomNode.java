package view.client;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.shared.Room;
import model.shared.Suite;

public class RoomNode extends StackPane {

	Room room = new Room();
	Color color = Color.LIGHTGREEN;
	private Tooltip tooltip = new Tooltip();
	public Rectangle rectangle;

	public RoomNode(Room room) {
		this.room = room;
		setRoomLayout();

	}

	private void setRoomLayout() {

		Text text;
		String roomType = (room.isSuite() == true ? "Suite " : "Room ");

		switch (room.getRoomStatus()) {
		case AVAILABLE: 
			rectangle = new Rectangle(125, 125, Color.LIGHTGREEN);
			break;
		case CHEKIN_TODAY:
			rectangle = new Rectangle(125, 125, Color.ORANGE);
			break;
		case CHECKOUT_TODAY:
			rectangle = new Rectangle(125, 125, Color.SKYBLUE);
			break;
		default:
			rectangle = new Rectangle(125, 125, Color.ORANGERED);
		}
		
		StackPane.setAlignment(rectangle, Pos.CENTER);
		text = new Text("\n " + room.getRoomStatus() + " \n \n  " + roomType + room.getRoomNum());

		Font font = new Font("SansSerif", 16);
		text.setFont(font);
		text.setFill(Color.BLACK);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		this.getChildren().addAll(rectangle, text);

		setTooltip();
		Tooltip.install(this, tooltip);

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
