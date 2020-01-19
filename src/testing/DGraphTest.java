package testing;

import static org.junit.Assert.fail;
import org.junit.jupiter.api.Test;
import dataStructure.graph;
import dataStructure.NodeData;
import dataStructure.node_data;
import dataStructure.DGraph;
import utils.Point3D;

class DGraphTest {

	//////////add tests//////////

	@Test
	void AddNodeTest() 
	{
		graph Dg = new DGraph();

		Point3D p0 = new Point3D(0, 0, 0);
		Point3D p1 = new Point3D(0, 2, 3);

		NodeData node0 =new NodeData(9 ,p0 , 5.0);
		node_data node1 = new NodeData(1 ,p1 , 6.0);
		node_data node2 = new NodeData(2 ,7);
		node_data node3 = new NodeData(3);
		node_data node4 = new NodeData();
		NodeData nodecopy =  new NodeData(node1);

		Dg.addNode(node0);
		Dg.addNode(node1);
		Dg.addNode(node2);
		Dg.addNode(node3);
		Dg.addNode(node4);
		Dg.addNode(nodecopy); // because the key is the same he can't add it
		
		System.out.println("Dg.nodeSize() "+ Dg.nodeSize());
		if (Dg.nodeSize()!=5) 
		{
			fail();
		}
	}
	//////////connect tests//////////

	@Test
	void ConnectTest() 
	{

		graph Dg = new DGraph();
		Point3D p0 = new Point3D(1, 6, 0);
		Point3D p1 = new Point3D(0, 2, 3);
		Point3D p2 = new Point3D(1, 4, 0);
		Point3D p3 = new Point3D(5, 2, 0);
		NodeData node0 = new NodeData (0 ,p0, 5.8,"gh",0);
		node_data node1 = new NodeData(1 ,p1 ,6,"gh", 0);
		node_data node2 = new NodeData(2 ,p2 ,7,"gh", 0);
		node_data node3 = new NodeData(3 ,p3 ,8,"gh", 0);
		System.out.println("befor nodes"+ Dg.nodeSize());
		Dg.addNode(node0);
		Dg.addNode(node1);
		Dg.addNode(node2);
		System.out.println("after add nodes"+ Dg.nodeSize());

		System.out.println("befor connect:  "+ Dg.edgeSize());

		Dg.connect(node0.getKey(), node1.getKey(), 6);
		Dg.connect(node1.getKey(), node2.getKey(), 7);

		System.out.println("after 2 connect:  "+ Dg.edgeSize());
		if (Dg.edgeSize()!=2) 
		{ 
			fail();
		}

		Dg.connect(node2.getKey(), node3.getKey(), 8);
		System.out.println("after 3 connect:  "+ Dg.edgeSize());

		if (Dg.edgeSize()!=3) 
		{ 
			fail();
		}

		if ((Dg.getEdge(node1.getKey(), node2.getKey()).getWeight())!=7) 
		{ 
			fail();
		}	
		if (( Dg.getEdge( node2.getKey(), node3.getKey() ).getWeight())!=8) 
		{ 
			fail();
		}	

		Dg.connect(node1.getKey(), node3.getKey(), 8);
		System.out.println("after 4 connect:  "+ Dg.edgeSize());

		if (Dg.edgeSize()!=4) 
		{ 
			fail();
		}

	}
	//////////Remove tests//////////
	@Test
	void RemoveNodeTest() 
	{
		graph Dg = new DGraph();

		Point3D p0 = new Point3D(1, 6, 0);
		Point3D p1 = new Point3D(0, 2, 3);
		Point3D p2 = new Point3D(1, 4, 0);
		Point3D p3 = new Point3D(5, 2, 0);

		node_data node0 = new NodeData (0 ,p0 ,5,"gh", 0);
		node_data node1 = new NodeData(1 ,p1 ,6,"gh", 0);
		node_data node2 = new NodeData(2 ,p2 ,7,"gh", 0);
		node_data node3 = new NodeData(3 ,p3 ,8,"gh", 0);

		Dg.addNode(node0);
		Dg.addNode(node1);
		Dg.addNode(node2);
		Dg.addNode(node3);

		Dg.connect(node0.getKey(), node1.getKey(), 9);
		Dg.connect(node1.getKey(), node2.getKey(), 10);
		Dg.connect(node1.getKey(), node3.getKey(), 11);
		System.out.println("Dg.edgeSize()"+Dg.edgeSize());
		Dg.removeNode(node0.getKey());
		System.out.println("Dg.edgeSize() after remove "+Dg.edgeSize());

		if (Dg.nodeSize()!=3) 
		{ 
			fail(); 
		}
		if (Dg.edgeSize()!=2) 
		{ 
			fail(); 
		}
	
		Dg.removeNode(node1.getKey());
		
		if (Dg.nodeSize()!=2) 
		{ 
			fail(); 
		}
		if (Dg.edgeSize()!=0) 
		{ 
			fail(); 
		}
		
	}

	@Test
	void RemoveEdgeTest() 
	{
		graph Dg = new DGraph();
		Point3D p0 = new Point3D(1, 6, 0);
		Point3D p1 = new Point3D(5, 2, 3);
		Point3D p2 = new Point3D(1, 4, 0);

		node_data node0 = new NodeData (0 ,p0 ,5,"gh", 0);
		node_data node1 = new NodeData(1 ,p1 ,6);
		node_data node2 = new NodeData(2 ,p2 ,7);

		Dg.addNode(node0);
		Dg.addNode(node1);
		Dg.addNode(node2);

		Dg.connect(node0.getKey(), node1.getKey(), 9);
		Dg.connect(node1.getKey(), node2.getKey(), 10);

		Dg.removeEdge(node1.getKey(), node2.getKey());
		if (Dg.edgeSize()!=1) 
		{ 
			fail();
		}
		if (Dg.getEdge(node0.getKey(), node1.getKey()) == null) 
		{ 
			fail();
		}
		if (Dg.getEdge(node1.getKey(), node2.getKey()) != null) 
		{ 
			System.out.println("the edge should not be exsist");
			fail();
		}
	}
}

