package model.shared;

import java.io.Serializable;

public class Service implements Serializable{
	private static final long serialVersionUID = 1L;
	public enum ServiceType { BREAKFAST, LUNCH, DINNER, SOFT_DRINK, RESERVATION, GYM, PARKING }
	
	private static int count = 0;
	private int serviceId;
	private ServiceType serviceType;
	private float price;
	private int piecesNumber;
	private float discount;
	private String descraption;
	
	public Service() {
		
	}
	
	public Service(ServiceType serviceType, int piecesNumber, float discount, String descraption ) {
		this.setServiceId(++count);
		this.setServiceType(serviceType);
		this.setPrice(serviceType);
		this.setPiecesNumber(piecesNumber);
		this.setDiscount(discount);
		this.setDescraption(descraption);
	}
	
	public Service(ServiceType serviceType, float price, int piecesNumber, float discount, String descraption ) {
		this.setServiceId(++count);
		this.setServiceType(serviceType);
		this.setPrice(price);
		this.setPiecesNumber(piecesNumber);
		this.setDiscount(discount);
		this.setDescraption(descraption);
	}
	
	

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Service.count = count;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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
	
	public void setPrice(ServiceType type) {
		if (type == ServiceType.BREAKFAST)
			this.price = 10 ;
		else if (type == ServiceType.LUNCH)
			this.price = 15 ;
		else if (type == ServiceType.DINNER)
			this.price = 10 ;
		else if (type == ServiceType.SOFT_DRINK)
			this.price = 3 ;
		else if (type == ServiceType.GYM)
			this.price = 7 ;
		else if (type == ServiceType.PARKING)
			this.price = 10 ;
			
	}

	public int getPiecesNumber() {
		return piecesNumber;
	}



	public void setPiecesNumber(int piecesNumber) {
		this.piecesNumber = piecesNumber;
	}



	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		if (discount < 0) {
			this.discount = 0;
		}
		else if (discount > 100) {
			this.discount = 100;
		}
		else {
			this.discount = discount;
		}
		
	}

	public String getDescraption() {
		return descraption;
	}

	public void setDescraption(String descraption) {
		this.descraption = descraption;
		if (this.descraption == null)
			this.descraption="";
	}
	
	public float getTotalPrice() {
		return price * piecesNumber * (1-discount);
	}

}
