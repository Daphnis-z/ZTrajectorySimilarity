package com.adx.datahandler;

import com.adx.entity.Trajectory;

public interface DataHandler {
	public boolean naHandle(Trajectory traj);	//ȱʧֵ����
	
	public boolean exPointHandle(Trajectory traj);//�쳣�ļ��
	
	public boolean trajSegmrnt(Trajectory traj);
	
	public Trajectory dataHandle();
}
