import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Test {	
	
	List<Person> mockPersons = new ArrayList<Person>();
	List<Elevator> mockElevators = new ArrayList<Elevator>();
	
	public static void main(String [] args) throws InterruptedException {
		int totalElevator = 0;
		int totalFloor = 0;
		int maxPerson = 0;
		List<Person> persons = new ArrayList<Person>();
		List<Elevator> elevators = new ArrayList<Elevator>();
		List<Person> waitingPerson = new ArrayList<Person>();
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
			elevator.setDirection(DIRECTION.STOP);
			elevator.setId(i);
			elevator.setMaxPerson(maxPerson);
			elevators.add(elevator);
		}

//		
//		while(true) {
//			Person per = new Person();
//			System.out.print("Nhập tầng đi:");
//			per.setFloorFrom(sc.nextInt());
//			System.out.print("Nhập số đến:");
//			per.setFloorTo(sc.nextInt());
//			persons.add(per);
//			Thread.sleep(5000);
//		}
		
//		mockDatas mock = new mockDatas();
//		elevators = mock.mockElevators;
//		for(int i= 0; i< elevators.size(); i++) {
//			System.out.println(elevators.get(i).toString());
//			System.out.println();
//		}
//		System.out.println("_____________________________________________________________________");
		
//		Person person10 = new Person();
//		person10.setId("10");
//		person10.setFloorFrom(2);
//		person10.setFloorTo(1);
//		person10.setDirection(DIRECTION.DOWN);
//		
//		
//		
//		Person person11 = new Person();
//		person11.setId("11");
//		person11.setFloorFrom(4);
//		person11.setFloorTo(5);
//		person11.setDirection(DIRECTION.UP);

			
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
			System.out.println("_________________________Update__________________________________________________");
//			helper.UpdateAllElevator(elevators, 5);
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
			Thread.sleep(3000);
		}
	}
}
