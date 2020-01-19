package gameClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.node_data;
import gui.GameGui_Std;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI extends Thread
{
	private static int numRobots = 0;
	private GameAlgorithms gameAlgo=new GameAlgorithms();
	private GameGui_Std GuiStd= new GameGui_Std();
	private  boolean clickRobot= false;
	private Robot robotT;
	private boolean automatic=false;
	private boolean manual=false;
	private node_data nodeClick;

	public MyGameGUI() throws JSONException 
	{		
		String[] choiceNumLevel = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		Object selectedNumLevel = JOptionPane.showInputDialog(null, "Choosenum of game", "Message", JOptionPane.INFORMATION_MESSAGE, null, choiceNumLevel, choiceNumLevel[0]);
		int level= Integer.parseInt((String) selectedNumLevel);

		this.gameAlgo.setGameService(Game_Server.getServer(level));		

		String[] chooseTypeGame = {"Automatic game","Manual game"};
		Object typeSelectedGame = JOptionPane.showInputDialog(null, "Choose game mode", "Message", JOptionPane.INFORMATION_MESSAGE, null, chooseTypeGame, chooseTypeGame[0]);

		StdDraw.gameGui= this;

		StdDraw.clear();

		if (typeSelectedGame=="Automatic game")
		{
			this.automatic=true;
			String stringGraph= gameAlgo.getGameService().getGraph(); 
			DGraph Dg= new DGraph();
			Dg.init(stringGraph);
			this.gameAlgo.setGraph(Dg);
			this.gameAlgo = new GameAlgorithms(this.gameAlgo.getGameService());

			gameAlgo.buildFruitList(gameAlgo.getGameService());
			try {gameAlgo.buildRobotList(gameAlgo.getGameService(),Dg);} catch (JSONException e) {e.printStackTrace();}

			this.gameAlgo.setGraph(Dg);

			GuiStd.drawGame(this.gameAlgo.getGraph(), this.gameAlgo.getFruitList(), this.gameAlgo.getRobotList());
			StdDraw.show();

			this.gameAlgo.getGameService().startGame();
			this.start();

			//			if (this.gameAlgo.getGameService().isRunning())
			//				System.out.println("the game is running");
			//			else
			//				System.out.println("the game is not running");
			//
			//			while(this.gameAlgo.getGameService().isRunning())
			//			{
			//				moveRobotAuto();
			//
			//				this.gameAlgo.setRobotList(this.gameAlgo.initRobots(this.gameAlgo.getGameService()));
			//				this.gameAlgo.setFruitList(this.gameAlgo.initFruit(this.gameAlgo.getGameService()));
			//
			//
			//				if (new Date().getTime()%10!=0) {
			//					continue;
			//				}
			//
			//				this.gameAlgo.setGraph(this.gameAlgo.getGraph());
			//				GuiStd.paintGame(this.gameAlgo.getGraph(),this.gameAlgo.getFruitList());
			//				GuiStd.paintRobots(this.gameAlgo.getRobotList());
			//
			//			}
			//			System.out.println("YOUR GRADE is:   "+ this.gameAlgo.getGradGame());
			//			//GuiStd.paintRobots(this.gameAlgo.getRobotList());
		}
		else if (typeSelectedGame=="Manual game")
		{
			this.manual=true;
			String stringGraph= gameAlgo.getGameService().getGraph(); 
			DGraph Dg= new DGraph();
			Dg.init(stringGraph);
			this.gameAlgo.setGraph(Dg);
			//	System.out.println("Dg null?   :"+Dg.toString());
			//	System.out.println("gameGraph   :"+this.gameAlgo.getGraph().toString());
			gameAlgo.buildFruitList(gameAlgo.getGameService());
			//System.out.println("empty list fruit?  "+this.gameAlgo.getFruitList().isEmpty());
			GuiStd.drawGame(Dg, this.gameAlgo.getFruitList());
			StdDraw.show();

			numRobots= gameAlgo.howManyRobots(gameAlgo.getGameService());
			JOptionPane.showMessageDialog(null, "you need to put "+numRobots+" robots.");
			System.out.println("game info:"+ this.gameAlgo.getGameService().toString());

			//add the robots in their first location
			int count=0;
			while (count<numRobots)
			{
				Object keyNodeSelected= JOptionPane.showInputDialog("choose number node");
				int keyNode=Integer.parseInt((String) keyNodeSelected);
				System.out.println("keyNode"+keyNode);
				node_data ansNode = new NodeData();
				Iterator<node_data> it =Dg.getV().iterator();
				while (it.hasNext())
				{
					node_data nd = (node_data) it.next();
					if (nd.getKey()==keyNode)
					{
						ansNode=nd;
					}
				}
				//node_data nd=this.gameAlgo.getGraph().getNode(keyNode);
				Robot currRobot= new Robot(keyNode,ansNode.getLocation());
				System.out.println("current robot is:  "+ currRobot.getR_id());
				if (count==0)
				{
					this.gameAlgo.clearRobotList();
				}
				this.gameAlgo.getRobotList().add(count,currRobot);// add to my list
				this.gameAlgo.getGameService().addRobot(keyNode);//////////////////////////////////////add in the server
				this.GuiStd.paintRobots(this.gameAlgo.getRobotList());
				count++;
			}
			////////////// end to add the robots in their first location
			System.out.println("number of robots: " + this.gameAlgo.getGameService().getRobots().size());
			System.out.println("robot list:    "+this.gameAlgo.getRobotList().toString());
			this.gameAlgo.getGameService().startGame();

			System.out.println("after Start Game - is running??? "+this.gameAlgo.getGameService().isRunning());
			//this.gameAlgo = new GameAlgorithms(this.gameAlgo.getGameService());
			//System.out.println("Info Game:  "+this.gameAlgo.getGameService().toString());

			this.start();


		}
	}


	private void moveRobotAuto() throws JSONException //move Robot Automaticly
	{
		List<String> log = this.gameAlgo.getGameService().move();
		System.out.println(log);
		//initFromList(log);
		if(log!=null) 
		{
			//			long t = this.gameAlgo.getGameService().timeToEnd();
			//	initFromList(log);
			for(int i=0;i<log.size();i++) 
			{
				String robot_json = log.get(i);
				try 
				{
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");

					if(dest==-1) 
					{	
						dest = this.gameAlgo.nextNode(this.gameAlgo.getGraph(),src);
						this.gameAlgo.getGameService().chooseNextEdge(rid, dest);
						//						this.gameAlgo.getGameService().move();
						//						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						//						System.out.println(ttt);
					}

					//					StdDraw.show();
					//					this.GuiStd.repaintGame(this.gameAlgo.getGraph(), this.gameAlgo.getFruitList(), this.gameAlgo.getRobotList());
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}


	}
	public  ArrayList<Robot> initRobotsFromList(List <String> str) throws JSONException 
	{
		ArrayList<Robot> robotList = new ArrayList<>();
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


	public void Clicks() 
	{
		System.out.println("movebyClick");
		JFrame massegeJF = new JFrame();
		double x,y;
		if (!this.clickRobot) 
		{
			x = StdDraw.mouseX();
			y = StdDraw.mouseY();
			Point3D mouseClickPoint= new Point3D(x, y);
			Robot currRobot = this.gameAlgo.robotYouCloseTo(this.gameAlgo.getRobotList(),mouseClickPoint);

			if (currRobot!= null) 
			{
				this.robotT=currRobot;
				this.clickRobot= true;
			} 
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Try again1");
			}
		}
		else // when you alredy choose robot and now choose dest
		{
			System.out.println("clickRobot3:   "+clickRobot);
			boolean foundLegalEdge=false;
			x = StdDraw.mouseX();
			y = StdDraw.mouseY();
			Point3D mouseClickPoint= new Point3D(x, y);

			node_data ndDest = this.gameAlgo.nodeYouCloseTo(this.gameAlgo.getGraph(),mouseClickPoint);
			System.out.println("ndDest key"+ndDest.getKey());
			if (this.robotT!= null)
			{
				Iterator<edge_data> itE = this.gameAlgo.getGraph().getE( this.robotT.getSrc()).iterator(); 
				while (itE.hasNext() && !foundLegalEdge) 
				{
					edge_data currEdge = itE.next();
					System.out.println("The key of the currEdge:  "+currEdge.getDest());
					this.nodeClick= this.gameAlgo.getGraph().getNode(currEdge.getDest());
					if (ndDest.getKey()==currEdge.getDest())
					{						
						System.out.println("RobotId " + this.robotT.getR_id()+" NodeID" + ndDest.getKey());

						this.gameAlgo.getGameService().chooseNextEdge(this.robotT.getR_id(), ndDest.getKey());
						this.gameAlgo.getGameService().move();
						System.out.println("The robot you want to go its Leagal! :)"); // legal= if you want to go to a niber
						foundLegalEdge=true;
					}
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Try again2");
			}


			if (foundLegalEdge)
				this.clickRobot = false;
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Try again3");
			}
		}
	}

	public void moveRobotByClick() throws JSONException //ArrayList<Robot> listRo
	{
		List<String> log = this.gameAlgo.getGameService().move();
		if(log!=null)
		{
			this.gameAlgo.setRobotList(initRobotsFromList(log));
			for (int i=0; i<this.gameAlgo.getRobotList().size(); i++)
			{
				Robot cuurR= this.gameAlgo.getRobotList().get(i);
				if (cuurR.getDest()==-1)
				{
					//					int dest = 
					//						this.gameAlgo.getGameService().chooseNextEdge(cuurR.getR_id(), dest);
					this.gameAlgo.getGameService().move();
				}
			}
		}
		//this.gameAlgo.getGameService().move();
	}
	//		List<String> log = this.gameAlgo.getGameService().move();
	//		if(log!=null) {
	//			long t = this.gameAlgo.getGameService().timeToEnd();
	//			for(int i=0;i<log.size();i++) {
	//				String robot_json = log.get(i);
	//				try {
	//					JSONObject line = new JSONObject(robot_json);
	//					JSONObject ttt = line.getJSONObject("Robot");
	//					int rid = ttt.getInt("id");
	//					int src = ttt.getInt("src");
	//					int dest = ttt.getInt("dest");
	//					if(dest==-1) 
	//					{
	//						if (StdDraw.isMousePressed()) 
	//						{
	//							node_data l = wherePress(StdDraw.mouseX(), StdDraw.mouseY(), this.gameAlgo.getGraph());
	//							if (l == null) {
	//								dest = -1;
	//							} else {
	//								dest = l.getKey();
	//								//	                                nextNode( gg, src,n);
	//							}
	//						}
	//						System.out.println("desssttttt:"+dest);
	//						this.gameAlgo.getGameService().chooseNextEdge(idRobot, dest);
	//						// System.out.println("src is :"+ src);
	//						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
	//						System.out.println(ttt);
	//					}
	//					this.gameAlgo.getGameService().move();
	//
	//				}
	//				catch (JSONException e) {e.printStackTrace();}
	//			}
	//		}
	//	}




	public void run() 
	{
		while(this.gameAlgo.getGameService().isRunning())
		{
			this.gameAlgo.setRobotList(this.gameAlgo.initRobots(this.gameAlgo.getGameService()));
			this.gameAlgo.setFruitList(this.gameAlgo.initFruit(this.gameAlgo.getGameService()));
//			GuiStd.paintFruit(this.gameAlgo.getFruitList());
//			GuiStd.paintRobots(this.gameAlgo.getRobotList());

			if (this.automatic)
			{
				try {
					moveRobotAuto();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{// this.manual==true
				try {
					moveRobotByClick();//this.gameAlgo.getRobotList()
				} catch (JSONException e) {	e.printStackTrace();}
			}

			//this.gameAlgo.setGraph(this.gameAlgo.getGraph());
			GuiStd.paintGraph(this.gameAlgo.getGraph());
			GuiStd.paintFruit(this.gameAlgo.getFruitList());
			GuiStd.paintRobots(this.gameAlgo.getRobotList());
			StdDraw.show();

			try {
				sleep(10);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("game info:"+ this.gameAlgo.getGameService().toString());

		}
		//this.gameAlgo.setGraph(this.gameAlgo.getGraph());
		//GuiStd.paintGraph(this.gameAlgo.getGraph());
		//				GuiStd.paintGame(this.gameAlgo.getGraph(),this.gameAlgo.getFruitList());
		//				GuiStd.paintRobots(this.gameAlgo.getRobotList());
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//************************Grade*******************************
		if (!this.gameAlgo.getGameService().isRunning())
		{
			JFrame massegeJF = new JFrame();
			try 
			{
				//System.out.println("YOUR GRADE is:   "+ this.gameAlgo.getGradGame());
				JOptionPane.showMessageDialog(massegeJF, "Game Over - Grade:"+this.gameAlgo.getGradGame());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		

	}

	public static void main(String[] args) throws JSONException 
	{
		MyGameGUI myGame=new MyGameGUI();
	}


}
