/**
 * trajectory
 * author: Daphnis
 */
package daphnis.kMeans;

import java.util.*;

public class Trajectory implements Cloneable{
	private Vector<Point> points;
    private int clusterNum = 0;
    private int id=-1;//trajectory num
    public boolean hasTimestamp=false;

    public Trajectory(){
    	this.points=new Vector<Point>();
    }
    public Trajectory(Vector<Point> points){
    	this.points=points;
    }
        
    public Vector<Point> getPoints(){
    	return this.points;
    }
        
    public void setClusterNum(int n) {
        this.clusterNum = n;
    }  
    public int getClusterNum() {
        return this.clusterNum;
    }
        
    public void setId(int id){
    	this.id=id;
    }
    public int getId(){
    	return this.id;
    }
    
    /**
     * add a point into this trajectory
     * @param point
     */
    public void addPoint(Point point){
    	points.addElement(point);
    }

    /**
     * calculate the similarity of two trajectories 
     * @param traj1
     * @param traj2
     * @return similarity
     */
    public static double calSimilarity(Trajectory traj1,Trajectory traj2){
    	return -1;
    }
        
    public String toString() {    	
    	StringBuilder ts=new StringBuilder(String.format("id: %d\n\t", id));
    	for(Point pt:points){
    		if(hasTimestamp){
    			ts.append(String.format("[%f,%f,%s]",pt.getLongitude(),pt.getLatitude(),
    					pt.getTimestamp()));
    		}
    		else{
    			ts.append(String.format("[%f,%f]",pt.getLongitude(),pt.getLatitude()));
    		}
    	}
    	return ts.toString();
    }

//    @Override
//    public Object clone(){
//    	Trajectory traj=null;
//    	try{
//    		traj=(Trajectory)super.clone();
//    	}catch(CloneNotSupportedException e){
//    		e.printStackTrace();
//    	}
//    	traj.points=(Vector<Point>)this.points.clone();
//    	return traj;
//    }
}
