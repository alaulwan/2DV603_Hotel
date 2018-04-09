package model.shared.filters.billsFilters;

import java.util.ArrayList;

import model.shared.Bill;

public class CustomerIdBillsFilter implements BillsFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customerId;
	
	public CustomerIdBillsFilter(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public ArrayList<Bill> applyBillsFilter(ArrayList<Bill> billsList) {
		for (int i=0; i< billsList.size(); i++) {
			if(billsList.get(i).getCustomerId() != customerId) {
				billsList.remove(i);
				i--;
			}
		}
		return billsList;
	}

}
