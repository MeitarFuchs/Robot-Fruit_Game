package gameClient;
/**
 * this class build the fruit of the game (automacally and manual both)
 * the fruit is consists few variables , he have type (banana or apple )
 * a value (how many point she give if we pass on it)
 * and a loccation( where she located in the graph)
 * there is also getters and setters.
 * there is init who build the fruit from the information of the json on the grad the use chose. 
 */
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
	/**
	 * This function adding all the fruit list from the service list information
	 *  ,to the list of all the exist fruit in Our game
	 * @param listS the list from the service
	 * @return the list of the fruit 
	 */
	public  ArrayList<Fruit>  initFromListSFruit(List <String> listS)
	{
		ArrayList <Fruit> tempFruitList =new ArrayList<>();
		for (String  str : listS) 
		{
			tempFruitList.add(initFromline(str));
		}
		return tempFruitList;
	}
 public String toString ()
 {
	String ans="\"Fruit\":{\"value\":"+value+",\"type\":"+type+",\"pos\":\""+location+"";
	return ans;
	 
 }
	/**
	 * this method took all the fruit in the string of the game and build them
	 * @param line the string of the game
	 * @return the fruit after his building
	 */
	public Fruit initFromline(String line) 
	{
		Fruit currFruit=new Fruit();
		try {
			JSONObject obj = new JSONObject(line);
			JSONObject fruit = obj.getJSONObject("Fruit");

			int type = fruit.getInt("type");
			double value = fruit.getDouble("value");
			String locationStr = fruit.getString("pos");
			Point3D location = new Point3D(locationStr);
			currFruit = new Fruit( type,value, location);

		} 
		catch (Exception E) 
		{
			E.printStackTrace();
		}
		return currFruit;
	}



	public static void main(String[] args) throws JSONException 
	{
		
	}

}
