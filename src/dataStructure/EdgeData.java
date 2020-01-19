package dataStructure;

import java.io.Serializable;

public class EdgeData implements edge_data, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private int key;
	private double Weight;
	private int Source;
	private  int Destination;
	private String Info;
	private int Tag;

	public EdgeData(){
		this.Weight=-1;
		this.Source=0;
		this.Destination=0;
		this.Info="";
		this.Tag=0;
	}

	public EdgeData(int src , int dst , double weight){
		this.Weight=weight;
		this.Source=src;
		this.Destination=dst;
		
	}
	
	public EdgeData(int src , int dst ){
		this.Source=src;
		this.Destination=dst;
		
	}
	
	public EdgeData(int src , int dst , double weight, String S , int tag){
		this.Weight=weight;
		this.Source=src;
		this.Destination=dst;
		this.Info=S;
		this.Tag=tag;
	}

	@Override
	public int getSrc() {
		return this.Source;
	}

	@Override
	public int getDest() {
		return this.Destination;
	}

	@Override
	public double getWeight() {
		return this.Weight;
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

	public void setWeight(double W) {
		this.Weight=W;		
	}
}
