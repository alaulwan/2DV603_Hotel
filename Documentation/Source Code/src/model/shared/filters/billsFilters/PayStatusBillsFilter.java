package model.shared.filters.billsFilters;

import java.util.ArrayList;

import model.shared.Bill;
import model.shared.Bill.PayStatus;

public class PayStatusBillsFilter implements BillsFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PayStatus payStatus;

	public PayStatusBillsFilter () {
	}
	
	public PayStatusBillsFilter (PayStatus payStatus) {
		this.payStatus = payStatus;
	}
	
	@Override
	public ArrayList<Bill> applyBillsFilter(ArrayList<Bill> billsList) {
		if (payStatus!=null) {
			for (int i=0; i< billsList.size(); i++) {
				if (billsList.get(i).getPayStatus() != payStatus) {
					billsList.remove(i);
					i--;
				}
			}
		}
		
		return billsList;
	}

}
