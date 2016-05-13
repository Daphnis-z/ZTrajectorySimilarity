/**
 * author: Daphnis
 */
package com.adx.entity;

import java.util.*;

public class Trajectory implements Cloneable{
	private Vector<Point> points;
    public int timeStamp;
    public int size;

	
	public Trajectory(int timeStamp){
    	this.points=new Vector<Point>();
    	this.timeStamp=timeStamp;
    	
    }
	
	public int getSize() {
		return points.size();
	}
    public Trajectory(Vector<Point> points,int timeStamp){
    	this.points=points;
    	this.timeStamp=timeStamp;
    }
        
    public Vector<Point> getPoints(){
    	return this.points;
    }
        
    public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
     * add a point into this trajectory
     * @param point
     */
    public void addPoint(Point point){
    	points.addElement(point);
    }


}
