package com.adx.datahandler;

import com.adx.entity.Trajectory;

public class DataHandler   {
	private Trajectory traj;
	private int segSyn;
	
	public DataHandler(Trajectory traj,int segSyn) {
		this.traj=traj;
		this.segSyn=segSyn;
	}
	public DataHandler(Trajectory traj) {
		this.traj=traj;
		segSyn=-1;
	}

	public boolean exPointHandle(Trajectory traj) {
    	KMeans kmeans = new KMeans(traj.getPoints());
    	if(kmeans.init()){
	    	kmeans.calculate();
	    	kmeans.removeUnusefulPoints();	    	
	    	kmeans.dataCompression();
    	}
		return true;
	}

	public boolean trajSegmrnt(Trajectory traj) {
		TrajectorySegment seg=new TrajectorySegment(traj);
		seg.trajSegment();
		if(segSyn!=-1){
			seg.trajSynch(segSyn);
		}
		return false;
	}

	public Trajectory dataHandle() {
		trajSegmrnt(traj);
		exPointHandle(traj);
		return traj;
	}

}
