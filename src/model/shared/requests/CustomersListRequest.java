package model.shared.requests;

import java.util.ArrayList;

import model.shared.filters.customersFilters.CustomersFilter;

public class CustomersListRequest extends Request {
	private static final long serialVersionUID = 1L;
	public ArrayList <CustomersFilter> customersFilterList;
	
	public CustomersListRequest(ArrayList <CustomersFilter> customersFilterList) {
		this.customersFilterList = customersFilterList;
	}

}