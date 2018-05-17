package model.shared.filters.customersFilters;

import java.util.ArrayList;

import model.shared.Customer;

public class NameCustomersFilter implements CustomersFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String [] customerName;
	
	public NameCustomersFilter(String customerName) {
		this.customerName = customerName.split("\\s+");
	}
	
	@Override
	public ArrayList<Customer> applyCustomersFilter(ArrayList<Customer> customersList) {
		for (int i=0; i< customersList.size(); i++) {
			for (String name : customerName) {
				if(!customersList.get(i).getName().toLowerCase().contains(name.toLowerCase())) {
					customersList.remove(i);
					i--;
					break;
				}
			}
		}
		return customersList;
	}

}
