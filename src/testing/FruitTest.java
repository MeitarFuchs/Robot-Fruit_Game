package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameClient.Fruit;
import gameClient.Robot;
import utils.Point3D;

class FruitTest {

	Point3D point = new Point3D(1,6,0);
	Fruit fruit = new Fruit(1,6,point);

	@Test
	void getTypeTest() {
		if (fruit.getType()!=1)
			fail("the get id is not good");
	}

	@Test
	void getValueTest() {
		if (fruit.getValue()!=6)
			fail("the get source is not good");
	}
	
	@Test
	void getLocationTest() {
		if (fruit.getLocation()!=this.point)
			fail("the get location is not good");	
	}

	@Test
	void setTypeTest() {
		this.fruit.setType(-1);
		if (fruit.getType()!=-1)
			fail("the get id is not good");
	}

	@Test
	void setValueTest() {
		this.fruit.setValue(3);
		if (fruit.getValue()!=3)
			fail("the get source is not good");
	}

	@Test
	void setLocationTest() {
		Point3D p = new Point3D(1,2,0);
		this.fruit.setLocation(p);

		if (fruit.getLocation()!=p)
			fail("the get location is not good");	
	}

}
