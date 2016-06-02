package com.adx.similaralg;

import java.util.Comparator;

import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

public abstract class Similarity {
	protected Trajectory testTraj,objTraj;
	protected double[][] raw_dis;
	protected double[][] dtw_dis;
	protected int[][] match;
	public double getSimilarity() {
		return similarity;
	}
	protected int matchsize;
	protected double dis,shape,edit;
	protected long time;
	protected double disS,shapeS,editS,timeS;
	protected double similarity;
	protected int[][] similarer_match;
	protected SimularDef sDef;
	
	public abstract void init(Trajectory objTraj,Trajectory testTraj,SimularDef sDef);
	public abstract void calculate_Params();
	public abstract void calculate_Similarity();
	public abstract void calculate_SimilarerMatch();
	
}

class MatchPoint{
	int t_index,o_index;
	double distance;	
}

class Mycomparator implements Comparator<MatchPoint>{
	public int compare(MatchPoint m1,MatchPoint m2){
		if(m1.distance>m2.distance) return 1;
		else if(m1.distance<m2.distance) return -1;
		else return 0;
	}
}

