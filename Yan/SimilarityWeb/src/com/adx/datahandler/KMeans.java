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
	private final int POINT_NUM_CLUSTER=5;//�ڴ���һ��Ⱥ�к��еĵ������   
	private final double SLOPE_DIFF;//б����ֵ�������ж���ͬһ��ֱ����
    private List<Point> points;//����켣���еĵ�
    
    private List<Cluster> clusters;   
    public List<Cluster> getClusters() {
		return clusters;
	}

	public KMeans(List<Point> points) {
    	this.points = points;
    	
    	//����б�ʲ����ֵ
    	SLOPE_DIFF=calSDThreshold(this.points);
    	
    	this.clusters = new ArrayList<Cluster>();    
    }
        
    /**
     * ��ʼ��
     * ����ʼ��ʧ�ܣ�����false��
     * ��Ӧ��ֹͣ���к���Ĳ���
     */
    public boolean init() {
    	if(points.size()<POINT_NUM_CLUSTER){
    		return false;
    	}
    	    	
    	//��ʼ��Ⱥ���趨���ĵ�
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
	 * ִ�м���
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
     * �Ƴ���Ⱥ�ĵ�
     */
	public void removeUnusefulPoints(){
    	for(Cluster cluster:clusters){
    		if(cluster.getPoints().size()==1){
    			points.remove(cluster.getPoints().get(0));
    		}
    	}
    }
	
	/**
	 * ����ѹ��
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
	 * ����б�ʲ����ֵ
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
	 * ����б�ʲ�
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
	
	//���õ��Ƿ��ǹյ�
	private boolean checkIsInflectionPoint(Point pt){
		if(pt.pid-1>=0&&pt.pid+1<points.size()){
			if(calSlopeDiff(points.get(pt.pid-1),pt,points.get(pt.pid+1))<SLOPE_DIFF){
				return false;
			}
		}
		return true;
	}	
	//��һ��������鰴��γ������
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

