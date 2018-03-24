package model.shared;

import java.util.ArrayList;

import model.shared.Service.ServiceType;

public class Bill {
	private int billId;
	private int customerId;
	private String customerName;
	private ArrayList <Service> serviceList = new ArrayList<Service>();

	public Bill(int billId,int customerId, String customerName, float price, int numberOfRooms, float discount, String descraption) {
		Service reserveService = new Service (ServiceType.RESERVATION, price, numberOfRooms, discount, descraption);
		serviceList.add(reserveService);
		this.setBillId(billId);
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
