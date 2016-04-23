/**
 * modified KMeans
 * author: Daphnis
 */
package daphnis.kMeans;

import java.util.*;

public class KMeans {
    private int clusterCnt;//cluster count    
    private int trajCnt;//trajectory count   
    private Vector<Trajectory> trajs;//all trajectories
    private Vector<Cluster> clusters;//存放聚类结果
    
    public KMeans(Vector<Trajectory> trajs,int clusterCnt) {
    	this.trajs=trajs;
    	this.clusterCnt=clusterCnt;
    	this.clusters = new Vector<Cluster>();
    	this.trajCnt=trajs.size();
    }
        
    //Initializes the process
    public void init() {    	
    	//Create Clusters and set random standard trajectory
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster cluster = new Cluster(i);
    		Random rd=new Random();
    		int n=rd.nextInt(trajCnt);
    		Trajectory centroid = trajs.get(n);
    		centroid.setId(n);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	   	
    	plotClusters();//Print Initial state
    }

	private void plotClusters() {
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster c = clusters.get(i);
    		c.plotCluster();
    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {       	
        	clearClusters();//Clear cluster state
        	
        	Vector<Trajectory> lastCentroids = getCentroids();        	
            assignCluster();//Assign trajectories to the closer cluster                       
        	calculateCentroids();//Calculate new centroids 
        	
        	iteration++;       	
        	Vector<Trajectory> currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double simi = 0;//calculate similarity
        	for(int i = 0; i<lastCentroids.size(); i++) {
        		simi += Trajectory.calSimilarity(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid similarity: " + simi);
        	plotClusters();
        	   
        	//判断是否已收敛，若收敛则结束迭代
        	if(Math.abs(simi-1.0)<0.0005) {
        		finish = true;
        	}
        }
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
