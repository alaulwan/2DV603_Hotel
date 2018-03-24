package model.shared;

public class Bed {

	public enum BedSize { SINGLE, DOUBLE }
	private static int count = 0;
	private int bedId;
	
	private BedSize bedSize ;
	
	public Bed () {
		
	}
	
	public Bed (BedSize bedSize) {
		this.setBedId(++count);
		this.setBedSize(bedSize);
	}
	
	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Bed.count = count;
	}

	public int getBedId() {
		return bedId;
	}

	public void setBedId(int bedId) {
		this.bedId = bedId;
	}

	public BedSize getBedSize() {
		return bedSize;
	}

	public void setBedSize(BedSize bedSize) {
		this.bedSize = bedSize;
	}
}
