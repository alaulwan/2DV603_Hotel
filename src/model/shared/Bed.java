package model.shared;

public class Bed {

	public enum BedSize { SINGLE, DOUBLE }

	private BedSize bedSize ;
	
	public Bed () {
		
	}
	
	public Bed (BedSize bedSize) {
		this.setSize(bedSize);
	}

	public BedSize getSize() {
		return bedSize;
	}

	public void setSize(BedSize bedSize) {
		this.bedSize = bedSize;
	}
}
