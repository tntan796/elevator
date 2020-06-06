import java.util.ArrayList;
import java.util.List;

public class Elevator {
	private int id = 0; // ID phân biệt các tháng
	private DIRECTION direction = DIRECTION.UP; // Hướng đi của thang
	private List<Floor> floors = new ArrayList<Floor>();
	private int position = 0; // Vị trí hiện tại của thang máy
	private OCCUPIE_STATUS occupieStatus = OCCUPIE_STATUS.HAVE_OCCUPIE; // Thang có khả năng chứa thêm người hay không
	private int maxPerson = 10; // Kích thước tối đa của thang
	private List<Person> persons = new ArrayList<Person>(); // Những người đang ở trong thang
	private boolean checkRemove = false; // Tham số kiểm tra nếu như đang dừng để cho người ra vào thì không thay đổi position ở thang máy này

	// Hàm cập nhật lại vị trí và hướng của thang
	public void updatePosition(int totalFloor) throws InterruptedException {
		if (this.checkRemove == false) {
			if (this.direction == DIRECTION.UP) {
				// Nếu như thang máy đang đi lên mà đến tầng cuối cùng thì đổi thành đi xuống
				if (this.position < totalFloor) {
					this.position = this.position + 1;
				} else {
					this.direction = DIRECTION.DOWN;
					this.position = this.position - 1;
				}
			} else if (this.direction == DIRECTION.DOWN){
				// Trường hợp đi xuống nếu như thang chưa đến tầng 0 thì trừ. Ngược lại đến tầng không thì đổi chiều
				if (this.position > 0) {
					this.position = this.position - 1;
				} else {
					this.direction = DIRECTION.UP;
					this.position = this.position + 1;
				}
			} else {
				// Trường hợp Stop thì giữ nguyên
			}
			// Kiểm tra xem có người ra thang. Nếu có thì loại bỏ khỏi List Person và loại bỏ tầng đó. Dừng 1s
			if (CheckPersonOutElevator()) {
				// Cập nhật lại là có người đi ra ngoài. Để thread mới không thay đổi position
				this.checkRemove = true;
				// Dừng 2s để vừa Mở cửa, Vừa đóng cửa
				RemoveAllPerson(this.position);
				RemoveFloor(this.position);
				Thread.sleep(10000);
				// Cập nhật lại trang thái thang đang không có người ra vào
				this.checkRemove = false;
			}
			// Kiểm tra nếu thang không còn người đi lên xuống nữa thì chuyển thang sang trạng thái dừng.
//			if (this.persons.size() == 0) {
//				this.direction = DIRECTION.STOP;
//			}
		}
	}
	
	
	public String GetListPerson(List<Person> persons) {
		String result = "";
		for (int i = 0; i< persons.size(); i++) {
			result += persons.get(i).toString() + "\n";
		}
		return result;
	}
	
	//Hàm loại bỏ tất cả người có tầng đến là tầng hiện tại
	public void RemoveAllPerson(int position) throws InterruptedException {
		try {
			List<Person> indexs = new ArrayList<Person>();
			for(int i= 0; i<this.persons.size(); i++) {
				if (this.persons.get(i).getFloorTo() == position) {
					indexs.add(persons.get(i));
				}
			}
			if (indexs.size() > 0) {
				for(int j= 0; j< indexs.size(); j++) {
					RemovePerson(indexs.get(j));
				}
				System.out.println("Thang máy số: " + this.id + " , Vị trí thang " + position + " dừng 2 giây cho mọi người = "+ indexs.toString() +" ra tầng");
			}
		} catch (Exception e) {
			System.out.println("Lỗi xóa người dùng RemoveAllPerson, "+ e + " của thang " + this.id);
		}
	}
	
	// Hàm loại bỏ 1 người ra khỏi thang
	public List<Person> RemovePerson(Person person) {
		this.persons.remove(person);
		// Thay đổi lại trạng thái khi loại bỏ thang đang có trạng thái là FUll
		if (this.occupieStatus == OCCUPIE_STATUS.FULL) {
			this.occupieStatus = OCCUPIE_STATUS.HAVE_OCCUPIE;
		}
		return this.persons;
	}
	
	//Hàm loại bỏ tầng hiện tại ra khỏi Floors
	public void RemoveFloor(int position) {
		try {
			int index = -1;
			for(int i=0; i< this.floors.size(); i++) {
				if(this.floors.get(i).getFloorId() == position) {
					index= i;
				}
			}
			if(index != -1) {
				this.floors.remove(index);
			}
		} catch (Exception e) {
			System.out.println("Lỗi loại bỏ tầng RemoveFloor: "+ e + " của thang " + this.id);
		}
		
	}
	
	//Hàm kiểm tra xem có người ra khỏi thang ở tầng hiện tại hay không
	public boolean CheckPersonOutElevator() {
		boolean result = false;
		for (int i= 0; i< this.persons.size(); i++) {
			if (this.persons.get(i).getFloorTo() == this.position && this.direction == this.persons.get(i).getDirection()) {
				result = true;
				return result;
			}
		}
		return result;
	}
	
	// Hàm thêm 1 người vào thang, Khi thêm người thì dừng 1s
	public List<Person> addPerson(Person person) {
		try {
			this.persons.add(person);
			// Nếu thêm 1 người thành công thì ta cập nhật lại các tầng của người vừa thêm vào trong mảng
			Floor floor = new Floor();
			floor.setFloorId(person.getFloorTo());
			floor.setPersonId(person.getId());
			addFloor(floor);
			// Kiểm tra nếu đã đủ người thì thay đổi trạng thái có thể thêm
			if (this.persons.size() == this.maxPerson) {
				this.occupieStatus = OCCUPIE_STATUS.FULL;
			}
		} catch (Exception e) {
			System.out.println("Lỗi thêm người vào thang máy addPerson:" + e + ". Người thêm : " + person.toString());
		}
		return this.persons;
	}
	
	// Lấy trạng thái của thang xem có chưa được thêm người hay không
	public OCCUPIE_STATUS getStatusOccupice() {
		if (this.persons.size() < this.maxPerson) {
			return OCCUPIE_STATUS.HAVE_OCCUPIE;
		}
		return OCCUPIE_STATUS.FULL;
	}

	// Kiểm tra xem tầng đã có rồi thì thôi. Không thêm vào list tầng nữa.
	public boolean CheckExitsFloor(Floor floor) {
		boolean result = false;
		for (int i= 0; i< this.floors.size(); i++) {
			if (this.floors.get(i).getFloorId() == floor.getFloorId()) {
				result = true;
				return result;
			}
		}
		return result;
	}

	// THêm mới một tầng. Nếu như tầng ý đã có thì không thêm nữa
	public void addFloor(Floor floor) {
		if (CheckExitsFloor(floor) == false) {
			this.floors.add(floor);	
		}
	}
	
	public String getListPersonString() {
		String result = "";
		for (int i = 0; i< this.persons.size(); i++ ) {
			result = result + this.persons.get(i).toString() + "\n";
		}
		return result;
	}
	
	// Hàm dùng để xem thang có còn thêm được người không
	public OCCUPIE_STATUS getOccupieStatus() {
		if (this.persons.size() < this.maxPerson) {
			return OCCUPIE_STATUS.HAVE_OCCUPIE;
		} else {
			return OCCUPIE_STATUS.FULL;
		}
	}
	
	@Override
	public String toString() {
		String floorString = "[";
		String personString = "";
		for (int i = 0; i < this.floors.size(); i++ ) {
			floorString += this.floors.get(i).getFloorId() + ", ";
		}
		for (int i = 0; i < this.persons.size(); i++ ) {
			personString += this.persons.get(i).getId() + " - " + this.persons.get(i).getFloorTo() + " , ";
		}
		floorString += "]";
		return "(" + this.id + " - " + this.position + " - " + this.persons.size() + " - " +  this.direction + " - " + floorString + "          __|__ " + personString + ")";
	}
	
	
	
	public int getId() {
		return id;
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getMaxPerson() {
		return maxPerson;
	}

	public void setOccupieStatus(OCCUPIE_STATUS occupieStatus) {
		this.occupieStatus = occupieStatus;
	}
	
	public void setMaxPerson(int person) {
		this.maxPerson = person;
	}
	
	public List<Person> getPersons() {
		return persons;
	}

	// Thêm một số người vào thang máy, ta chờ 1s mở cửa. 1s đóng cửa
	public void setPersons(List<Person> persons) throws InterruptedException {
		String testName = "";
		for (int i = 0; i< persons.size(); i++ ) {
			testName += persons.get(i).getId() + " & ";
			addPerson(persons.get(i));
		}
		System.out.println("Thang máy hiện tại: " + this.id + ", Chờ 2s để mọi người " + testName + " vào tầng: "+ this.position);
		Thread.sleep(10000);
	}
}