/**
 * modified KMeans
 * author: Daphnis
 */
package daphnis.kMeans;

import java.util.*;

public class KMeans {
    private int clusterCnt;//cluster count    
    private int trajCnt;//trajectory count
    
    private Vector<Trajectory> trajs;
    private Vector<Cluster> clusters;
    
    public KMeans(Vector<Trajectory> trajs,int clusterCnt) {
    	this.trajs=trajs;
    	this.clusterCnt=clusterCnt;
    	this.clusters = new Vector<Cluster>();
    	this.trajCnt=trajs.size();
    }
        
    //Initializes the process
    public void init() {    	
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster cluster = new Cluster(i);
    		Random rd=new Random();
    		Trajectory centroid = trajs.get(rd.nextInt(trajCnt));
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }

	private void plotClusters() {
//    	for (int i = 0; i &lt; NUM_CLUSTERS; i++) {
//    		Cluster c = clusters.get(i);
//    		c.plotCluster();
//    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
//        boolean finish = false;
//        int iteration = 0;
//        
//        // Add in new data, one at a time, recalculating centroids with each new one. 
//        while(!finish) {
//        	//Clear cluster state
//        	clearClusters();
//        	
//        	Vector<Trajectory> lastCentroids = getCentroids();
//        	
//        	//Assign points to the closer cluster
//        	assignCluster();
//            
//            //Calculate new centroids.
//        	calculateCentroids();
//        	
//        	iteration++;
//        	
//        	List currentCentroids = getCentroids();
//        	
//        	//Calculates total distance between new and old Centroids
//        	double distance = 0;
//        	for(int i = 0; i &lt; lastCentroids.size(); i++) {
//        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
//        	}
//        	System.out.println("#################");
//        	System.out.println("Iteration: " + iteration);
//        	System.out.println("Centroid distances: " + distance);
//        	plotClusters();
//        	        	
//        	if(distance == 0) {
//        		finish = true;
//        	}
//        }
    }
    
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private Vector<Trajectory> getCentroids() {
    	Vector<Trajectory> centroids = new Vector<Trajectory>();
//    	for(Cluster cluster : clusters) {
//    		Point aux = cluster.getCentroid();
//    		Point point = new Point(aux.getX(),aux.getY());
//    		centroids.add(point);
//    	}
    	return centroids;
    }
    
    private void assignCluster() {
//        double max = Double.MAX_VALUE;
//        double min = max; 
//        int cluster = 0;                 
//        double distance = 0.0; 
//        
//        for(Point point : points) {
//        	min = max;
//            for(int i = 0; i &lt; NUM_CLUSTERS; i++) {
//            	Cluster c = clusters.get(i);
//                distance = Point.distance(point, c.getCentroid());
//                if(distance &lt; min){
//                    min = distance;
//                    cluster = i;
//                }
//            }
//            point.setCluster(cluster);
//            clusters.get(cluster).addPoint(point);
//        }
    }
    
    private void calculateCentroids() {
//        for(Cluster cluster : clusters) {
//            double sumX = 0;
//            double sumY = 0;
//            List list = cluster.getPoints();
//            int n_points = list.size();
//            
//            for(Point point : list) {
//            	sumX += point.getX();
//                sumY += point.getY();
//            }
//            
//            Point centroid = cluster.getCentroid();
//            if(n_points &gt; 0) {
//            	double newX = sumX / n_points;
//            	double newY = sumY / n_points;
//                centroid.setX(newX);
//                centroid.setY(newY);
//            }
//        }
    }
}
