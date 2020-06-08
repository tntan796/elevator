import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.UUID;

public class ThreadPersonPressButton  implements Runnable{
	List<Elevator> elevators = new ArrayList<Elevator>();
	int totalFloor;
	
	
	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
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
					List<Integer> test = new ArrayList<Integer>();
					for(int i=0; i<elevators.size(); i++) {
						test.add(helper.CountLength(elevators.get(i), person, totalFloor));
					}
					// Thang được chọn là thang có độ dài ngắn nhất
					int minIndex = test.indexOf(Collections.min(test));
					System.out.println("Thang máy chờ: " + elevators.get(minIndex).getId() + " - Vị trí: " + elevators.get(minIndex).getPosition());
					elevators.get(minIndex).setWaitting(person);	
				} else {
					System.out.println("Thang được chọn là:" + elevators.get(t));
					elevators.get(t).setWaitting(person);		
					System.out.println("Thang sau khi thêm person là:\n" + elevators.get(t));
				}
			} catch (Exception e) {
				System.out.println("Nhập lại tầng đi bạn ơi");
			}
		}		
    }   
}
