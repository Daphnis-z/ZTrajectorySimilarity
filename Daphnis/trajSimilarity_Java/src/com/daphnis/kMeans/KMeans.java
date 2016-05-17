/**
 * modified KMeans
 * @author Daphnis
 * 20150515
 */
package com.daphnis.kMeans;

import java.util.*;
import com.adx.entity.*;
import com.adx.similarity.*;

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
    		
    		//随机设置每个群的标准轨迹
    		Random rd=new Random();
    		int n=rd.nextInt(trajCnt);
    		Trajectory centroid = trajs.get(n);
    		centroid.setId(n);
    		centroid.clusterNum=i;
    		cluster.setCentroid(centroid);
    		
    		clusters.add(cluster);
    	}
    	   	
    	showClusters();//Print Initial state
    	System.out.println("The clusters have initialized..");
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
        	
        	System.out.println("\n#########CLUSTERS############");
        	showClusters();
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid similarity: " + simi);
        	System.out.println("###########END###############");
        	   
        	//判断是否已收敛，若收敛则结束迭代
        	if(simi<0.0005) {
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
    
    /**
     * 获取标准轨迹
     * @return
     */
    private Vector<Trajectory> getCentroids() {
    	Vector<Trajectory> centroids = new Vector<Trajectory>();
    	for(Cluster cluster : clusters) {
    		centroids.add(cluster.getCentroid());
    	}
    	return centroids;
    }  
    
    /**
     * 轨迹分群
     */
    private void assignCluster() {
    	for(Trajectory traj:trajs){
        	double simi=100000.0,tmp=0.0;
        	int cluster=0;
        	for(int i=0;i<clusterCnt;++i){
        		tmp=SimpleDTW.DTW(traj, clusters.get(i).getCentroid());
        		if(simi>tmp){
        			simi=tmp;
        			cluster=i;
        		}
        	}         	
        	traj.clusterNum=cluster;        	        	
        	clusters.get(cluster).addTraj(traj);
    	}
    }
    
    /**
     * 计算标准轨迹
     */
    private void calculateCentroids() {
    	for(Cluster clu:clusters){
    		int cnt=clu.getTrajs().size();
    		if(cnt==0){
    			continue;
    		}
    		double[] simis=new double[cnt];
    		for(int i=0;i<cnt-1;++i){
    			for(int j=i+1;j<cnt;++j){
    				double simi=SimpleDTW.DTW(clu.getTrajs().get(i), clu.getTrajs().get(j));
    				simis[i]+=simi;
    				simis[j]+=simi;
    			}
    		}    		
    		clu.setCentroid(clu.getTrajs().get(findMin(simis)));
    	}
    }
    private int findMin(double[] arr){
    	if(arr.length==0){
    		return -1;
    	}
    	int ix=0;
    	double val=arr[0];
    	for(int i=1;i<arr.length;++i){
    		if(val>arr[i]){
    			val=arr[i];
    			ix=i;
    		}
    	}
    	return ix;
    }
    
}

