package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Robot;
import utils.Point3D;


public class GUI_JFRAME extends JFrame implements ActionListener, MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private graph Dg= new DGraph();
	private Graph_Algo Ag = new Graph_Algo();
	private int mc;

	public GUI_JFRAME(graph graph)
	{
		if (graph==null)
			throw new RuntimeException("the graph is null");
		this.Dg= new DGraph((DGraph) graph);
		this.mc=this.Dg.getMC();
		this.Ag.init(this.Dg);
		initGUI(graph);
	}



	private void initGUI(graph graph) 
	{
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		this.Dg=graph;

		MenuBar menuBar = new MenuBar();

		Menu file = new Menu("file");
		menuBar.add(file);
		this.setMenuBar(menuBar);
		Menu graph_algo = new Menu("graph algo");
		menuBar.add(graph_algo);

		MenuItem item1 = new MenuItem("save to file");
		item1.addActionListener(this);
		MenuItem item2 = new MenuItem("load from file");
		item2.addActionListener(this);

		MenuItem item3 = new MenuItem("Paint my Graph Algo");
		item3.addActionListener(this);
		MenuItem item4 = new MenuItem("Is the graph Conncect?");
		item4.addActionListener(this);

		file.add(item1);
		file.add(item2);
		graph_algo.add(item3);
		graph_algo.add(item4);

		this.addMouseListener(this);

		Thread t=new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int curr_mc=0;
				while (true){
					synchronized(this)
					{
						if (Dg.getMC()!=curr_mc)
						{
							repaint();
							curr_mc=Dg.getMC();
							//stats_lbl.set
						}
					}
				}
			}
		}
				);

		t.start();


	}
	
	public void paint(Graphics dg)
	{
		super.paint(dg);

		if (dg!=null)
		{// paint nodes
			Iterator<node_data> itNode = Dg.getV().iterator(); 
			while (itNode.hasNext())
			{
				node_data nd = itNode.next();
				//draw nodes
				scaleNode(nd.getLocation() , 1000,1000);
				dg.setColor(Color.blue);
				((Graphics) dg).fillOval((int)nd.getLocation().x(), (int)nd.getLocation().y(), 10, 10);
				//write the key node
				dg.setColor(Color.BLUE);
				dg.drawString(""+  nd.getKey() , (int)nd.getLocation().x()+1 , (int)nd.getLocation().y()+1 );

				if (Dg.edgeSize()!=0)
				{// if there are edge - paint edge
					Iterator<edge_data> itEdge = Dg.getE(nd.getKey()).iterator(); 
					while (itEdge.hasNext()) 
					{
						edge_data ed = (edge_data) itEdge.next();
						//draw the edges
						dg.setColor(Color.PINK);
						Point3D pSrc=new Point3D((int)nd.getLocation().x(), (int)nd.getLocation().y());
						node_data destNode = Dg.getNode(ed.getDest());
						Point3D pDest= new Point3D((int)destNode.getLocation().x(), (int)destNode.getLocation().y());
						(dg).drawLine(pSrc.ix(), pSrc.iy(),pDest.ix(), pDest.iy());
						//write the w edge
						String wString = "";
						wString+=String.valueOf(ed.getWeight());
						dg.drawString(wString, ((int)pSrc.ix()+pDest.ix())/2 , ((int)pSrc.iy()+pDest.iy())/2 );

						dg.setColor(Color.YELLOW);
						dg.fillOval((int)(0.9*pDest.ix()+0.1*pSrc.ix()) , (int)(0.9*pDest.iy()+0.1*pSrc.iy()) , 10 , 10 );
					}

				}
			}
		}
	}
	private void scaleNode( Point3D p ,double minW, double maxW)
	{
		double r_minx= 999999998;
		double r_maxx= -999999998;
		ArrayList<node_data> arrN = new ArrayList<node_data>(Dg.getV());
		for (int i = 0; i <arrN.size(); i++)
		{
			if (r_maxx < arrN.get(i).getLocation().x())
				r_maxx = arrN.get(i).getLocation().x();
			if (r_minx > arrN.get(i).getLocation().x())
				r_minx = arrN.get(i).getLocation().x();

		}
System.out.println("minX:   "+r_minx+" maxX:   "+r_maxx);
		double r_miny= 999999998;
		double r_maxy= -999999998;
		for (int i = 0; i < arrN.size(); i++) 
		{
			if (r_maxy < arrN.get(i).getLocation().y())
				r_maxy = arrN.get(i).getLocation().y();
			if (r_miny > arrN.get(i).getLocation().y())
				r_miny = arrN.get(i).getLocation().y();
		}
		System.out.println("minY:   "+r_miny+" maxY:   "+r_maxy);
		double updatX;
		double updatY;
		Iterator<node_data> itN = this.Dg.getV().iterator(); 
		while (itN.hasNext()) 
		{
			node_data nd = itN.next();
			if (nd.getLocation()== p)
			{
				updatX=scale(nd.getLocation().x(), r_minx , r_maxx, 600 , 800);
				updatY=scale(nd.getLocation().y(), r_miny , r_maxy, 600 , 800);
				nd.setLocation( new Point3D(updatX,updatY,0));
			}
		}     
	}

	private double scale(double data, double r_min, double r_max,  double t_min, double t_max)
	{

		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Graph_Algo guiAlgoGraph=new Graph_Algo();
		String str = e.getActionCommand();
		JFileChooser chooser;

		if(str.equals("Paint my Graph Algo"))
		{
			initGUI(this.Dg);				
		}

		if(str.equals("save to file"))
		{


			guiAlgoGraph=new Graph_Algo();	
			guiAlgoGraph.init(this.Dg);

			chooser= new JFileChooser(FileSystemView.getFileSystemView());
			chooser.setDialogTitle("Save graph to a file now..");
			int userChoose = chooser.showSaveDialog(null);

			if (userChoose == JFileChooser.APPROVE_OPTION) 
			{
				System.out.println("Saved as a file:   " + chooser.getSelectedFile().getAbsolutePath());
				guiAlgoGraph.save(chooser.getSelectedFile().getAbsolutePath());
			}		

		}
		if(str.equals("load from file"))
		{
			guiAlgoGraph=new Graph_Algo();
			JFileChooser folder=new JFileChooser(FileSystemView.getFileSystemView());

			int userChoose = folder.showOpenDialog(null);
			if(userChoose == JFileChooser.APPROVE_OPTION)
			{
				System.out.println("the file that choosen to open is:   " + folder.getSelectedFile().getName());
				guiAlgoGraph.init(folder.getSelectedFile().getAbsolutePath());
				this.Dg=guiAlgoGraph.copy();
				initGUI(Dg);	
			}
		}

		if(str.equals("Is the graph Conncect?")) 
		{
			if(guiAlgoGraph.getMyGraph()==null) 
				System.out.println("your graph is null");
			else
			{
				Graph_Algo AlgoG = new Graph_Algo();
				JFrame isCon = new JFrame();		
				AlgoG.init(this.Dg);

				if (AlgoG.isConnected()) 
				{ 
					JOptionPane.showMessageDialog(isCon ,"The graph is Connect!!!");
					System.out.println("The graph is Connect!!!");
				}
				else 
				{ 
					JOptionPane.showMessageDialog(isCon ,"The graph isn't Connect!!!");				
					System.out.println("The graph isn't Connect!!!");
				}
			}
		}

		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");

	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{;	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{;	}

	@Override
	public void mouseExited(MouseEvent e)
	{;	}


	@Override
	public void mousePressed(MouseEvent arg0) 
	{;	}

	public static void main(String[] args) {

		graph Dgraph = new DGraph();

		Point3D p1 = new Point3D(200, 200, 0);
		Point3D p2 = new Point3D(250, 189, 0);
		Point3D p3 = new Point3D(167, 470, 0);
		Point3D p4 = new Point3D(390, 178, 0);
		Point3D p5 = new Point3D(350, 279, 0);
		Point3D p6 = new Point3D(278, 590, 0);

		node_data n1 = new NodeData(1, p1, 9);
		node_data n2 = new NodeData(2, p2, 10);
		node_data n3 = new NodeData(3, p3, 11);
		node_data n4 = new NodeData(4, p4, 12);
		node_data n5 = new NodeData(5, p5, 13);
		node_data n6 = new NodeData(6, p6, 14);

		Dgraph.addNode(n1);
		Dgraph.addNode(n2);
		Dgraph.addNode(n3);
		Dgraph.addNode(n4);
		Dgraph.addNode(n5);
		Dgraph.addNode(n6);

		Dgraph.connect(n1.getKey(), n2.getKey(), 3.4);
		Dgraph.connect(n1.getKey(), n5.getKey(), 5);
		Dgraph.connect(n2.getKey(), n1.getKey(), 5.8);
		Dgraph.connect(n2.getKey(), n3.getKey(), 4);
		Dgraph.connect(n3.getKey(), n1.getKey(), 4);
		Dgraph.connect(n5.getKey(), n3.getKey(), 2.09);
		Dgraph.connect(n5.getKey(), n1.getKey(), 2.87);
		Dgraph.connect(n6.getKey(), n4.getKey(), 3.89);
		Dgraph.connect(n6.getKey(), n2.getKey(), 12);

		GUI_JFRAME GuiG= new GUI_JFRAME(Dgraph);
		GuiG.setVisible(true);
	}
}



