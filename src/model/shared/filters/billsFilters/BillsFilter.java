package model.shared.filters.billsFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Bill;

// This filter to search for bills
public interface BillsFilter extends Serializable{
	public ArrayList<Bill> applyBillsFilter (ArrayList<Bill> billsList);
}
