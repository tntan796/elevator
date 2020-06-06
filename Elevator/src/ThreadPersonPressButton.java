import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.UUID;

public class ThreadPersonPressButton  implements Runnable{
	List<Elevator> elevators = new ArrayList<Elevator>();
	List<Person> waitingPerson = new ArrayList<Person>();
	int totalFloor;
	
	
	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	public List<Person> getWaitingPerson() {
		return waitingPerson;
	}

	public void setWaitingPerson(List<Person> waitingPerson) {
		this.waitingPerson = waitingPerson;
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}
	
	@Override
    public void run() {
		while(true) {
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Nhập tầng hiện tại và tầng muốn đến theo dạng: From To");
				String data = sc.nextLine();
				String[] floors = data.split(" ");
				System.out.println("Nhập tầng thành công! Giá trị vừa nhập là: " + data);
				// Tạo ra 1 đối tượng person tương ứng với giá trị vừa nhập tầng hiện tại và tầng đến.
				Person person = new Person();
				person.setId(helper.GenerateStringKey());
				person.setFloorFrom(Integer.parseInt(floors[0]));
				person.setFloorTo(Integer.parseInt(floors[1]));
				person.setDirection(person.getFloorFrom() - person.getFloorTo() > 0 ? DIRECTION.DOWN : DIRECTION.UP);			
				// Xét xem trong Queue còn người nào phù hợp không
				
				
				// Tìm ra thang máy phù hợp với người vừa nhập
				int t = helper.findElevator(elevators, person);
				if (t == -1) {
					System.out.println("Không con thang nào trống");
					// Lưu lại người chưa được vào thang để xử lí sau
					waitingPerson.add(person);
					List<Integer> test = new ArrayList<Integer>();
					for(int i=0; i<elevators.size(); i++) {
						test.add(helper.CountLength(person.getFloorFrom(), elevators.get(i).getPosition(), totalFloor,
								elevators.get(i).getPersons().size(), elevators.get(i).getDirection(), person.getDirection()));
					}
					
					// Thang được chọn là thang có độ dài ngắn nhất
					int minIndex = test.indexOf(Collections.min(test));
					System.out.println("Thang máy chờ: " + elevators.get(minIndex));
					
//					// Tạo ra một mảng clone của các thang máy. Mảng này sẽ sắp xếp theo ưu tiên: Vị trí của thang và người.
//					List<Elevator> cloneElevator = new ArrayList<Elevator>(elevators);
//					helper.OrderElevator(cloneElevator, DIRECTION.UP);
//					System.out.println("Thang máy sau khi xếp lại: " + cloneElevator.size());
//					for(int i=0; i< cloneElevator.size(); i++) {
//						System.out.println(cloneElevator.get(i).toString());
//					}
				} else {
					System.out.println("Thang được chọn là:" + elevators.get(t));
					// Kiểm tra xem nếu hướng của thang đang dừng thì cho hướng của thang hoạt động lại
//					if (elevators.get(t).getDirection() == DIRECTION.STOP) {
//						elevators.get(t).setDirection(person.getDirection());
//					}
					elevators.get(t).addPerson(person);			
					System.out.println("Thang sau khi thêm person là:\n" + elevators.get(t));
				}
			} catch (Exception e) {
				System.out.println("Nhập lại tầng đi bạn ơi");
			}
		}		
    }   
}
