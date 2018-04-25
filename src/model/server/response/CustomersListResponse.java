package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer;
import model.shared.Customer;
import model.shared.filters.customersFilters.CustomersFilter;

public class CustomersListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Customer> customersList;

	public CustomersListResponse(ArrayList<CustomersFilter> customersFilterList) {
		customersList = new ArrayList<Customer>(HotelServer.hotel.getCustomersList());
		if (customersFilterList != null) {
			for (CustomersFilter filter : customersFilterList) {
				filter.applyCustomersFilter(customersList);
			}
		}
		super.object = customersList;
	}
}
