package model.shared.filters.billsFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Bill;

// This filter to search for bills
public interface BillsFilter extends Serializable {
	// When apply this method on bills-list, it will remove all bills that do not
	// much the filter from the given bills-list
	public ArrayList<Bill> applyBillsFilter(ArrayList<Bill> billsList);
}
