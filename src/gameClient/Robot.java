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
	public static int howManyRobot(String s) throws JSONException 
	{
		JSONObject robotSInfo = new JSONObject(s);
		JSONObject robots = robotSInfo.getJSONObject("GameServer");
		int numOfRobots = robots.getInt("robots");
		return numOfRobots;
	}

	public static List<Robot> initFromList(List <String> strList) throws JSONException 
	{
		List <Robot> robotList = new LinkedList<>();
		for (String str:strList) 
		{
			robotList.add(initLine(str));
		}
		return robotList;
	}

	public static  Robot initLine(String lineJson) throws JSONException 
	{
		JSONObject objline = new JSONObject(lineJson);
		JSONObject currRobotStr = objline.getJSONObject("Robot");
		int id= currRobotStr.getInt("id");
		int src=currRobotStr.getInt("src");
		int dest= currRobotStr.getInt("dest");
		double value=currRobotStr.getInt("value");
		double speed=currRobotStr.getInt("speed");
		String pointString=currRobotStr.getString("pos");
		Point3D point = new Point3D(pointString);
		Robot currRobot= new Robot(id, src,dest,point, value, speed);

		return currRobot;
	}
}
