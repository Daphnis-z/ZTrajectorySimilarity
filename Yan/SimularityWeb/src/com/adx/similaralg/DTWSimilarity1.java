package com.adx.similaralg;

import com.adx.entity.Trajectory;

import java.util.Vector;

import com.adx.entity.*;

public class DTWSimilarity1 implements SimilarityReactor{
	private Trajectory objTraj;
	private Trajectory testTraj;
	private double similarity;
	//����ʱ����Ĺ켣����
	private void DTWNoTime(){
		int n=objTraj.getSize(),m=testTraj.getSize();
		Vector<Point> objPoint=objTraj.getPoints();
		Vector<Point> testPoint=testTraj.getPoints();
		double[][] raw_dis=new double[n][m];
		double[][] dis=new double[n][m];
		int [][] tip=new int[n+m-1][2];
		
		double dtw_dis,dtw_s;//ƽ��dtw���룬dtwƥ����뷽����ƶ�
		int edit_dis=0;//�༭����
		int time=0;//dtwƥ������
		long t_average=0;//ƽ��ʱ���
		
		for(int i=0;i<n;i++)//����ԭʼ��������
		{
			for(int j=0;j<m;j++)
			{
//				raw_dis[i][j]=e_distance();
			}
		}
		dis[0][0]=raw_dis[0][0];//dtw��������ʼ��
		for(int i=1;i<n;i++)
		{
			dis[i][0]=raw_dis[i][0]+dis[i-1][0];
		}
		for(int j=1;j<m;j++)
		{
			dis[0][j]=raw_dis[0][j]+dis[0][j-1];
		}
		for(int i=1;i<n;i++)//����dtw�������
		{
			for(int j=1;j<m;j++)
			{
				dis[i][j]=raw_dis[i][j]+min(dis[i-1][j],dis[i][j-1],dis[i-1][j-1]);
			}
		}
		
		int x=n-1,y=m-1;
		dtw_dis=dis[x][y];
		
		dtw_dis-=raw_dis[x][y];
		tip[time][0]=x;
		tip[time][1]=y;
		time++;
		
		while(x>0||y>0)//Ѱ��ƥ��켣���
		{
			if((y>=1)&&(dtw_dis-dis[x][y-1]<=0.00001))
			{
				dtw_dis-=raw_dis[x][y-1];
				y=y-1;
				tip[time][0]=x;
				tip[time][1]=y;
				time++;
			}
			else if((x>=1)&&(dtw_dis-dis[x-1][y]<=0.000001))
			{
				dtw_dis-=raw_dis[x-1][y];
				x=x-1;
				tip[time][0]=x;
				tip[time][1]=y;
				time++;
			}
			else if((x>=1&&y>=1)&&(dtw_dis-dis[x-1][y-1]<=0.000001))
			{	
				dtw_dis-=raw_dis[x-1][y-1];
				x=x-1;
				y=y-1;
				tip[time][0]=x;
				tip[time][1]=y;
				time++;
			}
		}
		
		
		dtw_dis=dis[n-1][m-1]/time;
		double average=dtw_dis;
		dtw_s=0;
		for(int i=0;i<time;i++)
		{
			dtw_s+=Math.pow(raw_dis[tip[i][0]][tip[i][1]]-average, 2);
			edit_dis+=Math.abs(tip[i][0]-tip[i][1]);
		
		}
		dtw_s=dtw_s/time;
		t_average=t_average/time;
		
		similarity=0.55*(10-dtw_dis)/100+0.15*(5000-edit_dis)/5000+0.15*(100-dtw_s)/100+0.15*(100000000-t_average)/100000000;
		
	}
	//��ʱ����Ĺ켣����
	private void DTWWithTime(){
		
	}
	private static double e_distance(double x1,double y1,double x2,double y2)//ŷʽ����
	{
		return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
	}
	
	private static double min(double x,double y,double z)//��Сֵ
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
	
	@Override
	public double getSimilarity(Trajectory objTraj, Trajectory testTraj, int timestamp) {
		// TODO Auto-generated method stub
		return similarity;
	}
}
