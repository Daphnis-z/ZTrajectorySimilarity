package com.adx.datahandler;

import com.adx.entity.Trajectory;

public interface DataHandler {
	public boolean naHandle(Trajectory traj);	//缺失值处理
	
	public boolean exPointHandle(Trajectory traj);//异常的检测
	
	public boolean trajSegmrnt(Trajectory traj);
	
	public Trajectory dataHandle();
}
