package algorithms;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;

import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

//import static org.junit.Assert.fail;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable
{

	private static final long serialVersionUID = 1L;
	private DGraph myGraph = new DGraph();


	public Graph_Algo(graph _graph)
	{
		init(_graph);
	}

	public Graph_Algo(DGraph dgraph) 
	{
		this.myGraph=new DGraph(dgraph); 
	}
	public Graph_Algo() 
	{
		this.myGraph=null; 	
	}

	public graph getMyGraph()
	{
		return this.myGraph;
	}

	@Override
	public void init(graph g) 
	{
		this.myGraph=(DGraph) g;

	}

	@Override
	public void init(String file_name) //lode
	{	
		try
		{
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			myGraph= (DGraph)in.readObject();
			in.close();
			file.close();
			System.out.println("The Graph has been deserialized");
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught: we can't lode your graph");
		}
		catch(IOException ex)
		{
			System.out.println(ex);
		}
	}

	@Override
	public void save(String file_name) 
	{
		String filename = "myAlgoGraph.txt"; 

		try
		{    
			FileOutputStream file = new FileOutputStream(filename); 
			ObjectOutputStream out = new ObjectOutputStream(file); 

			out.writeObject(myGraph); 
			out.close(); 
			file.close(); 

			System.out.println("The Graph has been serialized"); 
		}   

		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught- we can't save your graph"); 
		} 
	} 


	private boolean checkAllTag1()
	{
		Iterator <node_data> it = ( this.myGraph.getV()).iterator(); 
		while (it.hasNext()) 
		{
			node_data nd =  it.next();
			if (nd.getTag()!=1)
			{

				return false;
			}
		}
		return true;	
	}

	private void initColor(int t)
	{
		Iterator<node_data> it = this.myGraph.getV().iterator(); 
		while (it.hasNext()) 
		{
			node_data nd = it.next();
			nd.setTag(t);
		}
	}
	private void infinityNodeW()
	{
		Iterator<node_data> it = this.myGraph.getV().iterator(); 
		while (it.hasNext()) 
		{
			node_data nd = it.next();
			nd.setWeight(Integer.MAX_VALUE);
		}
	}

	@Override
	public double shortestPathDist(int src, int dest) 
	{
		List<node_data> lNd=new LinkedList<node_data>() ;
		lNd=shortestPath(src, dest);
		if (lNd.isEmpty())
			return -1;
		double dis=this.myGraph.getNode(dest).getWeight();
		return dis;
	}
	@Override
	public List<node_data> shortestPath(int src, int dest) 
	{
		infinityNodeW();
		initColor(0);
		int count=0;

		Queue<node_data> queueNode = new LinkedList<node_data>();
		List<node_data> shortestPathList=new LinkedList<node_data>();
		node_data srcN = new NodeData(this.myGraph.HashMapNode.get(src));
		node_data destN = this.myGraph.HashMapNode.get(dest);
		if (this.myGraph.HashMapNode.get(src)==null ||  this.myGraph.HashMapNode.get(dest)==null)
		{
			return null;
		}

		srcN.setWeight(0);
		srcN.setInfo("");
		queueNode.add(srcN);
		node_data currNode=srcN;
		double min=Double.MAX_VALUE;
		int countEdge=0;
		Iterator<node_data> itNode = this.myGraph.getV().iterator();
		while(itNode.hasNext()) 
		{
			node_data nd = itNode.next();
			Iterator<edge_data> itEdge = this.myGraph.getE(nd.getKey()).iterator();
			while(itEdge.hasNext()) 
			{
				edge_data ed = itEdge.next();
				countEdge++;
			}
		}

		while(count<(Math.pow(2, countEdge)))	
		{
			Iterator<edge_data> itEdge = this.myGraph.getE(currNode.getKey()).iterator();

			if (itEdge.hasNext())
			{	
				while (itEdge.hasNext()) 
				{
					edge_data currEdge = (edge_data) itEdge.next();
					if(this.myGraph.getNode(currEdge.getDest())!=null)
					{
						if (currNode.getWeight()+currEdge.getWeight() < this.myGraph.getNode(currEdge.getDest()).getWeight() )
						{
							this.myGraph.getNode(currEdge.getDest()).setWeight(currNode.getWeight()+currEdge.getWeight());//update w
							System.out.println("the weight of the ed dest "+ this.myGraph.getNode(currEdge.getDest()).getWeight());
							this.myGraph.getNode(currEdge.getDest()).setInfo(""+currNode.getKey());
						}
						if ((this.myGraph.getNode(currEdge.getDest())).getTag()==0)
						{queueNode.add(this.myGraph.getNode(currEdge.getDest()));}
					}
				}
			}
			node_data minNode= new NodeData();
			minNode=null;
			node_data temp=new NodeData();
			min=Double.MAX_VALUE;
			while (!queueNode.isEmpty()) 
			{
				temp=queueNode.remove();;
				double t= temp.getWeight();
				if (temp.getKey()!=currNode.getKey())
				{
					if (t<min)
					{
						min=t;
						minNode=this.myGraph.getNode(temp.getKey());
					}
				}

			}

			currNode.setTag(1);
			if (minNode!=null)
			{
				currNode=minNode;
				queueNode.add(currNode);
			}

			count++;
		}

		if (!destN.getInfo().equals(""))
		{
			currNode=this.myGraph.getNode(dest);
			shortestPathList.add(currNode);
		}

		while(currNode.getKey()!=src)
		{
			shortestPathList.add(this.myGraph.getNode(Integer.parseInt(currNode.getInfo())));
			currNode=this.myGraph.getNode(Integer.parseInt(currNode.getInfo()));

		}

		if (shortestPathList.size()==1 )
		{
			shortestPathList=null;
			return null;
		}

		return swapListNode(shortestPathList);

	}

	private List<node_data> swapListNode(List<node_data> l)
	{
		List<node_data> newList=new LinkedList<node_data>();
		for (int i=l.size()-1; i>=0; i--)
		{
			newList.add(l.get(i));
		}

		Iterator<node_data> itList = newList.iterator(); 
		while (itList.hasNext()) 
		{
			node_data c = (node_data)itList.next();
		}

		return newList;
	}

	@Override
	public boolean isConnected() 
	{
		initColor(0);
		if (this.myGraph.nodeSize()== 1)
		{
			return true;
		}
		if (this.myGraph.edgeSize()== 0 && this.myGraph.nodeSize() > 1)
		{
			return false;
		}
		if(this.myGraph.nodeSize()>this.myGraph.edgeSize())
		{
			return false;
		}

		Stack<NodeData> sn = new Stack<NodeData>();
		Iterator <node_data> it = ( this.myGraph.getV()).iterator(); 
		if (it.hasNext()) 
		{
			node_data nd =  it.next();
			Iterator<edge_data> itE = this.myGraph.getE(nd.getKey()).iterator(); 
			while (itE.hasNext()) 
			{
				edge_data ed = itE.next();
				(this.myGraph.getNode(ed.getDest())).setTag(1);
				sn.push((NodeData) this.myGraph.getNode(ed.getDest()));
			}
			while(!sn.isEmpty())
			{ 
				Iterator<edge_data> itE2 = this.myGraph.getE(sn.pop().getKey()).iterator(); 
				if (!itE2.hasNext()) //if he does'nt connect to any node
					return false;
				while (itE2.hasNext()) 
				{
					edge_data ed2 = itE2.next();

					if (this.myGraph.getNode(ed2.getDest())!=null) ///ppppp
					{
						if (this.myGraph.getNode(ed2.getDest()).getTag()!=1)
						{
							this.myGraph.getNode(ed2.getDest()).setTag(1);	
							sn.push((NodeData) this.myGraph.getNode(ed2.getDest()));
						}
					}
				}
			}

		}
		if (checkAllTag1())		
		{
			System.out.println("conect!!!");
			return true;
		}
		else
		{
			System.out.println("NOT conect!!!");
			return false;
		}
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) 
	{
		LinkedList <node_data> listAns = new LinkedList<node_data>();
		LinkedList <node_data> tempList = new LinkedList<node_data>();
		initColor(0);

		if (!isConnected()) 
		{
			return TSPnotConnect( this.myGraph,targets);
		}

		if (targets.size()==1)
		{
			listAns.add(this.myGraph.HashMapNode.get(targets.get(0)));
			return listAns;
		}
		else 
		{
			int numList=0;
			while(numList<targets.size()-1)
			{
				tempList=null;
				tempList= (LinkedList<node_data>) shortestPath(targets.get(numList), targets.get(numList+1));

				listAns.addAll(tempList);

				if (numList!=0) 
				{
					listAns.remove(numList);
				}
				numList++;
			}

			numList=0;
			while(numList<targets.size()-1) // remove douplicat
			{
				if (listAns.get(numList).getKey() == listAns.get(numList+1).getKey() )
				{
					listAns.remove(numList);
				}
				numList++;
			}	
		}
		return listAns;
	}


	private List<node_data> TSPnotConnect(DGraph g, List<Integer> targets) 
	{
		DGraph gList= new DGraph( g);
		Graph_Algo Ag=new Graph_Algo();

		Collection<node_data> colectionNode=new LinkedList<node_data>( gList.getV());
		Iterator<node_data> itNodes=colectionNode.iterator();

		while(itNodes.hasNext()) 
		{
			node_data nd=((node_data) itNodes.next());
			if(!targets.contains(nd.getKey())) 
			{
				gList.removeNode(nd.getKey());
				System.out.println("we REMOVE this node");
			}
			else
				System.out.println("we DONT REMOVE this node");

		}

		Ag.init(gList);

		if(Ag.isConnected()) 
		{
			System.out.println("The new graph is connect!!!");
			return Ag.TSP(targets);
		}
		else 
		{
			System.out.println("The new graph is not connect!!!");
			List<node_data> l = new LinkedList<node_data>();
			return l;
		}
	}

	@Override
	public graph copy() 
	{ 
		graph newGraph = new DGraph();
		Iterator<node_data> itNode = this.myGraph.getV().iterator(); 
		while (itNode.hasNext()) 
		{
			node_data nd = (node_data)itNode.next();
			newGraph.addNode(nd);
			Iterator<edge_data> itEdge = this.myGraph.getE(nd.getKey()).iterator(); 
			while (itEdge.hasNext()) 
			{
				edge_data ed = (edge_data) itEdge.next();
				newGraph.connect(nd.getKey(), ed.getDest(), ed.getWeight());
			}
		}
		return  newGraph;
	}

}
