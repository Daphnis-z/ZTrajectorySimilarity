package com.adx.similaralg;

import com.adx.entity.Trajectory;

public class DTWSimilarity implements SimilarityReactor{
	private Trajectory objTraj;
	private Trajectory testTraj;
	private double similarity;
	//不带时间戳的轨迹计算
	private void DTWNoTime(){
		
	}
	//带时间戳的轨迹计算
	private void DTWWithTime(){
		
	}
	@Override
	public double getSimilarity(Trajectory objTraj, Trajectory testTraj) {
		// TODO Auto-generated method stub
		return similarity;
	}
}
