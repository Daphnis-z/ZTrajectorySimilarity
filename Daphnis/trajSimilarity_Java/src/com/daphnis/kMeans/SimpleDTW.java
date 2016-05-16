package com.daphnis.kMeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDTW {
	private double[][] raw_dis=new double[100][100];//该矩阵维护两点之间欧氏距离
	private double[][] dtw_dis=new double[100][100];//该矩阵维护dtw距离
	public static int[][] tip=new int[199][2];//该矩阵维护匹配点
	
	//不带时间戳的轨迹计算
	public static double DTW(Trajectory objTraj,Trajectory testTraj){
		int objTrajSize=objTraj.getSize(); //目标轨迹的长度
		int testTrajSize=testTraj.getSize(); //测试轨迹的长度
		
		raw_dis=SimilarityUtility.traj_distance(objTraj, testTraj);
		
		dtw_dis[0][0]=raw_dis[0][0];//dtw距离矩阵初始化
		for(int i=1;i<testTrajSize;i++)
		{
			dtw_dis[i][0]=raw_dis[i][0]+dtw_dis[i-1][0];
		}
		for(int j=1;j<objTrajSize;j++)
		{
			dtw_dis[0][j]=raw_dis[0][j]+dtw_dis[0][j-1];
		}
		for(int i=1;i<testTrajSize;i++)//计算dtw距离矩阵
		{
			for(int j=1;j<objTrajSize;j++)
			{
				dtw_dis[i][j]=raw_dis[i][j]+SimilarityUtility.min(dtw_dis[i-1][j],
							dtw_dis[i][j-1],dtw_dis[i-1][j-1]);
			}
		}
		
		return dtw_dis[testTrajSize-1][objTrajSize];		
	}
	
}

