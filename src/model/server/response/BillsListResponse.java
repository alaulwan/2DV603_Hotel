package model.server.response;

import java.util.ArrayList;
import model.server.HotelServer.SavingThread;
import model.shared.Bill;
import model.shared.Hotel;
import model.shared.filters.billsFilters.BillsFilter;

public class BillsListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Bill> billsList;

	public BillsListResponse(Hotel hotel, SavingThread savingThread, ArrayList<BillsFilter> billsFilterList) {
		super (hotel, savingThread);
		// Get all bills in the hotel
		billsList = new ArrayList<Bill>(hotel.getBillsList());
		
		// Apply the received-filters from the client on the bills-list
		if (billsFilterList != null) {
			for (BillsFilter filter : billsFilterList) {
				filter.applyBillsFilter(billsList);
			}
		}
		super.object = billsList;
	}
}
