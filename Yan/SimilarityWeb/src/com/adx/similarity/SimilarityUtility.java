package com.adx.similarity;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * SimilarityUtility
 * @author Agnes
 *�������ƶȵĹ����࣬��һЩ�����ķ���
 */
public class SimilarityUtility {
	//�������켣��ŷʽ����
	public static double[][] traj_distance(Trajectory objTraj,Trajectory testTraj)//ŷʽ����
	{
		double[][] raw_dis=new double[100][100];//�þ���ά������֮��ŷ�Ͼ���
		Vector<Point> objPoints=objTraj.getPoints();//Ŀ��켣�ĵ㼯
		Vector<Point> testPoints=testTraj.getPoints();//���Թ켣�ĵ㼯 
		for(int i=0;i<testPoints.size();i++)//����ԭʼ�����ŷʽ����
		{
			Point testPoint=testPoints.get(i);
			for(int j=0;j<objPoints.size();j++)
			{	
				Point objPoint=objPoints.get(j);
				raw_dis[i][j]=e_distance(testPoint.getLongitude(),
						testPoint.getLatitude(),objPoint.getLongitude(),objPoint.getLatitude());
			}
		}
		return raw_dis;
	}
	//���������ŷʽ����
	public static double e_distance(double x1,double y1,double x2,double y2)//ŷʽ����
	{
		return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
	}
	
	//��������������Сֵ
	public static double min(double x,double y,double z)//��Сֵ
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
}
