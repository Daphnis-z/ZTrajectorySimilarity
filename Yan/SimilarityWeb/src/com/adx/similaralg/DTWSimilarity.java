package com.adx.similaralg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

public class DTWSimilarity implements SimilarityReactor{
	private double[][] raw_dis=new double[100][100];//�þ���ά������֮��ŷ�Ͼ���
	private double[][] dtw_dis=new double[100][100];//�þ���ά��dtw����
	public static int[][] tip=new int[199][2];//�þ���ά��ƥ���
	private double similarity;
	private SimularDef sDef;
	public DTWSimilarity(SimularDef sDef) {
		// TODO Auto-generated constructor stub
		this.sDef=sDef;
	}
	//����ʱ����Ĺ켣����
	private void DTWNoTime(Trajectory objTraj,Trajectory testTraj){
		int objTrajSize=objTraj.getSize(); //Ŀ��켣�ĳ���
		int testTrajSize=testTraj.getSize(); //���Թ켣�ĳ���
		int numMatchPoint=0;//dtwƥ������
		int edit_dis=0;//�༭����
		double avr_dtw_dis,dtw_s;//ƽ��dtw���룬dtwƥ����뷽��
		
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
		int x=testTrajSize-1,y=objTrajSize-1;
		avr_dtw_dis=dtw_dis[x][y];
		avr_dtw_dis-=raw_dis[x][y];
		tip[numMatchPoint][0]=x;
		tip[numMatchPoint][1]=y;
		numMatchPoint++;
		
		while(x>0||y>0){//Ѱ��ƥ���
			if((x>=1&&y>=1)&&(dtw_dis[x-1][y-1]-avr_dtw_dis<=0.000001)){
				avr_dtw_dis-=raw_dis[x-1][y-1];
				x=x-1;
				y=y-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
			}
			else if((x>=1)&&(dtw_dis[x-1][y]-avr_dtw_dis<=0.000001)){
				avr_dtw_dis-=raw_dis[x-1][y];
				x=x-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
			}
			else {
				
				avr_dtw_dis-=raw_dis[x][y-1];
				y=y-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
				
			}
		}
		
		
		avr_dtw_dis=dtw_dis[testTrajSize-1][objTrajSize-1]/numMatchPoint;
		double average=avr_dtw_dis;
		dtw_s=0;
		String[] obj_time=new String[objTrajSize];
		for(int i=0;i<objTrajSize;i++){
			obj_time[i]=objTraj.getPoints().get(i).getTimestamp();
		}
		String[] test_time=new String[testTrajSize];
		for(int i=0;i<testTrajSize;i++){
			test_time[i]=testTraj.getPoints().get(i).getTimestamp();
		}
		
		for(int i=0;i<numMatchPoint;i++)
		{
			dtw_s+=Math.pow(raw_dis[tip[i][0]][tip[i][1]]-average, 2);
			edit_dis+=Math.abs(tip[i][0]-tip[i][1]);
		
		
		}
		dtw_s=dtw_s/numMatchPoint;
		similarity=sDef.getDtwDis_W()*(sDef.getDtwDis_B()-avr_dtw_dis)/sDef.getDtwDis_B()+
				sDef.getEditDis_W()*(sDef.getEditDis_B()-edit_dis)/sDef.getEditDis_B()+
				sDef.getShapeSum_W()*(sDef.getShapeSum_B()-dtw_s)/sDef.getShapeSum_B();
		if(similarity<0){
			similarity=0;
		}
	}
	//��ʱ����Ĺ켣����
	private void DTWWithTime(Trajectory objTraj,Trajectory testTraj) {
		int objTrajSize=objTraj.getSize(); //Ŀ��켣�ĳ���
		int testTrajSize=testTraj.getSize(); //���Թ켣�ĳ���
		int numMatchPoint=0;//dtwƥ������
		int edit_dis=0;//�༭����
		long t_average=0;//ƽ��ʱ���
		double avr_dtw_dis,dtw_s;//ƽ��dtw���룬dtwƥ����뷽��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
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
		int x=testTrajSize-1,y=objTrajSize-1;
		avr_dtw_dis=dtw_dis[x][y];
		avr_dtw_dis-=raw_dis[x][y];
		tip[numMatchPoint][0]=x;
		tip[numMatchPoint][1]=y;
		numMatchPoint++;
		
		while(x>0||y>0){//Ѱ��ƥ���
			if((x>=1&&y>=1)&&(dtw_dis[x-1][y-1]-avr_dtw_dis<=0.000001)){
				avr_dtw_dis-=raw_dis[x-1][y-1];
				x=x-1;
				y=y-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
			}
			else if((x>=1)&&(dtw_dis[x-1][y]-avr_dtw_dis<=0.000001)){
				avr_dtw_dis-=raw_dis[x-1][y];
				x=x-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
			}
			else {
				
				avr_dtw_dis-=raw_dis[x][y-1];
				y=y-1;
				tip[numMatchPoint][0]=x;
				tip[numMatchPoint][1]=y;
				numMatchPoint++;
				
			}
		}
		
		
		avr_dtw_dis=dtw_dis[testTrajSize-1][objTrajSize-1]/numMatchPoint;
		double average=avr_dtw_dis;
		dtw_s=0;
		String[] obj_time=new String[objTrajSize];
		for(int i=0;i<objTrajSize;i++){
			obj_time[i]=objTraj.getPoints().get(i).getTimestamp();
		}
		String[] test_time=new String[testTrajSize];
		for(int i=0;i<testTrajSize;i++){
			test_time[i]=testTraj.getPoints().get(i).getTimestamp();
		}
		
		for(int i=0;i<numMatchPoint;i++)
		{
			dtw_s+=Math.pow(raw_dis[tip[i][0]][tip[i][1]]-average, 2);
			edit_dis+=Math.abs(tip[i][0]-tip[i][1]);
		
			Date d1 ,d2;
			try {
				d1=sdf.parse(test_time[tip[i][0]]);
				d2=sdf.parse(obj_time[tip[i][1]]);
				t_average+=(Math.abs(d1.getTime()-d2.getTime())/1000);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		dtw_s=dtw_s/numMatchPoint;
		t_average=t_average/numMatchPoint;
		similarity=sDef.getDtwDis_W()*(sDef.getDtwDis_B()-avr_dtw_dis)/sDef.getDtwDis_B()+
				sDef.getEditDis_W()*(sDef.getEditDis_B()-edit_dis)/sDef.getEditDis_B()+
				sDef.getShapeSum_W()*(sDef.getShapeSum_B()-dtw_s)/sDef.getShapeSum_B()+
				sDef.getTsum_W()*(sDef.getTsum_B()-t_average)/sDef.getTsum_B();
	}
	@Override
	public double getSimilarity(Trajectory objTraj, Trajectory testTraj,int timestamp) {
		// TODO Auto-generated method stub
		if(timestamp==1){
			DTWWithTime(objTraj,testTraj);
		}else{
			DTWNoTime(objTraj,testTraj);
		}
		return similarity;
	}
}
