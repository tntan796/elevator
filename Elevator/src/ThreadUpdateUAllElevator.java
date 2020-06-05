import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadUpdateUAllElevator implements Runnable{
	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	List<Elevator> elevators = new ArrayList<Elevator>();
	int totalFloor = 0;
	
	@Override
    public void run() {	
		for(int i=0; i< elevators.size(); i++) {
			ThreadRunOneElevator temp = new ThreadRunOneElevator();
			temp.setTotalFloor(totalFloor);
			temp.setElevator(elevators.get(i));
			Thread threadTemp = new Thread(temp);
			threadTemp.start();
		}
    }   
}
