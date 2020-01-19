package gameClient;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Point3D;

public class Robot 
{
	private int r_id;
	private int src;
	private int dest;
	private double value;
	private double speed;
	private Point3D location;
	private static int countID=0;

	public int getR_id() {
		return r_id;
	}

	public void setR_id(int r_id) {
		this.r_id = r_id;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}


	public Point3D getLocation(){
		return this.location;
	}

	public void setLocation(Point3D location){
		this.location=location;
	}

	public Robot() 
	{
		this.r_id=-1;
		this.src=0;
		this.dest=0;
		this.location=null;
	}
	public Robot(int r_id) 
	{
		this.r_id=r_id;
		this.src=0;
		this.dest=0;
		this.location=null;

	}
	public Robot(int src, Point3D location) 
	{
		this.r_id=countID;
		this.src=src;
		this.dest=0;
		this.location=location;

	}
	public Robot(int r_id, int src, int dest) 
	{

		this.r_id=r_id;
		this.src=src;
		this.dest=dest;

	}

	public Robot(int r_id, int src, int dest, Point3D location) 
	{
		this.r_id=r_id;
		this.src=src;
		this.dest=dest;
		this.location=location;
	}
	public Robot(int r_id, int src, int dest, Point3D location,double value, double speed) 
	{
		this.r_id=r_id;
		this.src=src;
		this.dest=dest;
		this.location=location;
		this.value=value;
		this.speed=speed;
	}



	public String toString()
	{
		String ans="id: "+this.r_id+" src:  "+this.src+" dest:  "+this.dest+" LOC:  "+this.location+" value:  "+this.value+" speed:  "+this.speed;
		return ans ;
	}
	public static int howManyRobot(String s) throws JSONException {
		JSONObject obj = new JSONObject(s);
		JSONObject robots = obj.getJSONObject("GameServer");
		int num = robots.getInt("robots");
		return num;
	}

	public static List<Robot> initFromList(List <String> str) throws JSONException 
	{
		List <Robot> robotList = new LinkedList<>();
		for (String s:str) 
		{
			robotList.add(initLine(s));
		}
		return robotList;
	}

	public static  Robot initLine(String lineJson) throws JSONException 
	{
		JSONObject obj = new JSONObject(lineJson);
		JSONObject array_robots = obj.getJSONObject("Robot");
		int id = array_robots.getInt("id");
		int src =array_robots.getInt("src");
		int dest = array_robots.getInt("dest");
		double value =array_robots.getInt("value");
		double speed =array_robots.getInt("speed");
		String ps =array_robots.getString("pos");
		Point3D p = new Point3D(ps);
		Robot r= new Robot(id, src, dest, p, value, speed);

		return r;
	}
}
