package gameClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.node_data;
import gui.GameGui_Std;
import utils.Point3D;
import utils.StdDraw;

public class AutoMyGameGui extends Thread
{	
	private GameAlgorithms gameAlgo=new GameAlgorithms();
	private GameGui_Std GuiStd= new GameGui_Std();
	private boolean automatic=true;
	private static int numRobots = 0;

	
	
	public AutoMyGameGui(game_service game) throws JSONException 
	{
		StdDraw.gameAuto= this;

		StdDraw.clear();
		this.gameAlgo.setGameService(game);
		String stringGraph= gameAlgo.getGameService().getGraph(); 
		DGraph Dg= new DGraph();
		Dg.init(stringGraph);
		this.gameAlgo.setGraph(Dg);
		this.gameAlgo = new GameAlgorithms(this.gameAlgo.getGameService());

		gameAlgo.buildFruitList(gameAlgo.getGameService());
		try {gameAlgo.buildRobotList(gameAlgo.getGameService(),Dg);} catch (JSONException e) {e.printStackTrace();}

		this.gameAlgo.setGraph(Dg);

		this.GuiStd.drawGame(this.gameAlgo.getGraph(), this.gameAlgo.getFruitList(), this.gameAlgo.getRobotList());
		StdDraw.show();

		this.gameAlgo.getGameService().startGame();
		
		this.start();
	}
	
	/**
     * This function move the robot to his destanetion.
     */
	private void moveRobotAuto() throws JSONException //move Robot Automaticly
	{
		List<String> log = this.gameAlgo.getGameService().move();
		if(log!=null) 
		{
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
					}
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	
	
	
	public void run() 
	{
		while(this.gameAlgo.getGameService().isRunning())
		{
			this.gameAlgo.setRobotList(this.gameAlgo.initRobots(this.gameAlgo.getGameService()));
			this.gameAlgo.setFruitList(this.gameAlgo.initFruit(this.gameAlgo.getGameService()));
			if (this.automatic)
			{// this.manual==true
				try 
				{
					moveRobotAuto();
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
		JSONObject obj = new JSONObject(lineJson);
		JSONObject array_robots = obj.getJSONObject("Robot");
		int id = array_robots.getInt("id");
		int src =array_robots.getInt("src");
		int dest = array_robots.getInt("dest");
		double value =array_robots.getInt("value");
		double speed =array_robots.getInt("speed");
		String pointStr =array_robots.getString("pos");
		Point3D currPoint = new Point3D(pointStr);
		
		Robot currRobot= new Robot(id,src,dest,currPoint,value,speed);
		return currRobot;
	}
	
}