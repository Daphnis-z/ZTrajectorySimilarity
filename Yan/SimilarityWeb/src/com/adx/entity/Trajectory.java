/**
 * author: Daphnis
 * 20160726
 */
package com.adx.entity;

import java.util.*;

public class Trajectory implements Cloneable{
	protected  Vector<Point> points;
	public ArrayList<Integer> subTrajs_index;
	public ArrayList<Integer> naIndex;
	public int timeStamp;
	public boolean isNA;
	
	/**
	 * 轨迹编号，唯一标识一条轨迹
	 */
	public int ID;
	
	//轨迹中心点
	private Point centerTraj;
	public Point getCenterTraj() {
		return centerTraj;
	}
	public void setCenterTraj(Point centerTraj) {
		this.centerTraj = centerTraj;
	}
	
	//轨迹长度
	private double trajLen;	
	public double getTrajLen() {
		return trajLen;
	}
	public void setTrajLen(double trajLen) {
		this.trajLen = trajLen;
	}
	
	public Trajectory(int timeStamp){
    	this.points=new Vector<Point>();
    	this.subTrajs_index=new ArrayList<Integer>();
    	this.naIndex=new ArrayList<Integer>();
    	this.timeStamp=timeStamp;
    }
	public Trajectory(){
		this.points=new Vector<Point>();
		this.subTrajs_index=new ArrayList<Integer>();
	}
	public Trajectory(Vector<Point> points,int timeStamp){
		this.subTrajs_index=new ArrayList<Integer>();
		this.points=points;
		this.timeStamp=timeStamp;
	}
	
	public Vector<Trajectory> getSubTrajs() {
		int size=subTrajs_index.size(),corner=0,now=0;
		Vector<Trajectory> subTrajs=new Vector<Trajectory>();
		for(int j=0;j<size;j++){
			Trajectory subTraj=new Trajectory();
			corner=subTrajs_index.get(j);
			while(now!=corner&&now<points.size()){
				subTraj.addPoint(points.get(now));
				now++;
			}
			subTrajs.add(subTraj);
		}
		Trajectory subTraj_last=new Trajectory();
		for(int i=now;i<points.size();i++){
			subTraj_last.addPoint(points.get(i));
		}
		subTrajs.add(subTraj_last);
		return subTrajs;
	}

	public int getSize() {
		return points.size();
	}
        
    public Vector<Point> getPoints(){
    	return this.points;
    }
        
    public void setPoints(Vector<Point> points) {
		this.points = points;
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
    /**
     * add a index into this trajectory
     */
	public void addSubTrajs(int subTraj) {
		subTrajs_index.add(subTraj);
	}

}

