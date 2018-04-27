package model.server.response;

import java.util.ArrayList;

import model.server.HotelServer.SavingThread;
import model.shared.Customer;
import model.shared.Hotel;
import model.shared.filters.customersFilters.CustomersFilter;

public class CustomersListResponse extends Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Customer> customersList;

	public CustomersListResponse(Hotel hotel, SavingThread savingThread, ArrayList<CustomersFilter> customersFilterList) {
		super (hotel, savingThread);
		customersList = new ArrayList<Customer>(hotel.getCustomersList());
		if (customersFilterList != null) {
			for (CustomersFilter filter : customersFilterList) {
				filter.applyCustomersFilter(customersList);
			}
		}
		super.object = customersList;
	}
}
