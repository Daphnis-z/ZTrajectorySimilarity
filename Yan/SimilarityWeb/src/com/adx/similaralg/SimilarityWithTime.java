package com.adx.similaralg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

public class SimilarityWithTime extends Similarity{
	
	public SimilarityWithTime(Trajectory objTraj, Trajectory testTraj, SimularDef sDef) {
		init(objTraj,testTraj,sDef);
		calculate_Params();
		calculate_Similarity();
		calculate_SimilarerMatch();
	}

	public void init(Trajectory objTraj,Trajectory testTraj,SimularDef sDef){//初始化
		this.objTraj=objTraj;
		this.testTraj=testTraj;
		this.sDef=sDef;
		raw_dis=new double[testTraj.getSize()][objTraj.getSize()];
		dtw_dis=new double[testTraj.getSize()][objTraj.getSize()];
		match=new int[objTraj.getSize()+testTraj.getSize()-1][2];	
	}
	
	public void calculate_Params(){
		calculate_Rawdis();
		calculate_Dtwdis();
		calculate_Match();
		calculate_DSET();
		calculate_DSETS();
	}
	
	public void calculate_Rawdis()//计算原始距离矩阵
	{
		Vector<Point> objPoints=objTraj.getPoints();//目标轨迹的点集
		Vector<Point> testPoints=testTraj.getPoints();//测试轨迹的点集 
		for(int i=0;i<testTraj.getSize();i++)//计算原始两点间欧式距离
		{
			Point testPoint=testPoints.get(i);
			for(int j=0;j<objTraj.getSize();j++)
			{	
				Point objPoint=objPoints.get(j);
				raw_dis[i][j]=SimilarityUtility.r_distance(objPoint,testPoint);
			}
		}
	}
	
	public void calculate_Dtwdis(){//计算dtw矩阵
		dtw_dis[0][0]=raw_dis[0][0];
		for(int i=1;i<testTraj.getSize();i++)
		{
			dtw_dis[i][0]=raw_dis[i][0]+dtw_dis[i-1][0];
		}
		for(int j=1;j<objTraj.getSize();j++)
		{
			dtw_dis[0][j]=raw_dis[0][j]+dtw_dis[0][j-1];
		}
		for(int i=1;i<testTraj.getSize();i++)
		{
			for(int j=1;j<objTraj.getSize();j++)
			{
				dtw_dis[i][j]=raw_dis[i][j]+SimilarityUtility.min(dtw_dis[i-1][j],
							dtw_dis[i][j-1],dtw_dis[i-1][j-1]);
			}
		}
	}
		
	public void calculate_Match(){//回溯匹配
		int x=testTraj.getSize()-1,y=objTraj.getSize()-1;
		matchsize=0;
		double temp_dtwdis=dtw_dis[x][y];
		temp_dtwdis-=raw_dis[x][y];
		match[matchsize][0]=x;
		match[matchsize][1]=y;
		matchsize++;
		
		while(x>0||y>0){//寻找匹配点
			if((x>=1&&y>=1)&&(dtw_dis[x-1][y-1]-temp_dtwdis<=0.000001)){
				temp_dtwdis-=raw_dis[x-1][y-1];
				x=x-1;
				y=y-1;
				match[matchsize][0]=x;
				match[matchsize][1]=y;
				matchsize++;
			}
			else if((x>=1)&&(dtw_dis[x-1][y]-temp_dtwdis<=0.000001)){
				temp_dtwdis-=raw_dis[x-1][y];
				x=x-1;
				match[matchsize][0]=x;
				match[matchsize][1]=y;
				matchsize++;
			}
			else {
				temp_dtwdis-=raw_dis[x][y-1];
				y=y-1;
				match[matchsize][0]=x;
				match[matchsize][1]=y;
				matchsize++;
				
			}
		}
	}
	
	public void calculate_DSET(){//计算距离、形状、编辑距离、时间差
		dis=dtw_dis[testTraj.getSize()-1][objTraj.getSize()-1]/matchsize;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for(int i=0;i<matchsize;i++)
		{
			shape+=Math.pow(raw_dis[match[i][0]][match[i][1]]-dis,2.0);
			edit+=Math.abs(match[i][0]-match[i][1]);
			Date d1 ,d2;
			try {
				d1=sdf.parse(testTraj.getPoints().get(match[i][0]).getTimestamp());
				d2=sdf.parse(objTraj.getPoints().get(match[i][1]).getTimestamp());
				time+=(Math.abs(d1.getTime()-d2.getTime())/60000);
				System.out.println(d1.getTime()-d2.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		time/=matchsize;
	}
	
	public void calculate_DSETS(){//计算距离相似度、形状相似度、编辑相似度、时间相似度
		if(dis>=sDef.getDtwDis_B()) disS=0;
		else disS+=sDef.getDtwDis_W()*(1-dis/sDef.getDtwDis_B());
		if(shape>=sDef.getShapeSum_B()) shapeS=0;
		else shapeS=sDef.getShapeSum_W()*(sDef.getShapeSum_B()-shape)/sDef.getShapeSum_B();
		if(edit>=sDef.getEditDis_B()) editS=0;
		else editS=sDef.getEditDis_W()*(sDef.getEditDis_B()-edit)/sDef.getEditDis_B();
		if(time>=sDef.getTsum_B()) timeS=0;
		else timeS=sDef.getTsum_W()*(sDef.getTsum_B()-time)/sDef.getTsum_B();
		System.out.println("dis:"+dis+"   "+sDef.getDtwDis_B());
		System.out.println("disS:"+disS);
		System.out.println("shape:"+shape+"   "+sDef.getShapeSum_B());
		System.out.println("shapeS:"+shapeS);
		System.out.println("edit:"+edit+"   "+sDef.getEditDis_B());
		System.out.println("editS:"+editS);
		System.out.println("time:"+time+"   "+sDef.getTsum_B());
		System.out.println("timeS:"+timeS);
	}
	
	public void calculate_Similarity(){
		similarity=timeS+disS+editS+shapeS;
//		similarity*=similarity;
		int t=(int) ((similarity+0.000005)*100000);
		similarity=(double)t/1000;
	}
	
	public void calculate_SimilarerMatch(){
		int matchcnt=5+matchsize/10;
		similarestPoint=new Point[2];
		MatchPoint []mp=new MatchPoint[matchsize];
		for(int i=0;i<matchsize;i++){
			mp[i]=new MatchPoint();
			mp[i].index=i;
			mp[i].distance=raw_dis[match[i][0]][match[i][1]];
		}
		Arrays.sort(mp,new Mycomparator());
		similarestPoint[0]=testTraj.getPoints().get(match[mp[0].index][0]);
		similarestPoint[1]=objTraj.getPoints().get(match[mp[0].index][1]);
		Vector<Integer> sest=new Vector<Integer>();
		for(int i=0;i<matchcnt;i++){
			System.out.println(mp[i].index+":"+match[mp[i].index][0]+"---"+match[mp[i].index][1]+":"+mp[i].distance);
		}
		for(int i=0;i<matchcnt;i++){
			System.out.print(mp[i].index+":");
			int t=match[mp[i].index][0];
			int o=match[mp[i].index][1];
			int flag=mp[i].index;
			for(int j=mp[i].index;j>0;j--){
				int jt=match[j][0];
				int jo=match[j][1];
				System.out.println(raw_dis[jt][jo]+"/"+raw_dis[t][o]+"="+(raw_dis[jt][jo]/raw_dis[t][o]<1.1));
				if((raw_dis[jt][jo]/raw_dis[t][o])<1.05){
					flag=j;
				}
				else break;
			}
			for(int j=flag;j<=mp[i].index;j++){
				sest.add(j);
			}
			for(int j=mp[i].index+1;j<matchsize;j++){
				int jt=match[j][0];
				int jo=match[j][1];
				System.out.println(raw_dis[jt][jo]+"/"+raw_dis[t][o]+"="+(raw_dis[jt][jo]/raw_dis[t][o]<1.1));
				if((raw_dis[jt][jo]/raw_dis[t][o])<1.05){
					sest.add(j);
				}
				else break;
			}
			System.out.println(sest.size());
			if(sest.size()>=3) break;
			else sest.clear();
		}
		similarestTraj=new Trajectory[2];
    	similarestTraj[0]=new Trajectory();
    	similarestTraj[1]=new Trajectory();
	    if(sest.size()>=3){
	    	for(int i=0;i<sest.size();i++){
	    		System.out.println(match[(int)sest.get(i)][0]+"----"+match[(int)sest.get(i)][1]);
	    		int t1=match[(int)sest.get(i)][0];
	    		int t2=match[(int)sest.get(i)][1];
	    		similarestTraj[0].addPoint(testTraj.getPoints().get(t1));
	    		similarestTraj[1].addPoint(objTraj.getPoints().get(t2));
	    	}
	    }
	    else{
	    	similarestTraj[0].addPoint(similarestPoint[0]);
    		similarestTraj[1].addPoint(similarestPoint[1]);
	    }
	}
}
