package model.shared.filters.customersFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Customer;

//This filter to search for customers
public interface CustomersFilter extends Serializable {
	public ArrayList<Customer> applyCustomersFilter (ArrayList<Customer> customersList);
}
