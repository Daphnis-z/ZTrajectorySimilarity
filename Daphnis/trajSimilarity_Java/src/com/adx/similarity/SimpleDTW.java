package com.adx.similarity;

import com.adx.entity.Trajectory;

public class SimpleDTW {
	
	/**
	 * ���㲢���������켣�����ƶȣ�DTW���룩
	 * ����ʱ���
	 * @param objTraj
	 * @param testTraj
	 * @return double
	 */
	public static double DTW(Trajectory objTraj,Trajectory testTraj){
		double[][] raw_dis=new double[100][100];//�þ���ά������֮��ŷ�Ͼ���
		double[][] dtw_dis=new double[100][100];//�þ���ά��dtw����
		int objTrajSize=objTraj.getSize(); //Ŀ��켣�ĳ���
		int testTrajSize=testTraj.getSize(); //���Թ켣�ĳ���		
		raw_dis=SimilarityUtility.traj_distance(objTraj, testTraj);
		
		dtw_dis[0][0]=raw_dis[0][0];//dtw��������ʼ��
		for(int i=1;i<testTrajSize;i++){
			dtw_dis[i][0]=raw_dis[i][0]+dtw_dis[i-1][0];
		}
		for(int j=1;j<objTrajSize;j++){
			dtw_dis[0][j]=raw_dis[0][j]+dtw_dis[0][j-1];
		}
		
		//����dtw�������
		for(int i=1;i<testTrajSize;i++){
			for(int j=1;j<objTrajSize;j++)
			{
				dtw_dis[i][j]=raw_dis[i][j]+SimilarityUtility.min(dtw_dis[i-1][j],
							dtw_dis[i][j-1],dtw_dis[i-1][j-1]);
			}
		}
		
		return dtw_dis[testTrajSize-1][objTrajSize-1];		
	}

}

