package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class NodeData implements node_data,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Key;
	private Point3D Location;
	private double Weight;
	private String Info;
	private int Tag;
	private int hasFruit;
	

	public NodeData() {
		this.Key=0;
		this.Weight=0;
		this.Info="";
		this.Tag=0;
		this.Location=null; 
		this.hasFruit=0; 
	}
	
	public NodeData (int key) {
		this.Key=key; 
		this.Weight=0;
		this.Info="";
		this.Tag=0;
		this.Location=null; 
	}

	public NodeData (int key , double w) {
		this.Key=key; 
		this.Location=null; 
		this.Weight=w;
		this.Info="";
		this.Tag=0;
		
	}
	
	public NodeData(int k ,Point3D p) {
		this.Key=k; 
		this.Location=p;
		this.Weight=0;
		this.Info="";
		this.Tag=0;
	}
	
	public NodeData(int k , Point3D p , double w) {
		this.Key=k; 
		this.Location=p;
		this.Weight=w;
		this.Info="";
		this.Tag=0;
	}
	
	public NodeData(int k , Point3D p , double w , String i , int t) {
		this.Key=k; 
		this.Location=p;
		this.Weight=w;
		this.Info=i;
		this.Tag=t;
	}

	public NodeData(node_data nd) 
	{
		this.Key=nd.getKey(); 
		this.Location=nd.getLocation();
		this.Weight=nd.getWeight();
		this.Info=nd.getInfo();
		this.Tag=nd.getTag();
	}

	@Override
	public int getKey() {

		return this.Key;
	}


	@Override
	public Point3D getLocation() {
		return this.Location;
	}
	public int gethasFruit() {
		return this.hasFruit;
	}
	@Override
	public void setLocation(Point3D p) {
		this.Location=p;
	}
	
	public void sethasFruit(int i) {
		this.hasFruit=i;
	}

	@Override
	public double getWeight() {
		return this.Weight;
	}

	@Override
	public void setWeight(double w) {
		this.Weight=w;
	}

	@Override
	public String getInfo() {
		return this.Info;
	}

	@Override
	public void setInfo(String s) {
		this.Info=s;
	}

	@Override
	public int getTag() {
		return this.Tag;
	}

	@Override
	public void setTag(int t) {
		this.Tag=t;
	}

}
