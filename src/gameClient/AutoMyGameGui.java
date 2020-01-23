package gameClient;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
/**
 * The class represent the automatic game , 
 * the constructor build the game  and open the window of the GUI,
 * their is also a init to build the robot from the json file
 * in  the class we have the function  moveRobotAuto() who move the robot automacally in the graph
 * that class extends threads.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.DGraph;
import gui.GameGui_Std;
import utils.Point3D;
import utils.StdDraw;

public class AutoMyGameGui extends Thread
{	
	private GameAlgorithms gameAlgo=new GameAlgorithms();
	private GameGui_Std GuiStd= new GameGui_Std();
	private boolean automatic=true;
	private static int numRobots = 0;
	private KML_Logger kml = new KML_Logger();
	private Queue<Integer> q=new LinkedList<>();



	/**
	 * default constructor , start the automatic game
	 * @param game the game she need to start
	 * @throws JSONException
	 */
	public AutoMyGameGui(game_service game) throws JSONException 
	{
		//clock();
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
			//long time = this.gameAlgo.getGameService().timeToEnd();
			for (int i = 0; i <log.size(); i++) 
			{
				String robotSrting = log.get(i);
				try 
				{
					JSONObject line = new JSONObject(robotSrting);
					JSONObject currRobotStr = line.getJSONObject("Robot");
					int r_id = currRobotStr.getInt("id");
					int src = currRobotStr.getInt("src");
					int dest = currRobotStr.getInt("dest");
					String pointString=currRobotStr.getString("pos");
					Point3D point = new Point3D(pointString);

					Robot tempRobot=new Robot(r_id, src, dest,point);

					if(dest==-1) 
					{	
						this.gameAlgo.bestNextNode(this.gameAlgo.getGraph(), tempRobot);
						this.gameAlgo.clearTagEdge(this.gameAlgo.getGraph());

					}
				}
				catch (JSONException e) {
					System.out.println("catch move robot auto");
				}
			}
		}
		//this.gameAlgo.getGameService().move();
	}

	/**
	 * that function close the kml file
	 * @throws IOException
	 */

	public void KMLclose() throws IOException {

		kml.close_KML();
		kml.save_KML();

	}
	/**
	 * this method update the fruits from the server while the game is running.
	 */
	public void updateFruits(){
		List<String> updateFruits = this.gameAlgo.getGameService().getFruits();
		if(updateFruits != null){
			for (int i = 0; i < this.gameAlgo.getFruitList().size(); i++) {
				this.gameAlgo.getFruitList().get(i).initFromline(updateFruits.get(i));
				if(this.kml != null){
					if(this.gameAlgo.getFruitList().get(i).getType() == 1){
						this.kml.placeMark("fruit_1" , this.gameAlgo.getFruitList().get(i).getLocation().toString());
					}else{
						this.kml.placeMark("fruit_-1" , this.gameAlgo.getFruitList().get(i).getLocation().toString());
					}
				}
			}
		}
	}

	/**
	 *this method update the robots from the server while the game is running.
	 * @throws JSONException 
	 */
	public void updateRobots() throws JSONException{
		List<String> updateRobots = this.gameAlgo.getGameService().getRobots();
		for (int i = 0; i < this.gameAlgo.getRobotList().size(); i++) {
			this.gameAlgo.getRobotList().get(i).initLine(updateRobots.get(i));
			if (this.kml != null){
				this.kml.placeMark("https://banner2.cleanpng.com/20180219/hvw/kisspng-robot-cartoon-clip-art-robot-5a8b8347f17e33.1624179315190925519892.jpg" , this.gameAlgo.getRobotList().get(i).getLocation().toString());
			}
		}
	}

	long start = System.currentTimeMillis();
	/**
	 * this method Powered by the thread and call the function to move the robot

	 */
	public void run() 
	{
		while(this.gameAlgo.getGameService().isRunning())
		{

			//			try {
			//				updateFruits();
			//				updateRobots();
			//			} catch (JSONException e1) {
			//				// TODO Auto-generated catch block
			//				e1.printStackTrace();
			//			}
			this.gameAlgo.setRobotList(this.gameAlgo.initRobots(this.gameAlgo.getGameService()));
			this.gameAlgo.setFruitList(this.gameAlgo.initFruit(this.gameAlgo.getGameService()));
			GuiStd.paintFruit(this.gameAlgo.getFruitList());

			if (this.automatic)
			{// this.manual==true
				try 
				{
					if((System.currentTimeMillis() - start) > 1000/9)
					{	
						moveRobotAuto();
						start = System.currentTimeMillis();
						GuiStd.paintGraph(this.gameAlgo.getGraph());
						GuiStd.paintFruit(this.gameAlgo.getFruitList());
						GuiStd.paintRobots(this.gameAlgo.getRobotList());
						StdDraw.show();

						updateFruits();
						updateRobots();
					}
				} 
				catch (JSONException e) {	e.printStackTrace();}
			}



			//			try
			//			{
			//				sleep(10);
			//			} 
			//			catch (InterruptedException e) {e.printStackTrace();}
		}

		//**********Grade**********
		if (!this.gameAlgo.getGameService().isRunning())
		{
			JFrame massegeJF = new JFrame();
			try 
			{
				System.out.println(this.gameAlgo.getGameService().toString());
				JOptionPane.showMessageDialog(massegeJF, "Game Over - Grade:"+this.gameAlgo.getGradGame());

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
		try {
			KMLclose();
		} catch (IOException e) {
			e.printStackTrace();
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
	/**
	 * prints a clock and the score on the screen.
	 */
	//    private void clock() {
	//        try {
	//            String gameInfo = this.gameAlgo.getGameService().toString();
	//            JSONObject line = new JSONObject(gameInfo);
	//            JSONObject ttt = line.getJSONObject("GameServer");
	//            int score = ttt.getInt("grade");
	//            StdDraw.setPenColor(Color.BLACK);
	//            StdDraw.setPenRadius(0.4);
	//            Font font = new Font("Arial", Font.BOLD, 15);
	//            StdDraw.setFont(font);
	//            StdDraw.text(Range_x.get_max()-0.0004, Range_y.get_max() - 0.0005, "Score : " + score);
	//            StdDraw.setPenColor(Color.red);
	//            StdDraw.setPenRadius(0.4);
	//            StdDraw.text(Range_x.get_max()-0.0002, Range_y.get_max() ,"Time to end : " +this.gameAlgo.getGameService().timeToEnd() / 1000);
	//        } catch (JSONException e) {
	//            e.printStackTrace();
	//        }
	//        StdDraw.gameAuto = this;
	//    }

}