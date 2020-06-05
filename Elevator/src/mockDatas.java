import java.util.ArrayList;
import java.util.List;

public class mockDatas {
	public List<Person> mockPersons = new ArrayList<Person>();
	public List<Elevator> mockElevators = new ArrayList<Elevator>();
	
	public mockDatas() throws InterruptedException {
	
		Person person1 = new Person();
		person1.setId("1");
		person1.setFloorFrom(2);
		person1.setFloorTo(5);
		person1.setDirection(DIRECTION.UP);
		mockPersons.add(person1);
		
		Person person2 = new Person();
		person2.setId("2");
		person2.setFloorFrom(2);
		person2.setFloorTo(3);
		person2.setDirection(DIRECTION.UP);
		mockPersons.add(person2);
		
		Person person3 = new Person();
		person3.setId("3");
		person3.setFloorFrom(1);
		person3.setFloorTo(4);
		person3.setDirection(DIRECTION.UP);
		mockPersons.add(person3);
		
		Person person4 = new Person();
		person4.setId("4");
		person4.setFloorFrom(3);
		person4.setFloorTo(2);
		person4.setDirection(DIRECTION.DOWN);
		mockPersons.add(person4);
		
		Person person5 = new Person();
		person5.setId("5");
		person5.setFloorFrom(2);
		person5.setFloorTo(1);
		person5.setDirection(DIRECTION.DOWN);
		mockPersons.add(person5);
		
		Person person6 = new Person();
		person6.setId("6");
		person6.setFloorFrom(3);
		person6.setFloorTo(1);
		person6.setDirection(DIRECTION.DOWN);
		mockPersons.add(person6);

		Person person7 = new Person();
		person7.setId("7");
		person7.setFloorFrom(5);
		person7.setFloorTo(2);
		person7.setDirection(DIRECTION.DOWN);
		mockPersons.add(person7);
		
		Person person8 = new Person();
		person8.setId("8");
		person8.setFloorFrom(3);
		person8.setFloorTo(2);
		person8.setDirection(DIRECTION.DOWN);
		mockPersons.add(person8);
		

		Elevator elevator1 = new Elevator();
		elevator1.setId(1);
		elevator1.setDirection(DIRECTION.UP);
		elevator1.setMaxPerson(5);
		elevator1.setPosition(1);
		elevator1.addPerson(person1);
		elevator1.addPerson(person2);
		mockElevators.add(elevator1);
		
		Elevator elevator2 = new Elevator();
		elevator2.setId(2);
		elevator2.setDirection(DIRECTION.UP);
		elevator2.setMaxPerson(5);
		elevator2.setPosition(4);
		mockElevators.add(elevator2);
	
		Elevator elevator3 = new Elevator();
		elevator3.setId(3);
		elevator3.setDirection(DIRECTION.DOWN);
		elevator3.setMaxPerson(5);
		elevator3.setPosition(1);
		mockElevators.add(elevator3);
		
		Elevator elevator4 = new Elevator();
		elevator4.setId(4);
		elevator4.setDirection(DIRECTION.DOWN);
		elevator4.setMaxPerson(5);
		elevator4.setPosition(2);
		mockElevators.add(elevator4);
		
		Elevator elevator5 = new Elevator();
		elevator5.setId(5);
		elevator5.setDirection(DIRECTION.DOWN);
		elevator5.setMaxPerson(5);
		elevator5.setPosition(3);
		elevator5.addPerson(person8);
		elevator5.addPerson(person7);
		elevator5.addPerson(person6);
		elevator5.addPerson(person5);
		elevator5.addPerson(person4);
		mockElevators.add(elevator5);
		
		

	}
	
}
