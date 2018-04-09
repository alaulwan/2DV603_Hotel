package model.shared.filters.billsFilters;

import java.io.Serializable;
import java.util.ArrayList;
import model.shared.Bill;

public interface BillsFilter extends Serializable{
	public ArrayList<Bill> applyBillsFilter (ArrayList<Bill> billsList);
}
