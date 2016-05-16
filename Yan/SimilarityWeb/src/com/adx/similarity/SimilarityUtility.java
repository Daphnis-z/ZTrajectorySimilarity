package com.adx.similarity;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.Trajectory;

/**
 * SimilarityUtility
 * @author Agnes
 *计算相似度的工具类，有一些公共的方法
 */
public class SimilarityUtility {
	//计算两轨迹的欧式距离
	public static double[][] traj_distance(Trajectory objTraj,Trajectory testTraj)//欧式距离
	{
		double[][] raw_dis=new double[100][100];//该矩阵维护两点之间欧氏距离
		Vector<Point> objPoints=objTraj.getPoints();//目标轨迹的点集
		Vector<Point> testPoints=testTraj.getPoints();//测试轨迹的点集 
		for(int i=0;i<testPoints.size();i++)//计算原始两点间欧式距离
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
	//计算两点的欧式距离
	public static double e_distance(double x1,double y1,double x2,double y2)//欧式距离
	{
		return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
	}
	
	//计算三个数的最小值
	public static double min(double x,double y,double z)//最小值
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
}
