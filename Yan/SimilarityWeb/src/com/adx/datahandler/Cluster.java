/**
 * Cluster for point
 * @author Daphnis
 * 20160522
 */
package com.adx.datahandler;

import java.util.ArrayList;
import java.util.List;
import com.adx.entity.Point;

public class Cluster {	
	private List<Point> points;
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	private Point centroid;//中心点
	public Point getCentroid() {
		return centroid;
	}
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}
	
	private int cid;//群编号	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	
	public Cluster(int cid) {
		this.cid = cid;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}
		
	/**
	 * Add a point into this cluster
	 * @param point
	 */
	public void addPoint(Point point) {
		points.add(point);
	}
	
	/**
	 * Clear points of this cluster
	 */
	public void clear() {
		points.clear();
	}
	
	/**
	 * Print cluster state
	 */
	public void plotCluster() {
		System.out.println("[Cluster: " +(cid+1)+"]");
		System.out.println("[Centroid: " + centroid.getLongitude()+','+centroid.getLatitude() + "]");
		System.out.println("[Points:");
		for(Point p : points) {
			System.out.println(String.format("\t(%f,%f)", p.getLongitude(),p.getLatitude()));
		}
		System.out.println("]");
	}

}

