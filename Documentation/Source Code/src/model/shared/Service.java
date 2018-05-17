package model.shared;

import java.io.Serializable;

public class Service implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ServiceType {
		RESERVATION, RESTAURANT, GYM, PARKING
	}

	private ServiceType serviceType;
	private float price;
	private int piecesNumber;
	private String descraption;

	public Service() {

	}

	public Service(ServiceType serviceType, float price, int piecesNumber, String descraption) {
		this.setServiceType(serviceType);
		this.setPrice(price);
		this.setPiecesNumber(piecesNumber);
		this.setDescraption(descraption);
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPiecesNumber() {
		return piecesNumber;
	}

	public void setPiecesNumber(int piecesNumber) {
		this.piecesNumber = piecesNumber;
	}

	public String getDescraption() {
		return descraption;
	}

	public void setDescraption(String descraption) {
		this.descraption = descraption;
		if (this.descraption == null)
			this.descraption = "";
	}

	public float getTotalPrice() {
		return price * piecesNumber;
	}
	
	@Override
	public String toString() {
		return this.descraption;
	}

}
