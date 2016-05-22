package com.daphnis.kMeans;

import java.util.ArrayList;
import java.util.List;
import com.adx.entity.Point;

public class Cluster {
	
	private List<Point> points;
	private Point centroid;//中心点
	
	public int clusterNum;//群编号
	
	public Cluster(int clusterNum) {
		this.clusterNum = clusterNum;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public Point getCentroid() {
		return centroid;
	}
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
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
	
	public void plotCluster() {
		System.out.println("[Cluster: " +(clusterNum+1)+"]");
		System.out.println("[Centroid: " + centroid.getLongitude()+','+centroid.getLatitude() + "]");
		System.out.println("[Points:");
		for(Point p : points) {
			System.out.println(String.format("\t(%f,%f)", p.getLongitude(),p.getLatitude()));
		}
		System.out.println("]");
	}

}

