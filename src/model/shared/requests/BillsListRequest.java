package model.shared.requests;

import java.util.ArrayList;

import model.shared.filters.billsFilters.BillsFilter;
import model.shared.requests.Request.RequestType;

public class BillsListRequest extends Request {
	private static final long serialVersionUID = 1L;
	public ArrayList <BillsFilter> billsFilterList;
	
	public BillsListRequest(ArrayList <BillsFilter> billsFilterList) {
		this.billsFilterList = billsFilterList;
		super.requestType = RequestType.GET_BILLS;
	}

}
