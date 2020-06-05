
public class ThreadRunOneElevator implements Runnable{
	Elevator elevator = new Elevator();
	int totalFloor = 0;
	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
    public void run() {
       try {
		this.elevator.updatePosition(totalFloor);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Loi nay trong class ThreadRunOneElevator");
	}
    }   
}
