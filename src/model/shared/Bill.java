package model.shared;

import java.io.Serializable;
import java.util.ArrayList;

import model.shared.Service.ServiceType;

public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;
	private int billId;
	private int customerId;
	private String customerName;

	public enum PayStatus {
		PAID, UNPAID
	}

	private PayStatus payStatus;
	private ArrayList<Service> serviceList = new ArrayList<Service>();

	public Bill() {

	}

	public Bill(int billId, int customerId, String customerName, float price, float discount, String descraption) {
		Service reserveService = new Service(ServiceType.RESERVATION, price, 1, discount, descraption);
		serviceList.add(reserveService);
		this.setBillId(billId);
		this.customerId = customerId;
		this.customerName = customerName;
		this.payStatus = PayStatus.UNPAID;
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

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public ArrayList<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public float getTotalPrice() {
		float totalPrice = 0;
		for (Service service : serviceList) {
			totalPrice += service.getTotalPrice();
		}
		return totalPrice;
	}

	public void addService(ServiceType serviceType, int piecesNumber, float discount, String descraption ) {

		Service service = new Service(serviceType, piecesNumber, discount, descraption);
		this.serviceList.add(service);
	}

	public void addService(ServiceType serviceType, float price, int piecesNumber, float discount, String descraption) {

		Service service = new Service(serviceType, price, piecesNumber, discount, descraption);
		this.serviceList.add(service);
	}

}
