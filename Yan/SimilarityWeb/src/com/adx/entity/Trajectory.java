/**
 * @author Daphnis
 * 20150515
 */
package com.adx.entity;

import java.util.*;

public class Trajectory implements Cloneable{
	private Vector<Point> points;
	private int id;
	private boolean hasTimeStamp;
	
    public int timeStamp;
    public int size;   
	
	public Trajectory(int timeStamp){
    	this.points=new Vector<Point>();
    	this.timeStamp=timeStamp;    	
    }
    public Trajectory(Vector<Point> points,int timeStamp){
    	this.points=points;
    	this.timeStamp=timeStamp;
    }
	public Trajectory(int id,boolean hasTimeStamp){
		this.id=id;
		this.hasTimeStamp=hasTimeStamp;
		points=new Vector<Point>();
	}
    
    public Vector<Point> getPoints(){
    	return this.points;
    }
        
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/**
	 * 获取此轨迹所含点的个数
	 * @return int
	 */
	public int getSize() {
		return points.size();
	}
	
	/**
     * add a point into this trajectory
     * @param point
     */
    public void addPoint(Point point){
    	points.addElement(point);
    }
	
    @Override
	public String toString() {
    	StringBuilder sb=new StringBuilder("[");
    	for(Point p:points){
    		sb.append(p.getLongitude()+","+p.getLatitude()+","+p.getTimestamp()+";");
    	}
    	sb.append(']');
		return "{id:"+id+",size="+this.getSize()+",points=" + sb.toString() + "}";
	}
   
}

