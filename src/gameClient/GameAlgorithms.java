package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;

public class GameAlgorithms 
{
	private game_service game;
	private DGraph graph=new DGraph(); //may be just a graph

	private ArrayList<Fruit> fruitList = new ArrayList<Fruit>(); 
	private ArrayList<Robot> robotList ;//= new ArrayList<Robot>()


	public GameAlgorithms() 
	{
		graph=null;
		this.game=null;
		this.fruitList=null;
		this.robotList=null;
	}
	public GameAlgorithms(game_service game) throws JSONException 
	{
		this.graph=null;
		this.game=game;
		buildFruitList(game);
	}
	public GameAlgorithms(DGraph g ,game_service game) throws JSONException 
	{
		this.graph=g;
		this.game=game;
		buildFruitList(game);
		buildRobotList(game,g);
	}

	public game_service getGameService()
	{
		return this.game;
	}

	public void setGameService(game_service game)
	{
		this.game=game;
	}
	public DGraph getGraph()
	{
		return this.graph;
	}
	public void setGraph(DGraph dg) {

		this.graph=dg;
	}

	public ArrayList<Robot> getRobotList()
	{
		return this.robotList;
	}

	public void clearRobotList()
	{
		this.robotList=new ArrayList<Robot> ();
	}
	public ArrayList<Fruit> getFruitList()
	{
		return this.fruitList;
	}

	public void buildFruitList(game_service game) 
	{
		this.fruitList=new ArrayList<Fruit>();

		List<String> log = game.getFruits();
		if(log!=null) 
		{
			for(int i=0;i<log.size();i++) 
			{
				String fruit_json = log.get(i);
				try 
				{
					JSONObject lineFruit = new JSONObject(fruit_json);
					JSONObject currntFruit = lineFruit.getJSONObject("Fruit");

					int type = currntFruit.getInt("type");
					double value = currntFruit.getDouble("value");
					String location = currntFruit.getString("pos");
					String [] arrPoint = location.split(",");
					double xP= Double.parseDouble(arrPoint[0]);
					double yP= Double.parseDouble(arrPoint[1]);
					double zP= Double.parseDouble(arrPoint[2]);
					Point3D cuurntPoint = new Point3D(xP,yP,zP);

					//System.out.println("type:  "+type+" value:  "+value+ "  cuurntPoint:  "+cuurntPoint);
					Fruit newFruit = new Fruit(type ,value ,cuurntPoint); //create the new fruit
					this.fruitList.add(i, newFruit); //add to the this list


				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}

		//System.out.println("finish build fruit list and the size is:  "+this.fruitList.size());
	}
	public ArrayList<Fruit> initFruit(game_service game) 
	{
		ArrayList<Fruit> newfruitList=new ArrayList<Fruit>();

		List<String> log = game.getFruits();
		if(log!=null) 
		{
			for(int i=0;i<log.size();i++) 
			{
				String fruit_json = log.get(i);
				try 
				{
					JSONObject lineFruit = new JSONObject(fruit_json);
					JSONObject currntFruit = lineFruit.getJSONObject("Fruit");

					int type = currntFruit.getInt("type");
					double value = currntFruit.getDouble("value");
					String location = currntFruit.getString("pos");
					String [] arrPoint = location.split(",");
					double xP= Double.parseDouble(arrPoint[0]);
					double yP= Double.parseDouble(arrPoint[1]);
					double zP= Double.parseDouble(arrPoint[2]);
					Point3D cuurntPoint = new Point3D(xP,yP,zP);

					//System.out.println("type:  "+type+" value:  "+value+ "  cuurntPoint:  "+cuurntPoint);
					Fruit newFruit = new Fruit(type ,value ,cuurntPoint); //create the new fruit
					newfruitList.add(i, newFruit); //add to the this list


				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}
		return newfruitList;
		//System.out.println("finish build fruit list and the size is:  "+this.fruitList.size());
	}
	public ArrayList<Robot> initRobots(game_service game) 
	{
		ArrayList<Robot> newRobotList=new ArrayList<Robot>();

		List<String> log = game.getRobots();
		if(log!=null) 
		{
			for(int i=0;i<log.size();i++) 
			{
				String robot_json = log.get(i);
				try 
				{
					JSONObject robotline = new JSONObject(robot_json);
					JSONObject robotS = robotline.getJSONObject("Robot");
					int id = robotS.getInt("id");
					int src =robotS.getInt("src");
					int dest = robotS.getInt("dest");
					double value =robotS.getInt("value");
					double speed =robotS.getInt("speed");
					String ps =robotS.getString("pos");
					Point3D p = new Point3D(ps);

					Robot newRobot = new Robot(id, src, dest, p, value, speed);//create the new fruit
					newRobotList.add(i, newRobot); //add to the this list


				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}
		return newRobotList;
		//System.out.println("finish build fruit list and the size is:  "+this.fruitList.size());
	}
	public int howManyRobots(game_service game) throws JSONException
	{

		String robot_json = game.toString();
		JSONObject lineRobot = new JSONObject(robot_json);
		JSONObject currntRobot = lineRobot.getJSONObject("GameServer");
		int sizeRobots= currntRobot.getInt("robots");
		return sizeRobots;
	}


	public void buildRobotList(game_service game, DGraph dg) throws JSONException 
	{	
		this.robotList=new ArrayList<Robot>();
		int sizeRobots=howManyRobots(game);
		System.out.println("sizerobots: "+sizeRobots);
		for(int i=0; i<sizeRobots; i++)
		{
			Robot newRobot = new Robot(i ,0 ,0); //create the new robot
			System.out.println("newRobot:  "+newRobot.toString());
			this.robotList.add(i,newRobot); //add to the this list	
		}

		startLocationRobot(dg);

		List<String> log = game.getRobots();
		System.out.println("log size:  "+log.size());

		System.out.println("Robottttttttttttttt:  "+game.getRobots());
		if(log!=null) 
		{
			System.out.println("in to log");
			for(int i=0;i<log.size();i++) 
			{
				System.out.println("in to for");
				String robot_json = log.get(i);
				try 
				{
					JSONObject lineRobot = new JSONObject(robot_json);
					JSONObject currntRobot = lineRobot.getJSONObject("Robot");

					int id = currntRobot.getInt("id");
					int src = currntRobot.getInt("src");
					int dest = currntRobot.getInt("dest");
					double value = currntRobot.getDouble("value");
					double speed = currntRobot.getDouble("speed");
					String location = currntRobot.getString("pos");
					String [] arrPoint = location.split(",");
					double xP= Double.parseDouble(arrPoint[0]);
					double yP= Double.parseDouble(arrPoint[1]);
					double zP= Double.parseDouble(arrPoint[2]);
					Point3D cuurntPoint = new Point3D(xP,yP,zP);

					System.out.println("id:  "+id+" value:  "+value+ "  cuurntPoint:  "+cuurntPoint);
					Robot newRobot = new Robot(id, src, dest, cuurntPoint, value, speed); //create the new fruit
					System.out.println("newRobot:  "+newRobot.toString());
					boolean b=this.robotList.add(newRobot); //add to the this list
					System.out.println("b:  "+b);

				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}

		for(int i=0; i<sizeRobots; i++)
		{
			System.out.println("this.robotList.get(i):  "+this.robotList.get(i));
			System.out.println("this.robotList.get(i).getSrc():  "+this.robotList.get(i).getSrc());
			game.addRobot(this.robotList.get(i).getSrc());

			System.out.println("robotList:  "+this.robotList.get(i).toString());
		}


	}

	public void setFruitList(ArrayList<Fruit> fruitList) {
		this.fruitList = fruitList;
	}
	public void setRobotList(ArrayList<Robot> list) {
		this.robotList =  list;
	}
//	public void buildRobotListString(List<String> temp)  throws JSONException 
//	{	
//
//		ArrayList<Robot> tempR = new ArrayList<Robot>();
//		for (String stringR : temp) 
//		{
//			Robot currRobot = new Robot();
//			currRobot = (Robot) currRobot.initLine(stringR);
//			tempR.add(currRobot);
//		}
//		this.robotList = tempR;
//	}

	public  int nextNode(DGraph g, int src) 
	{

		int ans = -1;
		Collection<edge_data> ee = this.graph.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}

	public node_data theNodeRobot(Robot robot) // if not close to any robot - return null
	{
		Iterator<node_data> itN = this.graph.getV().iterator(); 
		while (itN.hasNext()) 
		{
			node_data currNode = itN.next();
			System.out.println("robot.getLocation()"+robot.getLocation());
			System.out.println("currNode.getLocation()"+currNode.getLocation());
			double dis= currNode.getLocation().distance3D(robot.getLocation());
			double eps=0.0000001;
			if (dis<=eps)
			{
				return currNode;
			}
		}
		return null;

	}
	public Robot robotYouCloseTo(ArrayList<Robot> listRo,Point3D clickPoint) // if not close to any robot - return null
	{
		double min=999999999;
		//boolean foundRobot=false;
		Robot ansRobot= new Robot();
		for(int i=0; i<listRo.size(); i++)
		{
			Robot tempRobot= listRo.get(i);
			double dis= tempRobot.getLocation().distance3D(clickPoint);
			//	double eps=0.0000001;
			if (dis<min)
			{
				min=dis;
				//foundRobot=true;
				ansRobot=tempRobot;
			}
		}

		if (ansRobot!=null)
			return ansRobot;

		return null;
	}

	public node_data nodeYouCloseTo(DGraph dg ,Point3D clickPoint) // if not close to any node - return null
	{
		boolean foundNode=false;
		double min=999999;
		node_data ansNode= new NodeData();
		Iterator<node_data> itN = dg.getV().iterator(); //
		while (itN.hasNext() && !foundNode) 
		{
			node_data nd = itN.next();
			double dis= nd.getLocation().distance3D(clickPoint);
			//double eps=0.0000001;
			if (dis<min)
			{
				min=dis;
				//foundNode=true;
				ansNode=nd;
			}
		}

		if (ansNode!=null)
			return ansNode;

		return null;
	}

	public  void startLocationRobot(DGraph dg)
	{
		boolean flag=false;
		for (int i=0; i<robotList.size(); i++)
		{
			Fruit currFruit= new Fruit();
			Robot currRobot= robotList.get(i);
			if (i<this.fruitList.size())
				currFruit= fruitList.get(i);
			if (currFruit.getLocation()!=null)
			{
				edge_data currEdge=theEdgeOfTheFruit(currFruit, dg);
				NodeData nodeSrcEdge= (NodeData) dg.getNode(currEdge.getSrc());
				NodeData nodeDestEdge= (NodeData) dg.getNode(currEdge.getDest());
				//				if (currFruit.getType()==1)
				//				{
				nodeSrcEdge.sethasFruit(1);
				currRobot.setLocation(nodeSrcEdge.getLocation());
				currRobot.setSrc(nodeSrcEdge.getKey());
				currRobot.setDest(nodeDestEdge.getKey());
				//				}
				//				else// if type==-1
				//				{
				//				}
			}
			else
			{
				Iterator<node_data> itNode = this.graph.getV().iterator(); 
				while (itNode.hasNext() && !flag)
				{
					NodeData currNode= (NodeData)  itNode.next();
					if (currNode.gethasFruit()==0 && !flag)
					{
						flag=true;
						currNode.sethasFruit(1);
						currRobot.setLocation(currNode.getLocation());
						currRobot.setSrc(currNode.getKey());
					}
				}
				flag=false;
			}
		}
		//paintRobot(this.robotList);
	}
	public node_data theNodeOfTheRobot(Robot r,DGraph g) 
	{
		node_data nodeRobot = new NodeData();
		Point3D pCurrRobot = r.getLocation();


		boolean foundNode=false;

		Iterator<node_data> itN = g.getV().iterator(); 
		while (itN.hasNext() && !foundNode) 
		{
			node_data nd = itN.next();
			//Point3D pNodeSrc = nd.getLocation(); // node src location
			double dis= nd.getLocation().distance3D(pCurrRobot);

			double eps=0.0000001;
			if (dis<=eps)
			{
				foundNode=true;
				nodeRobot=nd;
			}
		}			

		return nodeRobot;
	}
	public edge_data theEdgeOfTheFruit(Fruit f,DGraph g) 
	{
		edge_data edgeFruit = new EdgeData(); // edgeFruit=null
		Point3D pCurrFruit = f.getLocation();
		boolean foundEdge=false;

		Iterator<node_data> itN = g.getV().iterator(); 
		while (itN.hasNext() && !foundEdge) 
		{
			node_data nd = itN.next();
			Point3D pNodeSrc = nd.getLocation(); // node src location
			//scale(0, 0, 0, 0, 0);
			Iterator<edge_data> itE = g.getE(nd.getKey()).iterator(); 
			while (itE.hasNext() && !foundEdge) 
			{
				edge_data ed = itE.next();
				Point3D pNodeDest = g.getNode(ed.getDest()).getLocation();
				double srcToF= pNodeSrc.distance3D(pCurrFruit);
				double destToF= pNodeDest.distance3D(pCurrFruit);
				double srcToDest= pNodeSrc.distance3D(pNodeDest);
				double abs= Math.abs((srcToF+destToF)-srcToDest);
				double eps=0.0000001;
				if (abs<=eps)
				{
					foundEdge=true;
					edgeFruit=ed;
				}
			}			
		}
		return edgeFruit;
	}

	public  int bestNextNode (node_data srcNodeRobot)
	{
		double minDis=99999999;
		double tempDis=0;
		edge_data fruitEdge= new EdgeData();


		for (int j=0; j<this.fruitList.size(); j++)
		{
			Fruit currFruit=this.fruitList.get(j);
			tempDis= srcNodeRobot.getLocation().distance3D(currFruit.getLocation());
			if (minDis>tempDis)
			{
				minDis=tempDis;
				fruitEdge= theEdgeOfTheFruit(currFruit, this.graph);
			}
		}

		return this.graph.getNode(fruitEdge.getDest()).getKey();

	}



	public double getGradGame() throws JSONException
	{
		double grad=0;

		String robot_json = game.toString();
		JSONObject lineRobot = new JSONObject(robot_json);
		JSONObject currntRobot = lineRobot.getJSONObject("GameServer");
		grad= currntRobot.getDouble("grade");

		return grad;
	}




}
