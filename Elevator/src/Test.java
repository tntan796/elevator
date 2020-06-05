import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Test {	
	static List<Person> persons = new ArrayList<Person>();
	static List<Elevator> elevators = new ArrayList<Elevator>();
	static List<Person> waitingPerson = new ArrayList<Person>();
	
	public static void main(String [] args) throws InterruptedException {
		int totalElevator = 0;
		int totalFloor = 0;
		int maxPerson = 0;		
		Scanner sc = new Scanner(System.in);
		System.out.print("Nhập số thang máy: ");
		totalElevator = sc.nextInt();

		System.out.print("Nhập số tầng của tòa nhà:");
		totalFloor = sc.nextInt();
		
		System.out.print("Nhập sức chứa của thang máy:");
		maxPerson = sc.nextInt();
		
		// Khởi tạo thang máy
		for(int i = 0; i < totalElevator; i++) {
			Elevator elevator = new Elevator();
			elevator.setDirection(DIRECTION.UP);
			elevator.setId(i);
			elevator.setMaxPerson(maxPerson);
			elevators.add(elevator);
		}
			
		for(int i= 0; i< elevators.size(); i++) {
			System.out.println(elevators.get(i).toString());
		}

		// Luồng chạy thang máy. Bên trong sẽ chứa vòng lặp vô hạn để có thể nhận dữ liệu nhập bất kì lúc nào
		ThreadPersonPressButton t2 = new ThreadPersonPressButton();	
		t2.setElevators(elevators);
		t2.setWaitingPerson(waitingPerson);
		Thread threadTemp2 = new Thread(t2);
		threadTemp2.start();
		
		
		
		while(true) {
			System.out.println();
			System.out.println("_________________________Update__________________________________________________");
			//Xử lí nhưng trường hợp người bị đợi thang
			System.out.println("Danh sách người chưa có thang: " + GetListWaittingPerson());
			
			//Luồng in và cập nhật trong thái liên tục sau mỗi giây
			ThreadUpdateUAllElevator t1 = new ThreadUpdateUAllElevator();	
			t1.setElevators(elevators);
			t1.setTotalFloor(totalFloor);
			Thread threadTemp1 = new Thread(t1);
			threadTemp1.start();	
			
			System.out.println("Tình trang thang hiện tại");
			for(int i= 0; i< elevators.size(); i++) {
				System.out.println(elevators.get(i).toString());
				
			}				
			Thread.sleep(5000);
		}
	}
	
	public static String GetListWaittingPerson() {
		String result = "";
		for (int i=0; i< waitingPerson.size(); i++) {
			result += waitingPerson.get(i).getId() + " - " + waitingPerson.get(i).getFloorFrom() + " - " + waitingPerson.get(i).getFloorTo() + " - " + waitingPerson.get(i).getDirection() + " * ";
		}
		return result;
	}
}
