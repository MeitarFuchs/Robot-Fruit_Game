package gameClient;
/**
 * In this class we have constructor who start the game, getters and setters 
 * The class represent some algorithms for the game. 
 * We have here init for the robot and the fruit to build their lists.
 * their is also function to know how many robot we have in the level we chose. 
 * A function who chose the next node for the robot ramdomaly,
 * and function who help us to know where we have fruit  and where to put the robot at the beginning of the game
 * their is also algorithms who found in which node the robot is and a function who return the grade at the end .
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.json.JSONException;
import org.json.JSONObject;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

public class GameAlgorithms 
{
	private game_service game;
	private DGraph graph=new DGraph(); //may be just a graph

	private ArrayList<Fruit> fruitList = new ArrayList<Fruit>(); 
	private ArrayList<Robot> robotList ;//= new ArrayList<Robot>()

	private Queue<Integer> qWay=new LinkedList<Integer>() ;

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
	/**
	 * clear the robot list , create a new robot list who is empty
	 */
	public void clearRobotList()
	{
		this.robotList=new ArrayList<Robot> ();
	}
	public ArrayList<Fruit> getFruitList()
	{
		return this.fruitList;
	}
	/**
	 * this method get a game , took the fruit from there and build each fruit to add it to the fruit list
	 * @param game the game she get
	 */
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

					Fruit newFruit = new Fruit(type ,value ,cuurntPoint); //create the new fruit
					this.fruitList.add(i, newFruit); //add to the this list


				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}
	}
	/**
	 * this method get a game , took the fruit from there and build each fruit to add it to the fruit list and return that list
	 * @param game the game she get
	 * @return the fruit list she build
	 */
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
	}
	/**
	 * this method get a game , took the robot from there and build each robot to add it to the robot list and return that list
	 * @param game the game she get
	 * @return the robot list she build
	 */
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
	}
	/**
	 * this method check how many robot we have in the string of the level she get
	 * @param s the string of the level
	 * @return the number of robots
	 * @throws JSONException
	 */
	public int howManyRobots(game_service game) throws JSONException
	{

		String robot_json = game.toString();
		JSONObject lineRobot = new JSONObject(robot_json);
		JSONObject currntRobot = lineRobot.getJSONObject("GameServer");
		int sizeRobots= currntRobot.getInt("robots");
		return sizeRobots;
	}

	/**
	 * this method build the robot list with all  the robot and put them in here start location (use another method) in the graph
	 * @param game the game 
	 * @param dg the graph
	 * @throws JSONException
	 */
	public void buildRobotList(game_service game, DGraph dg) throws JSONException 
	{	
		this.robotList=new ArrayList<Robot>();
		int sizeRobots=howManyRobots(game);
		for(int i=0; i<sizeRobots; i++)
		{
			Robot newRobot = new Robot(i ,0 ,0); //create the new robot
			this.robotList.add(i,newRobot); //add to the this list	
		}

		startLocationRobot(dg);

		List<String> log = game.getRobots();

		if(log!=null) 
		{
			for(int i=0;i<log.size();i++) 
			{
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

					Robot newRobot = new Robot(id, src, dest, cuurntPoint, value, speed); //create the new fruit
					boolean b=this.robotList.add(newRobot); //add to the this list

				} 
				catch (JSONException e) 
				{	e.printStackTrace();   }
				catch (Exception e) 
				{	e.printStackTrace();   }

			}
		}

		for(int i=0; i<sizeRobots; i++)
		{
			game.addRobot(this.robotList.get(i).getSrc());
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
	/**
	 * chose randomly the next node the robot will go
	 * @param g the graph of the game
	 * @param src the key of the node the robot is in
	 * @return the key of the node the robot need to go
	 */
	public  int nextNodeRandomly(DGraph g, int src) 
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
		//		int ans = -1;
		//
		//		Collection<edge_data> ee = this.graph.getE(src);
		//		int s = ee.size();
		//		int [] arr=new int [s];
		//		Iterator<edge_data> itr = ee.iterator();
		//		int i=0;
		//		while (itr.hasNext()) {
		//
		//			edge_data ed = itr.next();
		//			int key = ed.getDest();
		//			arr[i]=key; 
		//			i++;
		//		}
		//
		//		int r = (int)(Math.random()*s);
		//		//		arr[r]
		//		//		while(i<r) {itr.next();i++;}
		//		ans = arr[r];
		//		return ans;
	}

	public  int nextNodeONMyNeib(DGraph g, int src) 
	{
		int ans= -1;
		Collection<edge_data> ee = this.graph.getE(src);
		Iterator<edge_data> itEdge = ee.iterator();
		while(itEdge.hasNext())
		{
			edge_data ed=itEdge.next();
			for (int i=0; i<this.fruitList.size(); i++)
			{
				edge_data edgeOfFruit= theEdgeOfTheFruit(this.fruitList.get(i),this.graph);
				if (ed.getSrc()==edgeOfFruit.getSrc() && ed.getDest()==edgeOfFruit.getDest())
				{
					return ed.getDest();
				}					
			}
		}
		return ans;
	}
	public  int theClosetestFruitToRobot(int keySrc) //the src of the edge of the fruit that is the clostest to the robot
	{
		int SrcFruit;
		double minDis=Double.MAX_VALUE;
		double tempDis=0;
		int ansSrcFruit=-1;
		Graph_Algo Ag = new Graph_Algo();
		Ag.init(this.graph);
		for (int i=0; i<this.fruitList.size(); i++)
		{
			Fruit currFruit=this.fruitList.get(i);
			edge_data edFruit=theEdgeOfTheFruit(currFruit, graph);
			//			if (currFruit.getType()== -1) // banana
			SrcFruit= edFruit.getSrc();
			//			else
			//				SrcFruit= edFruit.getDest();
			tempDis= Ag.shortestPathDist(keySrc, SrcFruit);

			if (tempDis<minDis)
			{

				minDis=tempDis;
				ansSrcFruit=SrcFruit;
			}

		}
		System.out.println("ansSrcFruit: "+ansSrcFruit);
		return ansSrcFruit;
	}
	public List<node_data> getShourtestPath (int src, int dest)
	{
		System.out.println("getShourtestPath");
		System.out.println("src: "+src+" dest:  "+dest);
		Graph_Algo Ag = new Graph_Algo();
		Ag.init(this.graph);
		return Ag.shortestPath(src, dest);
	}

	public int queueLast3 (NodeData n) 
	{

		if (qWay==null)
		{
			this.qWay.add(n.getKey());		
		}
		if (qWay.size() < 3) 
		{
			this.qWay.add(n.getKey());		
		}
		else 
		{
			this.qWay.remove();
			if (!qWay.isEmpty() )//&& qWay.peek()!=n.getKey()
			{
				this.qWay.add(n.getKey());
				//return n.getKey();
			}
		}
		//		 if (qWay.isEmpty())
		//			return -1;

		return qWay.peek();
	}
	/**
	 * this method check in which node the robot is in
	 * @param robot the robot
	 * @return the node the the robot is in
	 */
	public node_data theNodeRobot(Robot robot) // if not close to any robot - return null
	{
		Iterator<node_data> itN = this.graph.getV().iterator(); 
		while (itN.hasNext()) 
		{
			node_data currNode = itN.next();
			double dis= currNode.getLocation().distance3D(robot.getLocation());
			double eps=0.0001;
			if (dis<=eps)
			{
				return currNode;
			}
		}
		return null;

	}
	/**
	 * this method check next to which robot the mouse click was
	 * @param listRo list of robots
	 * @param clickPoint the location of the mouse click
	 * @return the robot that the click was next to him
	 */
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
	/**
	 * this method check next to which node the mouse was , in the graph she have
	 * @param dg the graph
	 * @param clickPoint the location of the mouse click
	 * @return the node that the click was closed to ,
	 * if not close to any node - return nullif not close to any node - return null
	 */
	public node_data nodeYouCloseTo(DGraph dg ,Point3D clickPoint) 
	{
		boolean foundNode=false;
		double min=Integer.MAX_VALUE;
		node_data ansNode= new NodeData();
		Iterator<node_data> itN = dg.getV().iterator(); 
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
	/**
	 * check for each robot what it the better place for him to start the game
	 * @param dg the graph of the game
	 */
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
				nodeSrcEdge.sethasFruit(1);
				currRobot.setLocation(nodeSrcEdge.getLocation());
				currRobot.setSrc(nodeSrcEdge.getKey());
				currRobot.setDest(nodeDestEdge.getKey());
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
	}
	/**
	 * check in which node the robot is in
	 * @param r the robot
	 * @param g the graph
	 * @return the node that the robot is in
	 */
	public node_data theNodeOfTheRobot(Robot r,DGraph g) 
	{
		node_data nodeRobot = new NodeData();
		Point3D pCurrRobot = r.getLocation();
		boolean foundNode=false;
		Iterator<node_data> itN = g.getV().iterator(); 
		while (itN.hasNext() && !foundNode) 
		{
			node_data nd = itN.next();
			double dis= nd.getLocation().distance3D(pCurrRobot);
			double eps=0.00000001;
			if (dis<=eps)
			{
				foundNode=true;
				nodeRobot=nd;
			}
		}			
		return nodeRobot;
	}
	/**
	 * get a fruit and check in which edge the fruit is in, in the graph
	 * @param f the fruit
	 * @param g the graph of the game
	 * @return the edge of the fruit
	 */
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
			Iterator<edge_data> itE = g.getE(nd.getKey()).iterator(); 
			while (itE.hasNext() && !foundEdge) 
			{
				edge_data ed = itE.next();
				Point3D pNodeDest = g.getNode(ed.getDest()).getLocation();
				double srcToF= pNodeSrc.distance3D(pCurrFruit);
				double destToF= pNodeDest.distance3D(pCurrFruit);
				double srcToDest= pNodeSrc.distance3D(pNodeDest);
				double abs= Math.abs((srcToF+destToF)-srcToDest);
				double eps=0.00000001;
				if (abs<=eps)//
				{
					foundEdge=true;
					edgeFruit=ed;
				}
			}			
		}
		System.out.println("edgeFruit src: "+edgeFruit.getSrc()+" dest: "+edgeFruit.getDest());
		return edgeFruit;
	}

	public List<edge_data> getListOfEdgeWithFruit() 
	{
		List <edge_data> edgeFruitList = new LinkedList<edge_data>();
		buildFruitList(this.game);
		for (Fruit f : this.fruitList) 
		{
			edgeFruitList.add(theEdgeOfTheFruit(f,this.graph));
		}

		return edgeFruitList;
	}

	public void clearTagEdge(DGraph graph) 
	{
		Iterator<node_data> itN = this.graph.getV().iterator(); 
		while (itN.hasNext()) 
		{
			node_data nd = itN.next();
			Iterator<edge_data> itE = this.graph.getE(nd.getKey()).iterator(); 
			while (itE.hasNext()) 
			{
				edge_data ed = itE.next();
				ed.setTag(0);
			}
		}

	}
	public void bestNextNode (DGraph dg, Robot currRobot) throws JSONException //node_data srcNodeRobot
	{
		//this.robotList= initRobots(this.game);
		Graph_Algo Ag = new Graph_Algo();
		Ag.init(dg);
		int dest=-1;
		int last1=-1;
		if  (this.nextNodeONMyNeib(this.getGraph() , currRobot.getSrc()) != -1) 
		{
			dest=this.nextNodeONMyNeib(this.getGraph() , currRobot.getSrc());
			this.game.chooseNextEdge(currRobot.getR_id(),dest);
		}

		else // i dont have fruit on my neibers
		{
			int closeSrcEdgeFruit=this.theClosetestFruitToRobot(this.getGraph().getNode(currRobot.getSrc()).getKey());			
			List<node_data> shortWay=Ag.shortestPath(currRobot.getSrc(), closeSrcEdgeFruit);

			if (shortWay.size()==1) {
				dest = shortWay.get(0).getKey();
				this.game.chooseNextEdge(currRobot.getR_id(), dest);
			}
			else
				dest = shortWay.get(1).getKey(); // defult dest

//			if ( shortWay.size() > 1) 
//			{

				last1= queueLast3((NodeData) this.getGraph().getNode(dest));

				if ((last1 == shortWay.get(1).getKey()) ) 
				{
					dest=nextNodeRandomly(graph, currRobot.getSrc());
					while(dest==shortWay.get(1).getKey()) 
					{
						dest=nextNodeRandomly(graph, currRobot.getSrc());
					}

					this.game.chooseNextEdge(currRobot.getR_id(), dest);

				}

			//}// end  if-shortWay.size() > 1
		}
		this.game.chooseNextEdge(currRobot.getR_id(), dest);
		//this.game.move();
	}



	public double getGameLevel() throws JSONException
	{
		double level=0;

		String gameString = this.game.toString();
		JSONObject lineRobot = new JSONObject(gameString);
		JSONObject currntRobot = lineRobot.getJSONObject("GameServer");
		level= currntRobot.getDouble("game_level");

		return level;
	}
	/**
	 * check what is the score of the game after the game is over
	 * @return the score
	 * @throws JSONException
	 */
	public double getGradGame() throws JSONException
	{
		double grad=0;

		String gameString = game.toString();
		JSONObject lineRobot = new JSONObject(gameString);
		JSONObject currntRobot = lineRobot.getJSONObject("GameServer");
		grad= currntRobot.getDouble("grade");

		return grad;
	}




}