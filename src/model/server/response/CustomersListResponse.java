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
		// Get all customers in the hotel
		customersList = new ArrayList<Customer>(hotel.getCustomersList());
		
		// Apply the received-filters from the client on the customers-list
		if (customersFilterList != null) {
			for (CustomersFilter filter : customersFilterList) {
				filter.applyCustomersFilter(customersList);
			}
		}
		super.object = customersList;
	}
}
