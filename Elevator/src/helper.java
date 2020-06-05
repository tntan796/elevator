import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class helper {
		public static boolean CheckElevatorInit(List<Elevator> elevators) {
			boolean result = true;
			for(int i=0; i < elevators.size(); i++) {
				if (elevators.get(i).getDirection() != DIRECTION.STOP) {
					result = false;
					return result;
				}
			}
			return result;
		}
	// Tìm thang máy phù hợp
		public static int findElevator(List<Elevator> elevators, Person person) {
			// List thang máy đi lên có thể trở được người
			List<Integer> elevatorsUp = new ArrayList<Integer>();
			// List thang máy đi xuống có thể trở được người
			List<Integer> elevatorsDown = new ArrayList<Integer>();
			// Người muốn đi xuống
			if (person.getDirection() == DIRECTION.DOWN) {
				// Nếu trường hợp khởi tạo tất cả các thang đều đang là stop thì chọn 1 thang bất kì// Chưa nghĩ ra giải quyết
				if (CheckElevatorInit(elevators) == true) {					
					for (int i = 0; i < elevators.size(); i++) {
						Elevator el = elevators.get(i);
						// Ta kiểm tra xem hướng của thang đang là đi xuống Down, hoặc đang Stop, xem thang có đang trống, và vị trí của thang có quá người hiện tại chưa.
						if ((el.getDirection() == DIRECTION.DOWN || el.getDirection() == DIRECTION.STOP)
								&& el.getOccupieStatus() == OCCUPIE_STATUS.HAVE_OCCUPIE
								&& el.getPosition() >= person.getFloorFrom()
							) {
							elevatorsDown.add(el.getId());
						}
					}					
				} else {
					// Trường hợp ngược lại
					for (int i = 0; i < elevators.size(); i++) {
						Elevator el = elevators.get(i);
						// Ta kiểm tra xem hướng của thang đang là đi xuống Down, hoặc đang Stop, xem thang có đang trống, và vị trí của thang có quá người hiện tại chưa.
						if ((el.getDirection() == DIRECTION.DOWN || el.getDirection() == DIRECTION.STOP)
								&& el.getOccupieStatus() == OCCUPIE_STATUS.HAVE_OCCUPIE
								&& el.getPosition() >= person.getFloorFrom()
							) {
							elevatorsDown.add(el.getId());
						}
					}
				}
				System.out.println("List thang máy xuống có thể chọn:" + elevatorsDown.toString());
				// Lấy phần tử nhỏ nhất sẽ là thang máy được chọn
				if (elevatorsDown.size() > 0) {
					int minIndex = elevatorsDown.indexOf(Collections.min(elevatorsDown));
					System.out.println("Thông tin thang xuống được chọn: " + elevatorsDown.get(minIndex));
					return elevatorsDown.get(minIndex);
				}
				return -1;
			} else {
				// Người muốn đi lên
				for (int i = 0; i < elevators.size(); i++) {
					Elevator el = elevators.get(i);
					if ((el.getDirection() == DIRECTION.UP || el.getDirection() == DIRECTION.STOP)
							&& el.getOccupieStatus() == OCCUPIE_STATUS.HAVE_OCCUPIE
							&& el.getPosition() <= person.getFloorTo()) {
						elevatorsUp.add(el.getId());
					}
				}
				if (elevatorsUp.size() > 0) {
					System.out.println("List thang máy lên có thể chọn:" + elevatorsUp.toString());
					// Lấy phần tử lớn nhất sẽ là thang máy được chọn
					int maxIndex = elevatorsUp.indexOf(Collections.max(elevatorsUp));
					System.out.println("Thông tin thang máy lên được chọn: " + elevatorsUp.get(maxIndex));
					return elevatorsUp.get(maxIndex);
				}
				return -1;
			}
		}
		
		//Tạo ra mã bất kỳ
		public static String GenerateStringKey() {			  
		    int leftLimit = 97; // letter 'a'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 15;
		    Random random = new Random();
		    StringBuilder buffer = new StringBuilder(targetStringLength);
		    for (int i = 0; i < targetStringLength; i++) {
		        int randomLimitedInt = leftLimit + (int) 
		          (random.nextFloat() * (rightLimit - leftLimit + 1));
		        buffer.append((char) randomLimitedInt);
		    }
		    String generatedString = buffer.toString();
		    return generatedString;
		}
		
}
