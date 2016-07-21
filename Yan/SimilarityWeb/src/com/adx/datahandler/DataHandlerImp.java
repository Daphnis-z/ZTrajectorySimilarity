package com.adx.datahandler;

import com.adx.entity.Trajectory;

public class DataHandlerImp implements DataHandler {
	private Trajectory traj;
	private int segSyn;
	
	public DataHandlerImp(Trajectory traj,int segSyn) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
		this.segSyn=segSyn;
	}
	public DataHandlerImp(Trajectory traj) {
		// TODO Auto-generated constructor stub
		this.traj=traj;
		segSyn=-1;
	}

	@Override
	public boolean naHandle(Trajectory traj) {
		// TODO Auto-generated method stub
		if(traj.isNA){
			NAValueHandler na_obj=new NAValueHandler(traj);
			na_obj.NAHandle();
			traj=na_obj.getTraj();		
		}
		return true;
	}

	@Override
	public boolean exPointHandle(Trajectory traj) {
		// TODO Auto-generated method stub
    	KMeans kmeans = new KMeans(traj.getPoints());
    	kmeans.init();
    	kmeans.calculate();
    	kmeans.removeUnusefulPoints();
//    	kmeans.dataCompression();
		return true;
	}

	@Override
	public boolean trajSegmrnt(Trajectory traj) {
		// TODO Auto-generated method stub
		TrajectorySegment seg=new TrajectorySegment(traj);
		seg.trajSegment();
		System.out.println("ÇÐ¸îµÄ¶ÎÊý"+traj.getSubTrajs().size());
		if(segSyn!=-1){
			seg.trajSynch(segSyn);
		}
		return false;
	}

	@Override
	public Trajectory dataHandle() {
		// TODO Auto-generated method stub
		naHandle(traj);
		exPointHandle(traj);
		trajSegmrnt(traj);
		return traj;
	}

}
