package testing;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.Fruit;
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



	@Test
	public void initFromListF() 
	{
		game_service game = Game_Server.getServer(1);
		List <String> fruitStrings = game.getFruits();
		ArrayList<Fruit> ansListFruit ;
		Fruit currFruitInLIst=new Fruit();
		ansListFruit= currFruitInLIst.initFromListSFruit(fruitStrings);
		
		currFruitInLIst=ansListFruit.get(0);

			if (currFruitInLIst.getType()!= -1)
			{
				fail();
			}
			System.out.println("lll"+currFruitInLIst.getValue());
			if (currFruitInLIst.getValue()!= 5.0)
			{
				fail();
			}
			Point3D p=new Point3D(35.197656770719604,32.10191878639921,0.0);
			
			if (!currFruitInLIst.getLocation().toString().equals(p.toString()))
			{
				fail();
			}

	}

	@Test
	public void initFromline() 
	{
		game_service game = Game_Server.getServer(1);
		List <String> frStrings = game.getFruits();
		String str = frStrings.get(0);
		System.out.println(str);
		Fruit currFruit =new Fruit();

		try 
		{
			currFruit=currFruit.initFromline(str);
			
			System.out.println(currFruit.getType());
			if (currFruit.getType() != -1)
			{
				System.out.println(currFruit.getType());
				fail();
			}
			System.out.println(currFruit.getValue());
			
			if (currFruit.getValue()!=5.0)
			{
				fail();
			}
			
			Point3D p=new Point3D(35.197656770719604,32.10191878639921,0.0);
			System.out.println(currFruit.getLocation().toString());
			System.out.println(p.toString());
			
			if (!currFruit.getLocation().toString().equals(p.toString()))
			{
				fail();
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

}
