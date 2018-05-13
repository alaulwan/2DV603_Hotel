package model.shared.filters.customersFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Customer;

//This filter to search for customers
public interface CustomersFilter extends Serializable {
	// When apply this method on costumers-list, it will remove all costumers that do not
	// much the filter from the given costumers-list
	public ArrayList<Customer> applyCustomersFilter (ArrayList<Customer> customersList);
}
