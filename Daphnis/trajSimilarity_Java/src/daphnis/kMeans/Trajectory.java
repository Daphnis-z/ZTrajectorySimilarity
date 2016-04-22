/**
 * trajectory
 * author: Daphnis
 */
package daphnis.kMeans;

import java.util.*;

public class Trajectory {
	private Vector<Point> points;
    private int clusterNum = 0;
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
    
//    //Creates random point
//    protected static Point createRandomPoint(int min, int max) {
//    	Random r = new Random();
//    	double x = min + (max - min) * r.nextDouble();
//    	double y = min + (max - min) * r.nextDouble();
//    	return new Point(x,y);
//    }
//    
//    protected static List createRandomPoints(int min, int max, int number) {
//    	List points = new ArrayList(number);
//    	for(int i = 0; i &lt; number; i++) {
//    		points.add(createRandomPoint(min,max));
//    	}
//    	return points;
//    }
    
    public String toString() {    	
    	StringBuilder ts=new StringBuilder("");
    	for(Point pt:points){
    		if(hasTimestamp){
    			ts.append(String.format("[%f,%f,%s]", pt.getLongitude(),pt.getLatitude(),
    					pt.getTimestamp()));
    		}
    		else{
    			ts.append(String.format("[%f,%f]", pt.getLongitude(),pt.getLatitude()));
    		}
    	}
    	return ts.toString();
    }

}
