package com.adx.similaralg;

import com.adx.entity.Trajectory;

public class DTWSimilarity implements SimilarityReactor{
	private Trajectory objTraj;
	private Trajectory testTraj;
	private double similarity;
	//����ʱ����Ĺ켣����
	private void DTWNoTime(){
		
	}
	//��ʱ����Ĺ켣����
	private void DTWWithTime(){
		
	}
	@Override
	public double getSimilarity(Trajectory objTraj, Trajectory testTraj) {
		// TODO Auto-generated method stub
		return similarity;
	}
}
