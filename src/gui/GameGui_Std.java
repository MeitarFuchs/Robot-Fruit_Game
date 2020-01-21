package gui;
/**
 * this class is responsible to open a window and draw the graph, robots and the fruits on the game.
 */
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import utils.Range;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gameClient.Fruit;
import gameClient.Robot;
import utils.Point3D;
import utils.StdDraw;

public class GameGui_Std 
{
	/**
	 * this method repaint the whole game (robots, fruits and graph)
	 */
	public void repaintGame(DGraph g ,ArrayList<Fruit> fruitL,ArrayList<Robot> robotL)
	{
		paintGame(g,fruitL);
		paintRobots(robotL);
	}
	/**
	 * this method create the windows of the GUI and send to a method who draw the graph  and the fruits
	 * @param g the graph we want to draw
	 * @param fruitL the list of the fruits we want to draw
	 */
	public void drawGame(DGraph g,ArrayList<Fruit> fruitL) 
	{
		StdDraw.setCanvasSize(800,800);		
		paintGame(g,fruitL);
	}

	/**
	 * this method create the windows of the GUI and send to a method who draw the graph , the robots  and the fruits
	 * @param g the graph we want to draw
	 * @param fruitL the list of the fruits we want to draw
	 * @param robotL the list of the robots we wnat to draw
	 */
	public void drawGame(DGraph g,ArrayList<Fruit> fruitL,ArrayList<Robot> robotL) 
	{
		StdDraw.setCanvasSize(800,800);	
		StdDraw.clear();
		StdDraw.enableDoubleBuffering();
		paintGame(g,fruitL);
		paintRobots(robotL);
	}
	/**
	 * this method draw the fruits 
	 * @param fruitL the list of the fruits we want to draw
	 */
	public void paintFruit(ArrayList<Fruit> fruitL) 
	{
		for (int i=0; i<fruitL.size(); i++)
		{
			Fruit currFruit= new Fruit();
			currFruit=fruitL.get(i);
			if (currFruit.getType()==1)//apple-red   1
				StdDraw.picture(currFruit.getLocation().x(), currFruit.getLocation().y(),"red.png",0.00005,0.00005);
			else//banana-yellow 
				StdDraw.picture(currFruit.getLocation().x(), currFruit.getLocation().y(),"yellow.jpg",0.0005,0.0005);
			//			if (currFruit.getType()==1)//apple-red   1
			//				StdDraw.setPenColor(Color.RED);
			//			else
			//				StdDraw.setPenColor(Color.YELLOW);//banana-yellow        1
			//			StdDraw.filledCircle((currFruit.getLocation().x()) ,(currFruit.getLocation().y() ),0.00018);
		}
	}
	/**
	 * this method draw the robots 
	 * @param robotL the list of robot we want to draw
	 */
	public void paintRobots(ArrayList<Robot> robotL)
	{
		for (int i=0; i<robotL.size(); i++)
		{
			Robot currRobot= new Robot();
			currRobot=robotL.get(i);
			StdDraw.setPenColor(Color.ORANGE);     
			StdDraw.picture(currRobot.getLocation().x(), currRobot.getLocation().y(),"bob.png",0.0020,0.0010);
		}
	}
	/**
	 * this method draw the graph she get , if the nodes are not in the window we change the scale.
	 * @param g the graph we need to draw
	 */
	public void paintGraph(DGraph g)
	{
		StdDraw.clear();
		StdDraw.enableDoubleBuffering();
		double r_minx=Integer.MAX_VALUE;
		double r_maxx=Integer.MIN_VALUE;

		ArrayList<node_data> ndArrList = new ArrayList<node_data>(g.getV());
		for (int i = 0; i < ndArrList.size(); i++) 
		{
			if (r_maxx < ndArrList.get(i).getLocation().x())
				r_maxx = ndArrList.get(i).getLocation().x();
			if (r_minx > ndArrList.get(i).getLocation().x())
				r_minx = ndArrList.get(i).getLocation().x();
		}

		double r_miny=Integer.MAX_VALUE;
		double r_maxy=Integer.MIN_VALUE;
		for (int i = 0; i < ndArrList.size(); i++) {
			if (r_maxy < ndArrList.get(i).getLocation().y())
				r_maxy = ndArrList.get(i).getLocation().y();
			if (r_miny > ndArrList.get(i).getLocation().y())
				r_miny = ndArrList.get(i).getLocation().y();
		}

		Range ansX = new Range(r_minx,r_maxx);
		Range ansY = new Range(r_miny,r_maxy);

		StdDraw.setXscale(ansX.get_min()-0.005 , ansX.get_max()+0.002);
		StdDraw.setYscale(ansY.get_min()-0.005,ansY.get_max()+0.002);


		Iterator<node_data> it = g.getV().iterator();
		while (it.hasNext())
		{
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setPenRadius(0.07);

			node_data srcN = (node_data) it.next();
			Point3D pS = srcN.getLocation();

			StdDraw.filledCircle(pS.x(),pS.y(),0.00008);

			StdDraw.text(pS.x(),pS.y(),""+(srcN.getKey()),0);
			Collection<edge_data> edgeCO = g.getE(srcN.getKey());
			if (edgeCO != null) 
			{
				Iterator<edge_data> itEdge = edgeCO.iterator();
				while (itEdge.hasNext()) 
				{
					edge_data src = (edge_data) itEdge.next();
					StdDraw.setPenColor(Color.PINK);
					StdDraw.setFont(new Font("David", 1, 12));
					node_data dest = g.getNode(src.getDest());
					StdDraw.setPenRadius(0.003);
					StdDraw.line(pS.x(), pS.y(), dest.getLocation().x(), dest.getLocation().y());
					String p1=String.format("%.3f",src.getWeight());

					StdDraw.setFont(new Font("David", 1, 15));
					StdDraw.textRight((pS.x() + dest.getLocation().x())/2 ,(pS.y() + dest.getLocation().y())/2 ,"" + Double.parseDouble(p1));
					StdDraw.setPenColor(Color.cyan);
					StdDraw.filledCircle((pS.x() *0.1) + (0.9 * dest.getLocation().x()) ,(pS.y() * 0.1) +
							(0.9* dest.getLocation().y()),0.00010);
				}
			}
		}


	}
	/**
	 * this method draw the graph and the fruit list
	 * @param g the graph she need to draw
	 * @param fruitL the fruit list she need to draw
	 */
	public void paintGame(DGraph g,ArrayList<Fruit> fruitL)
	{
		StdDraw.clear();

		double r_minx=Integer.MAX_VALUE;
		double r_maxx=Integer.MIN_VALUE;

		ArrayList<node_data> ndArrList = new ArrayList<node_data>(g.getV());
		for (int i = 0; i < ndArrList.size(); i++) 
		{
			if (r_maxx < ndArrList.get(i).getLocation().x())
				r_maxx = ndArrList.get(i).getLocation().x();
			if (r_minx > ndArrList.get(i).getLocation().x())
				r_minx = ndArrList.get(i).getLocation().x();
		}
		for (int i=0; i<fruitL.size();i++)
		{
			if (r_maxx < fruitL.get(i).getLocation().x())
				r_maxx = fruitL.get(i).getLocation().x();
			if (r_minx > fruitL.get(i).getLocation().x())
				r_minx = fruitL.get(i).getLocation().x();
		}
		double r_miny=Integer.MAX_VALUE;
		double r_maxy=Integer.MIN_VALUE;
		for (int i = 0; i < ndArrList.size(); i++) {
			if (r_maxy < ndArrList.get(i).getLocation().y())
				r_maxy = ndArrList.get(i).getLocation().y();
			if (r_miny > ndArrList.get(i).getLocation().y())
				r_miny = ndArrList.get(i).getLocation().y();
		}
		for (int i=0; i<fruitL.size();i++)
		{
			if (r_maxy < fruitL.get(i).getLocation().y())
				r_maxy = fruitL.get(i).getLocation().y();
			if (r_miny > fruitL.get(i).getLocation().y())
				r_miny = fruitL.get(i).getLocation().y();
		}
		Range ansX = new Range(r_minx,r_maxx);
		Range ansY = new Range(r_miny,r_maxy);

		StdDraw.setXscale(ansX.get_min()-0.005 , ansX.get_max()+0.002);
		StdDraw.setYscale(ansY.get_min()-0.005,ansY.get_max()+0.002);
		StdDraw.enableDoubleBuffering();
		//System.out.println("after scale");

		Iterator<node_data> it = g.getV().iterator();
		while (it.hasNext())
		{
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.setPenRadius(0.07);

			node_data srcN = (node_data) it.next();
			Point3D pS = srcN.getLocation();

			StdDraw.filledCircle(pS.x(),pS.y(),0.00008);

			StdDraw.text(pS.x(),pS.y(),""+(srcN.getKey()),0);
			Collection<edge_data> edgeCO = g.getE(srcN.getKey());
			if (edgeCO != null) 
			{
				Iterator<edge_data> itEdge = edgeCO.iterator();
				while (itEdge.hasNext()) 
				{
					edge_data src = (edge_data) itEdge.next();
					StdDraw.setPenColor(Color.PINK);
					StdDraw.setFont(new Font("David", 1, 12));
					node_data dest = g.getNode(src.getDest());
					StdDraw.setPenRadius(0.003);
					StdDraw.line(pS.x(), pS.y(), dest.getLocation().x(), dest.getLocation().y());
					String p1=String.format("%.3f",src.getWeight());

					StdDraw.setFont(new Font("David", 1, 15));
					StdDraw.textRight((pS.x() + dest.getLocation().x())/2 ,(pS.y() + dest.getLocation().y())/2 ,"" + Double.parseDouble(p1));
					StdDraw.setPenColor(Color.cyan);
					StdDraw.filledCircle((pS.x() *0.1) + (0.9 * dest.getLocation().x()) ,(pS.y() * 0.1) +
							(0.9* dest.getLocation().y()),0.00010);
				}
			}
		}

		StdDraw.setPenRadius(6);
		//draw fruit list
		paintFruit(fruitL);



	}

}
