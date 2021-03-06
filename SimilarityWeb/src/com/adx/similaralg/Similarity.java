package com.adx.similaralg;

import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

public abstract class Similarity {
	protected Trajectory testTraj,objTraj;
	protected double[][] raw_dis;
	protected double[][] raw_time;
	protected double[][] dtw_dis;
	protected int[][] match;
	protected int matchsize;
	protected double dis,shape,edit;
	protected long time;
	protected double disS,shapeS,editS,timeS;
	protected double similarity;
	protected Trajectory []similarestTraj;
	protected Point []similarestPoint;

	protected SimularDef sDef;
	
	public double getSimilarity() {
		return similarity;
	}	
	
	public Trajectory[] getSimilarestTraj() {
		return similarestTraj;
	}

	public Point[] getSimilarestPoint() {
		return similarestPoint;
	}
	
	public int[][] getMatch() {
		return match;
	}

	public int getMatchsize() {
		return matchsize;
	}
	

	public abstract void init(Trajectory objTraj,Trajectory testTraj,SimularDef sDef);
	public abstract void calculate_Params();
	public abstract void calculate_Similarity();
	public abstract void calculate_SimilarerMatch();
	
}




