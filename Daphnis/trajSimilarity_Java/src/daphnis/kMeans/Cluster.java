/**
 * a cluster
 * author: Daphnis
 */
package daphnis.kMeans;

import java.util.*;

public class Cluster {	
	public Vector<Trajectory> trajs;
	public Trajectory centroid;//standard trajectory
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.trajs = new Vector<Trajectory>();
		this.centroid = null;
	}
		
	public Vector<Trajectory> getTrajs() {
		return this.trajs;
	}
	public void setTrajs(Vector<Trajectory> trajs) {
		this.trajs=trajs;
	}

	public Trajectory getCentroid() {
		return centroid;
	}
	public void setCentroid(Trajectory centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}
	
	/**
	 * add a trajectory into this cluster
	 * @param traj
	 */
	public void addTraj(Trajectory traj) {
		trajs.addElement(traj);
	}
	
	public void clear() {
		trajs.clear();
	}
	
	public void plotCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid id: " + centroid.getId() + "]");
		System.out.println("[Trajectories id: \n");
		for(Trajectory traj : trajs) {
			System.out.println(traj.getId());
		}
		System.out.println("]");
	}

}
