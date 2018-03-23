package model.shared;

import java.util.ArrayList;

public class Bill {
	private static int count = 0;
	private int billId;
	private ArrayList <Service> serviceList = new ArrayList<Service>();

	public Bill() {
		
	}
	
	public static int getCount() {
		return count;
	}
	
	public static void setCount(int count) {
		Bill.count = count;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public ArrayList  <Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList <Service> serviceList) {
		this.serviceList = serviceList;
	}

}
