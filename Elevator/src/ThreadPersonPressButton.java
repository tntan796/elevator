import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.UUID;

public class ThreadPersonPressButton  implements Runnable{
	List<Elevator> elevators = new ArrayList<Elevator>();
	List<Person> waitingPerson = new ArrayList<Person>();
	
	
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
					waitingPerson.add(person);
				} else {
					System.out.println("Thang được chọn là:" + elevators.get(t));
					// Kiểm tra xem nếu hướng của thang đang dừng thì cho hướng của thang hoạt động lại
					if (elevators.get(t).getDirection() == DIRECTION.STOP) {
						elevators.get(t).setDirection(person.getDirection());
					}
					elevators.get(t).addPerson(person);			
					System.out.println("Thang sau khi thêm person là:\n" + elevators.get(t));
				}
			} catch (Exception e) {
				System.out.println("Nhập lại tầng đi bạn ơi");
			}
		}		
    }   
}
