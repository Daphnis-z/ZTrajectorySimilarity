/**
 * KMeans for point
 * @author Daphnis
 * 20160721
 */
package com.adx.datahandler;

import java.util.ArrayList;
import java.util.List;
import com.adx.entity.Point;

public class KMeans {
	private final int POINT_NUM_CLUSTER=5;//期待的一个群中含有的点的数量   
	private final double SLOPE_DIFF;//斜率阈值，用于判定在同一条直线上
    private List<Point> points;//保存轨迹所有的点
    
    private List<Cluster> clusters;   
    public List<Cluster> getClusters() {
		return clusters;
	}

	public KMeans(List<Point> points) {
    	this.points = points;
    	
    	//计算斜率差的阈值
    	SLOPE_DIFF=calSDThreshold(this.points);
    	
    	this.clusters = new ArrayList<Cluster>();    
    }
        
    /**
     * 初始化
     * 若初始化失败（返回false）
     * 则应该停止进行后面的操作
     */
    public boolean init() {
    	if(points.size()<POINT_NUM_CLUSTER){
    		return false;
    	}
    	    	
    	//初始化群并设定中心点
    	int clusterCnt=points.size()/POINT_NUM_CLUSTER;
    	for (int i = 0; i<clusterCnt; i++) {
    		Cluster cluster = new Cluster(i);    		
    		Point centroid = points.get(i*POINT_NUM_CLUSTER);   		
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	} 
    	return true;
    }
    
	/**
	 * 执行计算
	 */
    public void calculate() {
        boolean finish = false;        
        while(!finish) { 
        	clearClusters();//Clear cluster state        	
        	List<Point> lastCentroids = getCentroids();
        	assignCluster();
            calculateCentroids();        	
        	List<Point> currentCentroids = getCentroids();
        	
        	double distance = 0;
        	for(int i = 0; i<lastCentroids.size(); ++i) {
        		distance += twoPointsDistance(lastCentroids.get(i),currentCentroids.get(i));
        	}        	        	
        	if(distance<0.00005) {
        		finish = true;
        	}
        }
    }
    
    /**
     * 移除离群的点
     */
	public void removeUnusefulPoints(){
    	for(Cluster cluster:clusters){
    		if(cluster.getPoints().size()==1){
    			points.remove(cluster.getPoints().get(0));
    		}
    	}
    }
	
	/**
	 * 数据压缩
	 */
	public void dataCompression(){	
    	for(Cluster cluster:clusters){
			List<Point> tpts=cluster.getPoints();
    		if(tpts.size()>=3){
    			for(int i=0;i<tpts.size()-2;i+=3){
    				Point[] pts={tpts.get(i),tpts.get(i+1),tpts.get(i+2)};
    				sortPoints(pts);
    				if(calSlopeDiff(pts[0],pts[1],pts[2])<SLOPE_DIFF){
    					if(!checkIsInflectionPoint(pts[0])){
    						points.remove(pts[0]);
    					}else{
    						points.remove(pts[1]);
    					}
    					if(!checkIsInflectionPoint(pts[2])){    					    						
    						points.remove(pts[2]);
    					}
    				}   				
    			}   			
    		}
    	}
	}
		
	/**
	 * 计算斜率差的阈值
	 * @param points
	 * @return
	 */
	private double calSDThreshold(List<Point> points){
		final int NUM=5;
		int len=points.size();
		int dif=len/NUM;
		if(dif<3){
			return Double.MAX_VALUE;
		}
		
		double maxsd=-100000,sum=0;;
		for(int i=0;i<len-2;i+=dif){
			double tmp=calSlopeDiff(points.get(i),points.get(i+1),points.get(+2));
			maxsd=maxsd<tmp? tmp:maxsd;
			sum+=tmp;
		}
		return (sum-maxsd)/(NUM-1);
	}
	
	/**
	 * 计算斜率差
	 * @param pt1
	 * @param pt2
	 * @param pt3
	 * @return
	 */
	private double calSlopeDiff(Point pt1,Point pt2,Point pt3){
		double m1=(pt2.getLatitude()-pt1.getLatitude())*(pt3.getLongitude()-pt2.getLongitude());
		double m2=(pt3.getLatitude()-pt2.getLatitude())*(pt2.getLongitude()-pt1.getLongitude());
		return Math.abs(m1-m2);
	}
	
	//检查该点是否是拐点
	private boolean checkIsInflectionPoint(Point pt){
		if(pt.pid-1>=0&&pt.pid+1<points.size()){
			if(calSlopeDiff(points.get(pt.pid-1),pt,points.get(pt.pid+1))<SLOPE_DIFF){
				return false;
			}
		}
		return true;
	}	
	//对一个点的数组按经纬度排序
	private void sortPoints(Point[] pts){
		for(int i=0;i<pts.length-1;++i){
			for(int j=i+1;j<pts.length-i;++j){
				if(pts[i].getLongitude()>pts[j].getLongitude()||pts[i].getLatitude()>pts[j].getLatitude()){
					Point p=pts[i];
					pts[i]=pts[j];
					pts[j]=p;
				}
			}
		}
	}
    
	
    //Rset clusters
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
    
    //Calculate two points' distance
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
    
    /**
     * Print clusters on console
     */
//	private void showClusters() {
//    	for (int i = 0; i<clusters.size(); i++) {
//    		Cluster c = clusters.get(i);
//    		c.plotCluster();
//    	}
//    }
	
}

