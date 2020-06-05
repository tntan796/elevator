
public class Person {
	private String id = "123456789";
	private int floorFrom = 0;
	private int floorTo = 0;
	private DIRECTION direction = DIRECTION.UP;
	
	public DIRECTION getDirection() {
		return direction;
	}
	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}
	public int getFloorFrom() {
		return floorFrom;
	}
	public void setFloorFrom(int floorFrom) {
		this.floorFrom = floorFrom;
	}
	public int getFloorTo() {
		return floorTo;
	}
	public void setFloorTo(int floorTo) {
		this.floorTo = floorTo;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", floorFrom=" + floorFrom + ", floorTo=" + floorTo + ", direction=" + direction
				+ "]";
	}
	
}
