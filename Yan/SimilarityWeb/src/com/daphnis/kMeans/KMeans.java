/**
 * modified KMeans
 * @author Daphnis
 * 20150515
 */
package com.daphnis.kMeans;

import java.util.*;
import com.adx.entity.*;
import com.adx.similaralg.*;

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
    
    /**
     * initialize the process
     * create clusters and set random standard trajectory
     */
    public void init() {    	
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster cluster = new Cluster(i);
    		Random rd=new Random();
    		int n=rd.nextInt(trajCnt);
    		Trajectory centroid = trajs.get(n);
    		centroid.setId(n);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	   	
    	showClusters();//Print Initial state
    }

    /**
     * show clusters state
     */
	private void showClusters() {
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster c = clusters.get(i);
    		c.showCluster();
    	}
    }
    
	/**
	 * The process to calculate the KMeans with iterating method.
	 */
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
        		simi += SimpleDTW.DTW(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid similarity: " + simi);
        	showClusters();
        	   
        	//判断是否已收敛，若收敛则结束迭代
        	if(Math.abs(simi-1.0)<0.0005) {
        		finish = true;
        	}
        }
    }
    
    /**
     * clear clusters
     */
    private void clearClusters() {
    	for(Cluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private Vector<Trajectory> getCentroids() {
    	Vector<Trajectory> centroids = new Vector<Trajectory>();
    	for(Cluster cluster : clusters) {
    		centroids.add(cluster.getCentroid());
    	}
    	return centroids;
    }  
    
    private void assignCluster() {
    	for(Trajectory traj:trajs){
        	double simi=0.0,tmp=0.0;
        	int cluster=0;
        	for(int i=0;i<clusterCnt;++i){
        		tmp=SimpleDTW.DTW(traj, clusters.get(i).getCentroid());
        		if(simi<tmp){
        			simi=tmp;
        			cluster=i;
        		}
        	}
        	traj.setClusterNum(cluster);
        	clusters.get(cluster).addTraj(traj);
    	}
    }
    
    /**
     * 计算标准轨迹
     */
    private void calculateCentroids() {
    	for(Cluster clu:clusters){
    		int cnt=clu.getTrajs().size();
    		double[] simis=new double[cnt];
    		for(int i=0;i<cnt-1;++i){
    			for(int j=i+1;j<cnt;++j){
    				double simi=SimpleDTW.DTW(clu.getTrajs().get(i), clu.getTrajs().get(j));
    				simis[i]+=simi;
    				simis[j]+=simi;
    			}
    		}
    		clu.setCentroid(clu.getTrajs().get(findMax(simis)));
    	}
    }
    private int findMax(double[] arr){
    	int ix=0;
    	double val=arr[0];
    	for(int i=1;i<arr.length;++i){
    		if(val<arr[i]){
    			val=arr[i];
    			ix=i;
    		}
    	}
    	return ix;
    }
    
}


