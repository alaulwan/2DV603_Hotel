package controller.client;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.shared.Room;
import model.shared.Suite;

// This RoomNode is to represent the rooms in the main window and search room window 
public class RoomNode extends StackPane {

	Room room = new Room();
	Color color = Color.LIGHTGREEN;
	public Tooltip tooltip = new Tooltip();
	public Text text;
	public Rectangle rectangle;

	public RoomNode(Room room) {
		this.room = room;
		setRoomLayout();

	}
	private void setRoomLayout() {		
		String roomType = (room.isSuite() == true ? "Suite " : "Room ");
		String roomStatus ;

		switch (room.getRoomStatus()) {
		case AVAILABLE: 
			rectangle = new Rectangle(125, 125, Color.LIGHTGREEN);
			roomStatus = "\n Available  \n \n  ";
			break;
		case CHECKIN_TODAY:
			rectangle = new Rectangle(125, 125, Color.ORANGE);
			roomStatus = "\n Check-in Today \n \n        ";
			break;
		case CHECKOUT_TODAY:
			rectangle = new Rectangle(125, 125, Color.SKYBLUE);
			roomStatus = "\n Check-out Today  \n \n        ";
			break;
		case CHECK_OUT_IN:
			Stop[] stops = new Stop[] { new Stop(0, Color.SKYBLUE), new Stop(1, Color.ORANGE)};
	        LinearGradient linerColorPaint = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			rectangle = new Rectangle(125, 125, linerColorPaint);
			roomStatus = "\n Check-Out-In  \n \n        ";
			
			break;
		default:
			roomStatus = "\n Occupied  \n \n  ";
			rectangle = new Rectangle(125, 125, Color.ORANGERED);
		}
		
		
		StackPane.setAlignment(rectangle, Pos.CENTER);
		text = new Text(roomStatus + roomType + room.getRoomNum());

		Font font = new Font("SansSerif", 15);
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
		String roomType = (room.isSuite() == true ? "Suite " : "Room ");
		String suitRoomNum = "";
		if (room.isSuite())
			suitRoomNum = "\nSuite Rooms No.: " + ((Suite) room).getNumberOfRooms() ; 
		
		String inf_string = roomType + " Number: " + room.getRoomNum()  + suitRoomNum + "\n"+ roomType + " Size: "
				+ room.getRoomSize().toString() + "\nBeds No.: " + room.getBedsNum() + "\nMax Guests No.: "
				+ room.getMaxGuestCapacity() + "\nQuality Level: " + room.getQualityLev() + "\n" + inf_smoking + "\n"
				+ inf_airCon + "\n" + inf_view + "\n" + inf_balcony;

		tooltip.setWrapText(true);
		tooltip.setText(inf_string);
		tooltip.setMaxWidth(300);

		return tooltip;

	}

}
