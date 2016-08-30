package com.adx.similaralg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		raw_time=new double[testTraj.getSize()][objTraj.getSize()];
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date d1,d2;
		for(int i=0;i<testTraj.getSize();i++)//计算原始两点间欧式距离
		{
			Point testPoint=testPoints.get(i);
			for(int j=0;j<objTraj.getSize();j++)
			{	
				Point objPoint=objPoints.get(j);
				raw_dis[i][j]=SimilarityUtility.r_distance(objPoint,testPoint);
				try {
					d1=sdf.parse(testPoint.getTimestamp());
					d2=sdf.parse(objPoint.getTimestamp());
					raw_time[i][j]=(Math.abs(d1.getTime()-d2.getTime())/60000);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			time+=raw_time[match[i][0]][match[i][1]];
		}
		edit/=matchsize;
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
	}
	
	public void calculate_Similarity(){//寻找最相似点与段
		similarity=timeS+disS+editS+shapeS;
		int t=(int) ((similarity+0.000005)*100000);
		similarity=(double)t/1000;
	}
	
	public void calculate_SimilarerMatch(){//计算最相似点与段
		testTraj.refreshID();
		objTraj.refreshID();
		int x=testTraj.getSize();
		int y=objTraj.getSize();
		int [][] lcss=new int[x+1][y+1];
		int [][] flag=new int[x+1][y+1];
		int llength=0;
		double md=raw_dis[match[0][0]][match[0][1]];
		double me=Math.abs(match[0][0]-match[0][1]);
		double mt=raw_time[match[0][0]][match[0][1]];
		for(int i=0;i<matchsize;i++){
			if(raw_dis[match[i][0]][match[i][1]]<md) md=raw_dis[match[i][0]][match[i][1]];
			if(Math.abs(match[i][0]-match[i][1])<me) me=Math.abs(match[i][0]-match[i][1]);
			if(raw_time[match[i][0]][match[i][1]]<mt) mt=raw_time[match[i][0]][match[i][1]];
		}
		md=(dis+md)/2;
		me=(me+edit)/2;
		mt=(mt+time)/2;
		for(int i=0;i<x;i++) lcss[i][0]=0;
		for(int i=1;i<y;i++) lcss[0][i]=0;
		for(int i=1;i<=x;i++){
			for(int j=1;j<=y;j++){
				if(raw_dis[i-1][j-1]<=md/*&&raw_time[i-1][j-1]<=mt*/){
					lcss[i][j]=lcss[i-1][j-1]+1;
					flag[i][j]=1;
					
				}
				else if(lcss[i-1][j]>=lcss[i][j-1]){
					lcss[i][j]=lcss[i-1][j];
					flag[i][j]=2;
				}
				else{
					lcss[i][j]=lcss[i][j-1];
				}
			}
		}
		llength=lcss[x][y];
		int [][] common=new int[llength][2];
//		System.out.println("llength="+llength);
		int index=llength-1;
		while(index>=0){
			if(flag[x][y]==1){
				common[index][0]=x-1;
				common[index][1]=y-1;
				index--;
				x--;
				y--;
			}
			else if(flag[x][y]==2){
				x--;
			}
			else y--;
		}
		
		similarestPoint=new Point[2];
		similarestTraj=new Trajectory[2];
		similarestTraj[0]=new Trajectory();
		similarestTraj[1]=new Trajectory();
		double mind=raw_dis[0][0];
		double mint=raw_time[0][0];
		x=0;
		y=0;
		for(int i=0;i<testTraj.getSize();i++){
			for(int j=0;j<objTraj.getSize();j++){
				if(raw_dis[i][j]<mind&&raw_time[i][j]<mint){
					mind=raw_dis[i][j];
					mint=raw_time[i][j];
					x=i;
					y=j;
				}
				
			}
		}
		similarestPoint[0]=testTraj.getPoints().elementAt(x);
		similarestPoint[1]=objTraj.getPoints().elementAt(y);
		if(llength==0){
			if(similarestPoint[0].pid>0&&similarestPoint[1].pid>0){
	    		double lat=(similarestPoint[0].getLatitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid-1).getLatitude())/2;
	    		double lon=(similarestPoint[0].getLongitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid-1).getLongitude())/2;
	    		Point tp0=new Point(lon,lat);
	    		similarestTraj[0].addPoint(tp0);
	    		lat=(similarestPoint[1].getLatitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid-1).getLatitude())/2;
	    		lon=(similarestPoint[1].getLongitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid-1).getLongitude())/2;
	    		Point tp1=new Point(lon,lat);
	    		similarestTraj[1].addPoint(tp1);
	    			    		
	    	}
	    	similarestTraj[0].addPoint(similarestPoint[0]);
    		similarestTraj[1].addPoint(similarestPoint[1]);
    		if(similarestPoint[0].pid<testTraj.getSize()-1&&similarestPoint[1].pid<objTraj.getSize()-1){
	    		double lat=(similarestPoint[0].getLatitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid+1).getLatitude())/2;
	    		double lon=(similarestPoint[0].getLongitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid+1).getLongitude())/2;
	    		
	    		Point tp0=new Point(lon,lat);
	    		
	    		similarestTraj[0].addPoint(tp0);
	    		lat=(similarestPoint[1].getLatitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid+1).getLatitude())/2;
	    		lon=(similarestPoint[1].getLongitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid+1).getLongitude())/2;
	    		Point tp1=new Point(lon,lat);
	    		similarestTraj[1].addPoint(tp1);
    		}
    		
		}
		else{
			Vector<Trajectory[]> trajs=new Vector<Trajectory[]>();
			for(int i=0;i<llength-1;i++){
				Trajectory [] subtrajs=new Trajectory[2];//每次构造相似子段
				subtrajs[0]=new Trajectory();
				subtrajs[1]=new Trajectory();
				subtrajs[0].addPoint(testTraj.getPoints().elementAt(common[i][0]));
				subtrajs[1].addPoint(objTraj.getPoints().elementAt(common[i][1]));
				int t=i;
				for(int j=t+1;j<llength;j++){
					if(common[j][0]==common[t][0]+1&&common[j][1]==common[t][1]+1){
						subtrajs[0].addPoint(testTraj.getPoints().elementAt(common[j][0]));
						subtrajs[1].addPoint(objTraj.getPoints().elementAt(common[j][1]));
						t=j;
					}
					else break;
				}
				if(subtrajs[0].getSize()>1) trajs.add(subtrajs);
				i=t;
			}
			if(trajs.size()>0){
				double []s=new double[trajs.size()];
//				for(int i=0;i<trajs.size();i++){
//					for(int j=0;j<trajs.elementAt(i)[0].getSize();j++){
//						System.out.println(j+":"+trajs.elementAt(i)[0].getPoints().elementAt(j).pid+"---"+trajs.elementAt(i)[0].getPoints().elementAt(j).pid);
//					}
//				}
				for(int i=0;i<trajs.size();i++){
						s[i]=subsimilarity(trajs.elementAt(i)[0],trajs.elementAt(i)[1]);	
				}
				index=0;
				double best=s[0];
				for(int i=1;i<trajs.size();i++){
					if(s[i]>best){
						best=s[i];
						index=i;
					}
				}
				similarestTraj[0]=trajs.elementAt(index)[0];
				similarestTraj[1]=trajs.elementAt(index)[1];
			}
			else{
				if(similarestPoint[0].pid>0&&similarestPoint[1].pid>0){
		    		double lat=(similarestPoint[0].getLatitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid-1).getLatitude())/2;
		    		double lon=(similarestPoint[0].getLongitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid-1).getLongitude())/2;
		    		Point tp0=new Point(lon,lat);
		    		similarestTraj[0].addPoint(tp0);
		    		lat=(similarestPoint[1].getLatitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid-1).getLatitude())/2;
		    		lon=(similarestPoint[1].getLongitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid-1).getLongitude())/2;
		    		Point tp1=new Point(lon,lat);
		    		similarestTraj[1].addPoint(tp1);
		    			    		
		    	}
		    	similarestTraj[0].addPoint(similarestPoint[0]);
	    		similarestTraj[1].addPoint(similarestPoint[1]);
	    		if(similarestPoint[0].pid<testTraj.getSize()-1&&similarestPoint[1].pid<objTraj.getSize()-1){
		    		double lat=(similarestPoint[0].getLatitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid+1).getLatitude())/2;
		    		double lon=(similarestPoint[0].getLongitude()+testTraj.getPoints().elementAt(similarestPoint[0].pid+1).getLongitude())/2;
		    		
		    		Point tp0=new Point(lon,lat);
		    		
		    		similarestTraj[0].addPoint(tp0);
		    		lat=(similarestPoint[1].getLatitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid+1).getLatitude())/2;
		    		lon=(similarestPoint[1].getLongitude()+objTraj.getPoints().elementAt(similarestPoint[1].pid+1).getLongitude())/2;
		    		Point tp1=new Point(lon,lat);
		    		similarestTraj[1].addPoint(tp1);	    		
		    	}
	    		
			}
		}
		
	}
	
	public double subsimilarity(Trajectory t1,Trajectory t2){
		double subs;
		double subd=0;
		double subt=0;
		for(int i=0;i<t1.getSize();i++){
			subd+=raw_dis[t1.getPoints().elementAt(i).pid][t2.getPoints().elementAt(i).pid];
			subt+=raw_time[t1.getPoints().elementAt(i).pid][t2.getPoints().elementAt(i).pid];
			
		}
		subd/=t1.getSize();
		subt/=t1.getSize();
		subs=sDef.getDtwDis_W()*(1-subd/dis)+sDef.getTsum_W()*(1-subt/time)+sDef.getEditDis_W()+sDef.getShapeSum_W();
//		System.out.println(subs);
		return subs;
		
	}

}
