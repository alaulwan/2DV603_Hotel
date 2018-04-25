package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer;
import model.shared.Bill;
import model.shared.filters.billsFilters.BillsFilter;

public class BillsListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Bill> billsList;

	public BillsListResponse(ArrayList<BillsFilter> billsFilterList) {
		billsList = new ArrayList<Bill>(HotelServer.hotel.getBillsList());
		if (billsFilterList != null) {
			for (BillsFilter filter : billsFilterList) {
				filter.applyBillsFilter(billsList);
			}
		}
		super.object = billsList;
	}
}
