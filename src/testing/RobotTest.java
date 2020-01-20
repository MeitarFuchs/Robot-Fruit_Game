package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameClient.Robot;
import utils.Point3D;

class RobotTest {
	Point3D point = new Point3D(1,2,0);
	Robot robot = new Robot(0,5,4,point,10,50);

	
	@Test
	void getIdTest() {
		if (robot.getR_id()!=0)
			fail("the get id is not good");
	}

	@Test
	void getSrcTest() {
		if (robot.getSrc()!=5)
			fail("the get source is not good");
	}
	@Test
	void getDestTest() {
		if (robot.getDest()!=4)
			fail("the get destination is not good");	
		}
	@Test
	void getLocationTest() {
		if (robot.getLocation()!=this.point)
			fail("the get location is not good");	
		}
	
	@Test
	void setIdTest() {
		this.robot.setR_id(1);
		if (robot.getR_id()!=1)
			fail("the get id is not good");
	}

	@Test
	void setSrcTest() {
		this.robot.setSrc(6);
		if (robot.getSrc()!=6)
			fail("the get source is not good");
	}
	@Test
	void setDestTest() {
		this.robot.setDest(8);

		if (robot.getDest()!=8)
			fail("the get destination is not good");	
		}
	@Test
	void setLocationTest() {
		Point3D p = new Point3D(1,7,0);
		this.robot.setLocation(p);

		if (robot.getLocation()!=p)
			fail("the get location is not good");	
		}
	

}
