package com.adx.similaralg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTW1 {
	
	public static double[][] raw_dis=new double[100][100];//�þ���ά������֮��ŷ�Ͼ���
	public static double[][] dis=new double[100][100];//�þ���ά��dtw����
	public static int[][] tip=new int[199][2];//�þ���ά��ƥ���
	
	public static double dtw(double[][] traj1,double[][] traj2,String[] time1,String[] time2)throws ParseException//����ʱ����Ĺ켣���ƶȶ���
	{
		double dtw_dis,dtw_s,similarity;//ƽ��dtw���룬dtwƥ����뷽����ƶ�
		int edit_dis=0;//�༭����
		int time=0;//dtwƥ������
		long t_average=0;//ƽ��ʱ���
		int n=traj1.length,m=traj2.length;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		
		for(int i=0;i<n;i++)//����ԭʼ��������
		{
			for(int j=0;j<m;j++)
			{
				raw_dis[i][j]=e_distance(traj1[i][0],traj1[i][1],traj2[j][0],traj2[j][1]);
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
		
			Date d1=sdf.parse(time1[tip[i][0]]);
			Date d2=sdf.parse(time2[tip[i][1]]);
			t_average+=(Math.abs(d1.getTime()-d2.getTime())/1000);
		}
		dtw_s=dtw_s/time;
		t_average=t_average/time;
		
		similarity=0.55*(10-dtw_dis)/100+0.15*(5000-edit_dis)/5000+0.15*(100-dtw_s)/100+0.15*(100000000-t_average)/100000000;
		
		//System.out.println("����������"+time+"   "+"dtw���룺"+dis[n-1][m-1]+"   "+"�༭���룺"+edit_dis+"   "+"�ռ���뷽�"+dtw_s+"ʱ��ƫ�ƣ�"+t_average+"��");
		
		return similarity;//Ŀǰ���ص���ƽ��dtw����
		
	}
	
	public static double e_distance(double x1,double y1,double x2,double y2)//ŷʽ����
	{
		return Math.sqrt(Math.pow((x1-x2), 2)+Math.pow((y1-y2), 2));
	}
	
	public static double min(double x,double y,double z)//��Сֵ
	{
		double t=x<y?x:y;
		return t<z?t:z;
	}
	
	
	
	
	
	public static void main(String[] args) {
		double [][] t1={{0,0},{1,1},{2,2},{3,3},{4,4},{3,4},{3,9},{9,7},{13,7},{8,2},{20,10}};
		double [][] t2={{0,4},{1,5},{2,6},{3,7}};
		double [][] t3={{3,7},{2,6},{1,5},{0,4}};
		double [][] t4={{118.92761,31.92705},{118.93761,31.91705},{118.94761,31.90705},{118.95761,31.80705},{118.96761,31.81705},{118.97761,31.82705},{118.98761,31.83705},{118.99761,31.84705},{118.22761,31.85705},{118.23761,31.86705},{118.33761,31.87705},{118.25761,31.88705},{118.26761,31.89705},{118.27761,31.70705},{118.28761,31.71705},{118.29761,31.72705},{118.30761,31.73705},{118.31761,31.74705},{118.32761,31.75705},{118.33761,31.76705},{118.34761,31.77705},{118.81761,31.78705},{118.36761,31.71705},{118.37761,31.98705},{118.37761,31.97705},{118.39761,31.96705},{118.40761,31.95705},{118.41761,31.94705},{118.52761,31.93705},{118.53761,31.92705},{118.81761,31.91705},{118.29761,31.90705},{118.81761,31.80705},{118.82761,31.81705},{118.83761,31.82705},{118.84761,31.43705},{118.85761,31.44705},{118.86761,31.45705},{118.88761,31.46705},{118.89761,31.47705},{118.90761,31.48705},{118.91761,31.49705},{118.92761,31.50705},{118.93761,31.70705},{118.94761,31.71705},{118.95761,31.72705},{118.96761,31.73705},{118.97761,31.43705}};
		double [][] t5={{118.81761,31.98705},{118.82761,31.97705},{118.83761,31.96705},{118.84761,31.95705},{118.85761,31.94705},{118.86761,31.93705},{118.88761,31.92705},{118.89761,31.91705},{118.90761,31.90705},{118.91761,31.80705},{118.92761,31.81705},{118.93761,31.82705},{118.94761,31.83705},{118.95761,31.84705},{118.96761,31.85705},{118.97761,31.86705},{118.98761,31.87705},{118.99761,31.88705},{118.22761,31.89705},{118.23761,31.70705},{118.33761,31.71705},{118.25761,31.72705},{118.26761,31.73705},{118.27761,31.74705},{118.28761,31.75705},{118.29761,31.76705},{118.30761,31.77705},{118.31761,31.78705},{118.32761,31.71705},{118.33761,31.72705},{118.34761,31.71705},{118.81761,31.98705},{118.36761,31.33705},{118.37761,31.34705},{118.37761,31.34705},{118.39761,31.36705},{118.40761,31.39705},{118.41761,31.40705},{118.52761,31.42705},{118.53761,31.43705},{118.81761,31.44705},{118.29761,31.45705},{118.88761,31.46705},{118.55761,31.47705},{118.43761,31.48705},{118.42761,31.49705},{118.55761,31.50705},{118.55761,31.50705}};
		double [][] t6={{118.32761,31.83705},{118.33761,31.84705},{118.34761,31.85705},{118.81761,31.86705},{118.36761,31.87705},{118.37761,31.88705},{118.37761,31.89705},{118.39761,31.70705},{118.40761,31.71705},{118.41761,31.72705},{118.52761,31.73705},{118.53761,31.74705},{118.43761,31.75705},{118.42761,31.76705},{118.55761,31.77705},{118.55761,31.78705},{118.81761,31.71705},{118.82761,31.98705},{118.83761,31.97705},{118.85761,31.96705},{118.86761,31.95705},{118.88761,31.94705},{118.89761,31.93705},{118.90761,31.92705},{118.91761,31.91705},{118.92761,31.90705},{118.93761,31.80705},{118.94761,31.81705},{118.95761,31.82705},{118.96761,31.43705},{118.97761,31.44705},{118.98761,31.45705},{118.99761,31.46705},{118.22761,31.47705},{118.23761,31.48705},{118.33761,31.49705},{118.25761,31.50705},{118.26761,31.70705},{118.27761,31.71705},{118.28761,31.72705},{118.29761,31.73705},{118.30761,31.43705},{118.91761,31.92705},{118.92761,31.91705},{118.93761,31.90705},{118.94761,31.80705},{118.95761,31.81705},{118.96761,31.82705}};
		String [] time1={"2015/6/21 10:47:00","2015/6/21 12:59:00","2015/6/23 8:19:00","2015/6/24 14:52:00","2015/6/24 19:47:00","2015/6/25 16:01:00","2015/6/26 5:13:00","2015/6/26 8:00:00","2015/6/26 10:33:00","2015/6/26 20:10:00","2015/6/28 10:11:00","2015/6/28 11:15:00","2015/6/28 11:30:00","2015/6/30 22:33:00","2015/7/1 5:11:00","2015/7/1 7:11:00","2015/7/1 11:59:00","2015/7/1 20:11:00","2015/7/1 21:12:00","2015/7/2 23:41:00","2015/7/3 9:23:00","2015/7/5 12:31:00","2015/7/5 12:50:00","2015/7/6 7:30:00","2015/7/6 11:30:00","2015/7/6 19:27:00","2015/7/6 20:31:00","2015/7/6 20:40:00","2015/7/6 20:58:00","2015/7/8 8:08:00","2015/7/10 14:25:00","2015/7/11 21:10:00","2015/7/11 22:58:00","2015/7/11 22:58:00","2015/7/12 10:25:00","2015/7/13 10:40:00","2015/7/13 11:30:00","2015/7/14 22:33:00","2015/7/15 5:11:00","2015/7/15 7:11:00","2015/7/15 11:59:00","2015/7/15 20:11:00","2015/7/15 21:12:00","2015/7/15 23:41:00","2015/7/16 9:23:00","2015/7/16 12:31:00","2015/7/16 12:50:00","2015/7/18 19:22:00"};
		String [] time2={"2015/1/1 12:20:00","2015/1/2 13:04:00","2015/1/2 14:52:00","2015/1/2 14:52:00","2015/1/2 16:01:00","2015/1/3 5:13:00","2015/1/3 8:00:00","2015/1/3 10:33:00","2015/1/3 20:10:00","2015/1/4 10:11:00","2015/1/4 11:15:00","2015/1/4 11:30:00","2015/1/4 22:33:00","2015/1/5 5:11:00","2015/1/5 7:11:00","2015/1/6 11:59:00","2015/1/6 20:11:00","2015/1/6 21:12:00","2015/1/6 23:41:00","2015/1/7 9:23:00","2015/1/7 12:31:00","2015/1/9 12:50:00","2015/1/10 7:30:00","2015/1/10 11:30:00","2015/1/12 19:27:00","2015/1/12 20:31:00","2015/1/12 20:40:00","2015/1/13 20:58:00","2015/1/14 8:08:00","2015/1/14 14:25:00","2015/1/16 21:10:00","2015/1/17 22:58:00","2015/1/17 22:58:00","2015/1/18 10:25:00","2015/1/18 10:40:00","2015/1/20 19:31:00","2015/1/20 23:20:00","2015/1/23 11:11:00","2015/1/23 14:28:00","2015/1/23 22:45:00","2015/1/25 8:10:00","2015/1/25 10:23:00","2015/1/26 11:30:00","2015/1/27 15:45:00","2015/1/27 21:15:00","2015/1/28 21:30:00","2015/1/29 20:15:00","2015/1/31 23:58:00"};
		
		try {
			System.out.println(dtw(t5,t6,time1,time2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}