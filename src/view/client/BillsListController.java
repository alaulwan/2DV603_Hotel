package view.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Optional;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.shared.Bill;
import model.shared.Bill.PayStatus;
import model.shared.filters.billsFilters.BillsFilter;
import model.shared.filters.billsFilters.CustomerNameBillsFilter;
import model.shared.filters.billsFilters.PayStatusBillsFilter;
import model.shared.Service;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

public class BillsListController extends Controller {
	@FXML
	private TableView<Bill> billsTable;
	@FXML
	private TableColumn<Bill, Integer> idCol;
	@FXML
	private TableColumn<Bill, Integer> customerIdCol;
	@FXML
	private TableColumn<Bill, String> customerNameCol;
	@FXML
	private TableColumn<Bill, Float> totlaPriceCol;
	@FXML
	private TableColumn<Bill, PayStatus> statusCol;
	@FXML
	private TextField searchName;
	@FXML
	private CheckBox viewAllBox;

	private final String BILLS_LIST_LAYOUT = "res/view/BillsList.fxml";
	public ArrayList<Bill> billsArray;
	public Bill selectedBill;

	public BillsListController(RootLayoutController rootLayoutController) {
		super.fxmlPath = BILLS_LIST_LAYOUT;
		super.rootLayoutController = rootLayoutController;
		super.serverAPI = rootLayoutController.serverAPI;
	}

	@FXML
	public void initialize() {
		setData();
		setContextMenu();
	}

	private void setData() {
		if (billsArray != null) {
			ObservableList<Bill> data = FXCollections.observableList(billsArray);
			billsTable.setItems(data);
			idCol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("billId"));
			customerIdCol.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("customerId"));
			customerNameCol.setCellValueFactory(new PropertyValueFactory<Bill, String>("customerName"));
			totlaPriceCol.setCellValueFactory(new PropertyValueFactory<Bill, Float>("totalPrice"));
			statusCol.setCellValueFactory(new PropertyValueFactory<Bill, PayStatus>("payStatus"));
		}
	}

	// Event Listener on TextField[#searchName].onKeyReleased
	@FXML
	public void apllyAllChosenFilters() {
		ArrayList<Bill> billsArray = new ArrayList<Bill>(this.billsArray);
		applyCustomerNameFilter(billsArray);
	}

	// Event Listener on CheckBox[#viewAllBox].onAction
	@FXML
	public void viewPaidChecked() {
		ArrayList<BillsFilter> billsFilterList = new ArrayList<BillsFilter>();
		PayStatusBillsFilter payStatusBillsFilter;
		if (viewAllBox.isSelected()) {
			payStatusBillsFilter = new PayStatusBillsFilter();
		} else {
			payStatusBillsFilter = new PayStatusBillsFilter(PayStatus.UNPAID);
		}
		billsFilterList.add(payStatusBillsFilter);
		this.billsArray = serverAPI.getBillsList(billsFilterList);
		apllyAllChosenFilters();
	}

	public void applyCustomerNameFilter(ArrayList<Bill> billsArray) {
		String customerName = searchName.getText();
		CustomerNameBillsFilter customerNameBillsFilter = new CustomerNameBillsFilter(customerName);
		customerNameBillsFilter.applyBillsFilter(billsArray);
		ObservableList<Bill> data = FXCollections.observableList(billsArray);
		billsTable.setItems(data);
	}

	protected void markBillAsPaid() {
		Optional<ButtonType> result = alertWindow(AlertType.CONFIRMATION, "Mark bill as paid...",
				"Are you sure you want to pay this bill", "");
		if (result.isPresent() && result.get().equals(ButtonType.OK)) {
			selectedBill.setPayStatus(PayStatus.PAID);
			serverAPI.post(selectedBill, selectedBill.getBillId());
			if (billsTable != null)
				billsTable.refresh();
		}
	}

	@SuppressWarnings("unused")
	public void billToPdf() {
		try {
			String tempDir = System.getProperty("java.io.tmpdir");
			File file = new File(tempDir + "bill.pdf");

			OutputStream fileOutStream = new FileOutputStream(file);
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, fileOutStream);

			document.open();
			Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
			Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
			Chunk chunk = new Chunk("Bill ID: " + selectedBill.getBillId(), chapterFont);
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			Section section1 = chapter.addSection("Customer ID: " + selectedBill.getCustomerId(), 0);
			Section subSec = section1.addSection("Customer Name: " + selectedBill.getCustomerName(), 0);
			Section subSec2 = section1.addSection("Pay Status: " + selectedBill.getPayStatus(), 0);

			chapter.add(new Paragraph("Services:\n\n", paragraphFont));

			PdfPTable table = new PdfPTable(4);
			table.addCell("Descraption");
			table.addCell("Pieces Number");
			table.addCell("Price/Unit");
			table.addCell("Total Price");
			float totalPrice = 0;
			for (Service service : selectedBill.getServiceList()) {
				table.addCell(service.getDescraption());
				table.addCell(service.getPiecesNumber() + "");
				table.addCell(service.getPrice() + "");
				table.addCell(service.getTotalPrice() + "");
				totalPrice += service.getTotalPrice();
			}

			chapter.add(table);
			Section section3 = chapter.addSection("Total Price: " + totalPrice, 0);

			document.add(chapter);
			// document.add(table);

			document.close();

			Desktop dt = Desktop.getDesktop();
			dt.open(file);
		} catch (Exception e) {

		}

	}

	private void setContextMenu() {
		billsTable.setRowFactory(new Callback<TableView<Bill>, TableRow<Bill>>() {
			@Override
			public TableRow<Bill> call(TableView<Bill> tableView) {
				final TableRow<Bill> row = new TableRow<>();

				final ContextMenu menu = new ContextMenu();
				MenuItem mi1 = new MenuItem("View Bill");
				mi1.setOnAction((ActionEvent event) -> {
					billToPdf();
				});

				MenuItem mi2 = new MenuItem("Mark as Paid");
				mi2.setOnAction((ActionEvent event) -> {
					markBillAsPaid();
				});

				menu.getItems().addAll(mi1, mi2);
				row.setOnMousePressed((MouseEvent t) -> {
					selectedBill = billsTable.getSelectionModel().getSelectedItem();
					if (selectedBill != null && selectedBill.getPayStatus().equals(PayStatus.PAID))
						mi2.setDisable(true);
				});
				// only display context menu for non-null items:
				row.contextMenuProperty().bind(
						Bindings.when(Bindings.isNotNull(row.itemProperty())).then(menu).otherwise((ContextMenu) null));
				return row;
			}
		});
	}
}
