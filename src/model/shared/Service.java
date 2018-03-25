package model.shared;

public class Service {
	public enum ServiceType { RESERVATION, ITEM, CLEANING }
	
	private static int count = 0;
	private int serviceId;
	private ServiceType serviceType;
	private float price;
	private int piecesNumber;
	private float discount;
	private String descraption;
	
	public Service() {
		
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
		return price * piecesNumber * discount;
	}

}
