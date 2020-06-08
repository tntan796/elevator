import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
			List<Integer> elevatorsUp = new ArrayList<Integer>(); 	// List thang máy đi lên có thể trở được người			
			List<Integer> elevatorsDown = new ArrayList<Integer>(); // List thang máy đi xuống có thể trở được người
			// Người muốn đi xuống
			if (person.getDirection() == DIRECTION.DOWN) {
				if (CheckElevatorInit(elevators) == true) {					
					// Nếu trường hợp khởi tạo tất cả các thang đều đang là stop thì chọn 1 thang bất kì// Chưa nghĩ ra giải quyết		
				} else {
					// Trường hợp ngược lại
					for (int i = 0; i < elevators.size(); i++) {
						Elevator el = elevators.get(i);
						// Ta kiểm tra xem hướng của thang đang là đi xuống Down, hoặc đang Stop, xem thang có đang trống, và vị trí của thang có quá người hiện tại chưa.
						if ((el.getDirection() == DIRECTION.DOWN || el.getDirection() == DIRECTION.STOP)
								&& el.getOccupieStatus() == OCCUPIE_STATUS.HAVE_OCCUPIE
								&& el.getPosition() >= person.getFloorFrom()
								&& el.getPosition() > person.getFloorTo()
							) {
							elevatorsDown.add(el.getId());
						}
					}
				}
				System.out.println("List thang máy xuống có thể chọn:" + elevatorsDown.toString());
				// Chọn trong các thang này thang nào có ít người đang waitting hơn
				List<Integer> tempElevatorDown = new ArrayList<Integer>();
				for(int i=0; i< elevatorsDown.size(); i++) {
					tempElevatorDown.add((elevators.get(elevatorsDown.get(i)).getPersons().size() * 2) + elevators.get(elevatorsDown.get(i)).getWaitting().size());
				}
				
				if (elevatorsDown.size() > 0) {
					int minIndex = tempElevatorDown.indexOf(Collections.min(tempElevatorDown)); // Lấy phần tử nhỏ nhất sẽ là thang máy được chọn chính là thang được chọn
					System.out.println("Thông tin thang xuống được chọn: " + elevatorsDown.get(minIndex));
					return elevatorsDown.get(minIndex);
				}
				return -1;
			} else {
				// Người muốn đi lên
				for (int i = 0; i < elevators.size(); i++) {
					Elevator el = elevators.get(i);
					// Để thang máy được chọn thì vị trí hiện tại của thang phải cùng hướng đi với người bấm, Còn chỗ. Vị trí của thang thì phải < cả vị trí hiện tại và vị trí muốn đến của người bấm thang
					if ((el.getDirection() == DIRECTION.UP || el.getDirection() == DIRECTION.STOP)
							&& el.getOccupieStatus() == OCCUPIE_STATUS.HAVE_OCCUPIE
							&& el.getPosition() < person.getFloorTo()
							&& el.getPosition() <= person.getFloorFrom()) {
						elevatorsUp.add(el.getId());
					}
				}
				List<Integer> tempElevatorUp = new ArrayList<Integer>();
				for(int i=0; i< elevatorsDown.size(); i++) {
					tempElevatorUp.add((elevators.get(elevatorsDown.get(i)).getPersons().size() * 2) + elevators.get(elevatorsDown.get(i)).getWaitting().size());
				}
				if (elevatorsUp.size() > 0) {
					System.out.println("List thang máy lên có thể chọn:" + elevatorsUp.toString());					
					int maxIndex = tempElevatorUp.indexOf(Collections.max(tempElevatorUp)); // Lấy phần tử lớn nhất sẽ là thang máy được chọn chính là thang sẽ được chọn
					System.out.println("Thông tin thang máy lên được chọn: " + elevatorsUp.get(maxIndex));
					return elevatorsUp.get(maxIndex);
				}
				return -1;
			}
		}
		
		// Sắp xếp List thang máy theo ưu tiên: Vị trí > Số người trên thang. Nếu thang đi lên quá vị trí đang chọn
		// thì thang nào max sẽ được chọn. Ngược lại
		static void OrderElevator(List<Elevator> elevators, DIRECTION type) {
			List<Elevator> result = new ArrayList<Elevator>();
			// Tìm list các thang cùng chiều. 
			for(int i = 0; i< elevators.size(); i++) {
				if (elevators.get(i).getDirection() == type) {
					result.add(elevators.get(i));
				}
			}
			
		    Collections.sort(result, new Comparator() {
		        public int compare(Object o1, Object o2) {

		            Integer x1 = ((Elevator) o1).getPosition();
		            Integer x2 = ((Elevator) o2).getPosition();
		            int sComp = x1.compareTo(x2);

		            if (sComp != 0) {
		               return sComp;
		            } 
		            Integer x3 = ((Elevator) o1).getPersons().size();
		            Integer x4 = ((Elevator) o2).getPersons().size();
		            return x3.compareTo(x4);
		    }});
		}
		
		// Hàm tính khoảng cách tới điểm hiện tại. Trong trường hợp không có thang
		public static int CountLength(Elevator elevator, Person person, int totalFloor) {
			
			int result = 0;
			if (elevator.getDirection() == person.getDirection()) {
				// Trường hợp cùng chiều => Phải đi đến đích => Quay lại về đích => Rồi quay cùng chiều
				 result = (totalFloor - elevator.getPosition()) + totalFloor + person.getFloorFrom();				 
				 // Nếu như thang có người, thì ta sẽ lấy số người + với 2s tương ứng với mở và đóng cửa
				 if (elevator.getPersons().size() > 0) {
						result += elevator.getPersons().size() + 2;
				 }
				 // Những người trong waitting cũng cộng 2
				 if (elevator.getWaitting().size() > 0) {
						result += elevator.getWaitting().size() + 2;
				 }
			} else {
				// Trường hợp ngược chiều thì chỉ cần Về đích => Quay cùng chiều
				result = totalFloor + person.getFloorFrom();
				//Nếu như thang có người, thì ta sẽ lấy số người + với 2s tương ứng với mở và đóng cửa
				if (elevator.getPersons().size() > 0) {
					result += elevator.getPersons().size() + 2;
				}
				// Những người trong waitting cũng cộng 2
				 if (elevator.getWaitting().size() > 0) {
						result += elevator.getWaitting().size() + 2;
				 }
			}
			return result;
		}
		
		//Tạo ra mã bất kỳ, giúp phân biệt các person với nhau
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
