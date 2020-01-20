package gameClient;
/**
 * this class represent a game GUI .
 * there is a constructor who used for starting the game ,
 * ask the user which level he want to start and which game he want to play (automatic or manual)
 * there is also functions to play the manual game: 
 * INIT to get the number of the robots in the game from json  and their information to build them
 * we have also 2 function to move the robot manual, one who check the mouse clicks and sets the new location of the robot in the manual game mode.  
 * and the second move the robot to him new destination.
 * 
 */
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

	private GameAlgorithms gameAlgo=new GameAlgorithms();
	private GameGui_Std GuiStd= new GameGui_Std();
	private static int numRobots = 0;
	private  boolean clickRobot= false;
	private Robot robotT;
	private boolean automatic=false;
	private boolean manual=false;
	private node_data nodeClick;

	/**
	 *Default constructor.(start the game)
	 */
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
			AutoMyGameGui autoGame=new AutoMyGameGui(this.gameAlgo.getGameService());

		}
		else 
			if (typeSelectedGame=="Manual game")
			{
				this.manual=true;
				String stringGraph= gameAlgo.getGameService().getGraph(); 
				DGraph Dg= new DGraph();
				Dg.init(stringGraph);
				this.gameAlgo.setGraph(Dg);
				gameAlgo.buildFruitList(gameAlgo.getGameService());
				GuiStd.drawGame(Dg, this.gameAlgo.getFruitList());
				StdDraw.show();

				numRobots= gameAlgo.howManyRobots(gameAlgo.getGameService());
				JOptionPane.showMessageDialog(null, "you need to put "+numRobots+" robots.");

				//add the robots in their first location that the user choosed
				int count=0;
				while (count<numRobots)
				{
					Object keyNodeSelected= JOptionPane.showInputDialog("choose number node");
					int keyNode=Integer.parseInt((String) keyNodeSelected);
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
					Robot currRobot= new Robot(keyNode,ansNode.getLocation());
					if (count==0)
					{
						this.gameAlgo.clearRobotList();
					}
					this.gameAlgo.getRobotList().add(count,currRobot);// add to our list
					this.gameAlgo.getGameService().addRobot(keyNode);//add in the server
					this.GuiStd.paintRobots(this.gameAlgo.getRobotList());
					count++;
				}
				////////////// end to add the robots in their first location

				this.gameAlgo.getGameService().startGame();
				this.start();
			}
	}

	/**
	 * This function adding all the robot list from the service list information ,to the list of all the exist robots in Our game.
	 */
	public  ArrayList<Robot> initRobotsFromList(List <String> strList) throws JSONException 
	{
		ArrayList<Robot> robotList = new ArrayList<>();
		for (String str:strList) 
		{
			robotList.add(initLine(str));
		}
		return robotList;
	}
	
	/**
	 * This function adding the currnt robot string from the service to the list of all the exist robots in Our game.
	 */
	public  Robot initLine(String lineJson) throws JSONException 
	{
		JSONObject objLine = new JSONObject(lineJson);
		JSONObject currRobotString = objLine.getJSONObject("Robot");
		int id = currRobotString.getInt("id");
		int src =currRobotString.getInt("src");
		int dest = currRobotString.getInt("dest");
		double value =currRobotString.getInt("value");
		double speed =currRobotString.getInt("speed");
		String pointStr =currRobotString.getString("pos");
		Point3D currPoint = new Point3D(pointStr);
		
		Robot currRobot= new Robot(id, src, dest, currPoint, value, speed);
		return currRobot;
	}
	
	/**
	 *This function check the clicks and sets the new location of the robot in the manual game mode.
	 */
	public void Clicks() 
	{
		JFrame massegeJF = new JFrame();
		double xClick=0;
		double yClick=0;
		if (!this.clickRobot) 
		{
			xClick= StdDraw.mouseX();
			yClick= StdDraw.mouseY();
			Point3D mouseClickPoint= new Point3D(xClick,yClick);
			Robot currRobot = this.gameAlgo.robotYouCloseTo(this.gameAlgo.getRobotList(),mouseClickPoint);
			if (currRobot!= null) 
			{
				this.robotT=currRobot;
				this.clickRobot= true;
			} 
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Pleas try again");
			}
		}
		else // when you alredy choose robot and now choose dest
		{
			boolean foundLegalEdge=false;
			xClick = StdDraw.mouseX();
			yClick = StdDraw.mouseY();
			Point3D mouseClickPoint= new Point3D(xClick, yClick);

			node_data nodeDest = this.gameAlgo.nodeYouCloseTo(this.gameAlgo.getGraph(),mouseClickPoint);
			if (this.robotT!= null)
			{
				Iterator<edge_data> itEdge = this.gameAlgo.getGraph().getE( this.robotT.getSrc()).iterator(); 
				while (itEdge.hasNext() && !foundLegalEdge) 
				{
					edge_data currEdge = itEdge.next();
					this.nodeClick= this.gameAlgo.getGraph().getNode(currEdge.getDest());
					if (nodeDest.getKey()==currEdge.getDest())
					{						
						this.gameAlgo.getGameService().chooseNextEdge(this.robotT.getR_id(), nodeDest.getKey());
						this.gameAlgo.getGameService().move();
						
						foundLegalEdge=true;
					}
				}
			}
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Try again");
			}

			if (foundLegalEdge)
				this.clickRobot = false;
			else 
			{
				JOptionPane.showMessageDialog(massegeJF, "Try again");
			}
		}
	}

	/**
	 * This function move the robot to his destenation.
	 */
	public void moveRobotByClick() throws JSONException
	{
		List<String> log = this.gameAlgo.getGameService().move();
		if(log!=null)
		{
			this.gameAlgo.setRobotList(initRobotsFromList(log));
			for (int i=0; i<this.gameAlgo.getRobotList().size(); i++)
			{
				Robot cuurRobot= this.gameAlgo.getRobotList().get(i);
				if (cuurRobot.getDest()==-1)
				{
					this.gameAlgo.getGameService().move();
				}
			}
		}
	}
	
	/**
	 * The RunFunction of the this thread
	 */
	public void run() 
	{
		while(this.gameAlgo.getGameService().isRunning())
		{
			this.gameAlgo.setRobotList(this.gameAlgo.initRobots(this.gameAlgo.getGameService()));
			this.gameAlgo.setFruitList(this.gameAlgo.initFruit(this.gameAlgo.getGameService()));
			
			if (this.manual)
			{// this.manual==true
				try 
				{
					moveRobotByClick();
				} 
				catch (JSONException e) {	e.printStackTrace();}
			}

			GuiStd.paintGraph(this.gameAlgo.getGraph());
			GuiStd.paintFruit(this.gameAlgo.getFruitList());
			GuiStd.paintRobots(this.gameAlgo.getRobotList());
			StdDraw.show();

			try
			{
				sleep(10);
			} 
			catch (InterruptedException e) {e.printStackTrace();}
		}

		try 
		{
			sleep(1000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		//**********Grade**********
		if (!this.gameAlgo.getGameService().isRunning())
		{
			JFrame massegeJF = new JFrame();
			try 
			{
				JOptionPane.showMessageDialog(massegeJF, "Game Over - Grade:"+this.gameAlgo.getGradGame());
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws JSONException 
	{
		MyGameGUI myGame=new MyGameGUI();
	}


}