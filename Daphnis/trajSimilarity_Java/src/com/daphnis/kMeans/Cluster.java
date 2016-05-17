/**
 * a cluster
 * @author Daphnis
 * 20150515
 */
package com.daphnis.kMeans;

import java.util.*;
import com.adx.entity.*;

public class Cluster {	
	private Vector<Trajectory> trajs;
	private Trajectory centroid;//standard trajectory
	private int id;
	
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
		return this.centroid;
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
	
	/**
	 * clear trajectories
	 */
	public void clear() {
		trajs.clear();
	}
	
	public void showCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid id: " + centroid.getId() + "]");
		System.out.print("[Trajectories id: ");
		for(Trajectory traj : trajs) {
			System.out.print(traj.getId()+" ");
		}
		System.out.println("]\n");
	}

}

