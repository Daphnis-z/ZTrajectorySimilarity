package com.daphnis.kMeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.adx.entity.Point;

public class KMeans {
	
	private final int POINT_NUM_CLUSTER=5;//期待的一个群中含有的点的数量   
    private List<Point> points;
    
    private List<Cluster> clusters;   
    public List<Cluster> getClusters() {
		return clusters;
	}

	public KMeans(List<Point> points) {
    	this.points = points;
    	this.clusters = new ArrayList<Cluster>();    
    }
        
    //Initializes the process
    public void init() {
    	//Create Clusters
    	//Set Random Centroids
    	int clusterCnt=points.size()/POINT_NUM_CLUSTER;
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster cluster = new Cluster(i);
    		Random rd=new Random();
    		Point centroid = points.get(rd.nextInt(points.size()));
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    }

	private void plotClusters() {
    	for (int i = 0; i<clusters.size(); i++) {
    		Cluster c = clusters.get(i);
    		c.plotCluster();
    	}
    }
    
	//The process to calculate the KMeans, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        while(!finish) {       	
        	clearClusters();//Clear cluster state        	
        	List<Point> lastCentroids = getCentroids();
        	assignCluster();
            calculateCentroids();        	
        	iteration++;        	
        	List<Point> currentCentroids = getCentroids();
        	
        	double distance = 0;
        	for(int i = 0; i<lastCentroids.size(); ++i) {
        		distance += twoPointsDistance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	        	
        	if(distance<0.00005) {
        		finish = true;
        	}
        }
    	plotClusters();
    	System.out.println("#######KMeans End##########");
    	System.out.println("Iteration: " + iteration);
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List<Point> getCentroids() {
    	List<Point> centroids = new ArrayList<Point>(clusters.size());
    	for(Cluster cluster : clusters) {
    		Point cent = cluster.getCentroid();
    		Point point = new Point(cent.getLongitude(),cent.getLatitude());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        int clusterNum = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	double min = Double.MAX_VALUE;
            for(int i = 0; i<clusters.size(); ++i) {
            	Cluster cluster = clusters.get(i);
                distance = twoPointsDistance(point, cluster.getCentroid());
                if(distance<min){
                    min = distance;
                    clusterNum = i;
                }
            }
            clusters.get(clusterNum).addPoint(point);
        }
    }
    
    /**
     * Calculate two points' distance
     * @param pt1
     * @param pt2
     * @return
     */
    private double twoPointsDistance(Point pt1,Point pt2){
    	double d1=Math.pow(pt1.getLongitude()-pt2.getLongitude(), 2);
    	double d2=Math.pow(pt1.getLatitude()-pt2.getLatitude(), 2);
    	return d1+d2;
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumLon = 0,sumLat = 0;
            List<Point> points = cluster.getPoints();           
            for(Point point : points) {
            	sumLon += point.getLongitude();
                sumLat += point.getLatitude();
            }
            
            if(points.size()>0) {
            	Point p=new Point(sumLon / points.size(),sumLat / points.size());
            	cluster.setCentroid(p);
            }
        }
    }

}

