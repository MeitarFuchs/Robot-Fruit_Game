package gameClient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Server.game_service;
import utils.Point3D;

public class Fruit 
{
	private int type;
	private double value;
	private Point3D location;
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public Point3D getLocation(){
		return this.location;
	}
	
	public void setLocation(Point3D location) {
		this.location = location;
	}

	
	
	public Fruit() 
	{
		this.type=-1; // to check what is the deafult type
		this.value=0;
		this.location=null;
		
	}
	
	public Fruit(int type, double value, Point3D location) 
	{
		this.type=type;
		this.value=value;
		this.location=location;	
	}
	
	public  ArrayList<Fruit>  initFromListSFruit(List <String> ls){
		ArrayList <Fruit> tempFruitList =new ArrayList<>();
		for (String  s:ls) 
		{
			tempFruitList.add(initFromline(s));
		}
		return tempFruitList;
	}


	public  Fruit initFromline(String line) 
	{
		Fruit currFruit=new Fruit();
		try {
			JSONObject obj = new JSONObject(line);
			JSONObject fruit = obj.getJSONObject("Fruit");
			int type;
			double value;
			String location_s;
			Point3D location;
			value = fruit.getDouble("value");
			location_s = fruit.getString("pos");
			location = new Point3D(location_s);
			type = fruit.getInt("type");
			currFruit = new Fruit( type,value, location);

		} 
		catch (Exception E) 
		{
			E.printStackTrace();
		}
		return currFruit;

	}

}
