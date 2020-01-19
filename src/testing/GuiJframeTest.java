package testing;

import org.junit.jupiter.api.Test;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.graph;
import dataStructure.node_data;
import gui.GUI_JFRAME;
import utils.Point3D;

class GuiJframeTest {

	@Test
	void GUITest() 
	{
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

//		Dgraph.removeNode(n6.getKey());
//		GuiG.repaint();
//		Dgraph.removeEdge(n5.getKey(), n3.getKey());
//		GuiG.repaint();

	}
}

