package gameClient;
/**
 * this class represent the robot of the game for the automate  and the manual game.
 * the robot is consists few variables , he have id , source(where is it now) ,destination(where he need to go ),
 * value , speed , location.
 * there is also getters , setters and tostring
 * there is init who build the robot from the information of the json and a function who found the number of robot there is on the level the user want.
 */
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
//	/**
//	 * this method check how many robot we have in the string of the level she get
//	 * @param s the string of the level
//	 * @return the number of robots
//	 * @throws JSONException
//	 */
//	public static int howManyRobot(String s) throws JSONException 
//	{
//		JSONObject robotSInfo = new JSONObject(s);
//		JSONObject robots = robotSInfo.getJSONObject("GameServer");
//		int numOfRobots = robots.getInt("robots");
//		return numOfRobots;
//	}
	/**
	 * this method build a list of robot that we have in the game with the llist she get
	 * @param strList a list of string of robots
	 * @return a list of robot
	 * @throws JSONException
	 */
	public static List<Robot> initFromList(List <String> strList) throws JSONException 
	{

		List <Robot> robotList = new LinkedList<>();
		for (String str:strList) 
		{
			robotList.add(initLine(str));
		}
		return robotList;
	}
	/**
	 * this method build the robots (with the constructor) that she get in the string
	 * @param lineJson the string of the robot
	 * @return the robot she build
	 * @throws JSONException
	 */
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
