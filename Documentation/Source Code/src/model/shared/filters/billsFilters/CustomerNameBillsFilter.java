package model.shared.filters.billsFilters;

import java.util.ArrayList;

import model.shared.Bill;

public class CustomerNameBillsFilter implements BillsFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String [] customerName;
	
	public CustomerNameBillsFilter(String customerName) {
		this.customerName = customerName.split("\\s+");
	}
	
	@Override
	public ArrayList<Bill> applyBillsFilter(ArrayList<Bill> billsList) {
		for (int i=0; i< billsList.size(); i++) {
			for (String name : customerName) {
				if(!billsList.get(i).getCustomerName().toLowerCase().contains(name.toLowerCase())) {
					billsList.remove(i);
					i--;
					break;
				}
			}
		}
		return billsList;
	}

}
