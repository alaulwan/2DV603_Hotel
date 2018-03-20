package model.shared;

public class Bed {

	public enum Size { SINGLE, DOUBLE }

	private Size size ;
	
	public Bed () {
		
	}
	
	public Bed (Size size) {
		this.setSize(size);
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
}
