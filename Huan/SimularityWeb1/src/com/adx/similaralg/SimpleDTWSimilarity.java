package com.adx.similaralg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

public class SimpleDTWSimilarity implements SimilarityReactor{
	private double[][] raw_dis=new double[100][100];//�þ���ά������֮��ŷ�Ͼ���
	private double[][] dtw_dis=new double[100][100];//�þ���ά��dtw����
	public static int[][] tip=new int[199][2];//�þ���ά��ƥ���
	private double similarity;
	private SimularDef sDef;
	public SimpleDTWSimilarity(SimularDef sDef) {
		// TODO Auto-generated constructor stub
		this.sDef=sDef;
	}
	//����ʱ����Ĺ켣����
	private void DTW(Trajectory objTraj,Trajectory testTraj){
		int objTrajSize=objTraj.getSize(); //Ŀ��켣�ĳ���
		int testTrajSize=testTraj.getSize(); //���Թ켣�ĳ���
		
		raw_dis=SimilarityUtility.traj_distance(objTraj, testTraj);
		
		dtw_dis[0][0]=raw_dis[0][0];//dtw��������ʼ��
		for(int i=1;i<testTrajSize;i++)
		{
			dtw_dis[i][0]=raw_dis[i][0]+dtw_dis[i-1][0];
		}
		for(int j=1;j<objTrajSize;j++)
		{
			dtw_dis[0][j]=raw_dis[0][j]+dtw_dis[0][j-1];
		}
		for(int i=1;i<testTrajSize;i++)//����dtw�������
		{
			for(int j=1;j<objTrajSize;j++)
			{
				dtw_dis[i][j]=raw_dis[i][j]+SimilarityUtility.min(dtw_dis[i-1][j],
							dtw_dis[i][j-1],dtw_dis[i-1][j-1]);
			}
		}
		
		similarity=dtw_dis[testTrajSize-1][objTrajSize];
		
	}
	
	@Override
	public double getSimilarity(Trajectory objTraj, Trajectory testTraj,int timestamp) {
		// TODO Auto-generated method stub
		DTW(objTraj,testTraj);
		return similarity;
	}
}
