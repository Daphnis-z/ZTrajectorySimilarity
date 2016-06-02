package com.adx.similaralg;

import java.util.Arrays;
import java.util.Vector;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.SimilarityUtility;

public class SimilarityWithoutTime extends Similarity{
	
	public SimilarityWithoutTime(Trajectory objTraj,Trajectory testTraj,SimularDef sDef){
		init(objTraj,testTraj,sDef);
		calculate_Params();
		calculate_Similarity();
		calculate_SimilarerMatch();
	}

	public void init(Trajectory objTraj,Trajectory testTraj,SimularDef sDef){//��ʼ��
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
		calculate_DSE();
		calculate_DSES();
	}
	
	public void calculate_Rawdis()//����ԭʼ�������
	{
		Vector<Point> objPoints=objTraj.getPoints();//Ŀ��켣�ĵ㼯
		Vector<Point> testPoints=testTraj.getPoints();//���Թ켣�ĵ㼯 
		for(int i=0;i<testTraj.getSize();i++)//����ԭʼ�����ŷʽ����
		{
			Point testPoint=testPoints.get(i);
			for(int j=0;j<objTraj.getSize();j++)
			{	
				Point objPoint=objPoints.get(j);
				raw_dis[i][j]=SimilarityUtility.r_distance(objPoint,testPoint);
			}
		}
	}
	
	public void calculate_Dtwdis(){//����dtw����
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
		
	public void calculate_Match(){//����ƥ��
		int x=testTraj.getSize()-1,y=objTraj.getSize()-1;
		matchsize=0;
		double temp_dtwdis=dtw_dis[x][y];
		temp_dtwdis-=raw_dis[x][y];
		match[matchsize][0]=x;
		match[matchsize][1]=y;
		matchsize++;
		
		while(x>0||y>0){//Ѱ��ƥ���
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
	
	public void calculate_DSE(){//������롢��״���༭����
		dis=dtw_dis[testTraj.getSize()-1][objTraj.getSize()-1]/matchsize;
		for(int i=0;i<matchsize;i++)
		{
			shape+=Math.pow(raw_dis[match[i][0]][match[i][1]]-dis,2.0);
			edit+=Math.abs(match[i][0]-match[i][1]);
		}
	}
	
	public void calculate_DSES(){//����������ƶȡ���״���ƶȡ��༭���ƶ�
		if(dis>=sDef.getDtwDis_B()) disS=0;
		else disS+=sDef.getDtwDis_W()*(1-dis/sDef.getDtwDis_B());
		if(shape>=sDef.getShapeSum_B()) shapeS=0;
		else shapeS=sDef.getShapeSum_W()*(sDef.getShapeSum_B()-shape)/sDef.getShapeSum_B();
		if(edit>=sDef.getEditDis_B()) editS=0;
		else editS=sDef.getEditDis_W()*(sDef.getEditDis_B()-edit)/sDef.getEditDis_B();
		System.out.println("dis:"+dis+"   "+sDef.getDtwDis_B());
		System.out.println("disS:"+disS);
		System.out.println("shape:"+shape+"   "+sDef.getShapeSum_B());
		System.out.println("shapeS:"+shapeS);
		System.out.println("edit:"+edit+"   "+sDef.getEditDis_B());
		System.out.println("editS:"+editS);
	}
	
	public void calculate_Similarity(){//�������ƶ�
		similarity=disS+editS+shapeS;
//		similarity*=similarity;
		int t=(int) ((similarity+0.000005)*100000);
		similarity=(double)t/1000;
	}
	
	
	public void calculate_SimilarerMatch(){
		if(similarity<=0){
			int matchcnt=5+objTraj.getSize()/5;
			similarestPoint=new Point[2];
			MatchPoint [] mp=new MatchPoint[objTraj.getSize()];
			for(int i=0;i<objTraj.getSize();i++){
				mp[i]=new MatchPoint();
				mp[i].o_index=i;
				mp[i].t_index=0;
				mp[i].distance=raw_dis[0][i];
				for(int j=1;j<testTraj.getSize();j++){
					if(raw_dis[j][i]<mp[i].distance){
						mp[i].t_index=j;
						mp[i].distance=raw_dis[j][i];
					}
				}
			}
			Arrays.sort(mp,new Mycomparator());
			similarestPoint[0]=testTraj.getPoints().get(mp[0].t_index);
			similarestPoint[1]=objTraj.getPoints().get(mp[0].o_index);
			Vector sest=new Vector();
			for(int i=0;i<matchcnt-1;i++){
				sest.add(i);
				int t=i;
				for(int j=i+1;j<matchcnt;j++){
					if(Math.abs(mp[t].t_index-mp[j].t_index)==1&&(mp[t].o_index!=mp[j].o_index)){
						sest.add(j);
						t=j;
					}
				}
				if(sest.size()>=2) break;
				else sest.removeAllElements();
			}
		    if(sest.size()>=2){
		    	similarestTraj=new Trajectory[2];
		    	similarestTraj[0]=new Trajectory();
		    	similarestTraj[1]=new Trajectory();
		    	for(int i=0;i<sest.size();i++){
		    		int t1=mp[(int) sest.get(i)].t_index;
		    		int t2=mp[(int) sest.get(i)].o_index;
		    		similarestTraj[0].addPoint(testTraj.getPoints().get(t1));
		    		similarestTraj[1].addPoint(objTraj.getPoints().get(t2));
		    	}
		    }
		    else similarestTraj=null;
			
			
		}
		else{
			int matchcnt=5+matchsize/5;
			similarestPoint=new Point[2];
			int [][] similarest_match=new int[1][2];
			MatchPoint []mp=new MatchPoint[matchsize];
			for(int i=0;i<matchsize;i++){
				mp[i]=new MatchPoint();
				mp[i].t_index=match[i][0];
				mp[i].o_index=match[i][1];
				mp[i].distance=raw_dis[match[i][0]][match[i][1]];
			}
			Arrays.sort(mp,new Mycomparator());
			similarestPoint[0]=testTraj.getPoints().get(mp[0].t_index);
			similarestPoint[1]=objTraj.getPoints().get(mp[0].o_index);
			Vector sest=new Vector();
			for(int i=0;i<matchcnt-1;i++){
				sest.add(i);
				int t=i;
				for(int j=i+1;j<matchcnt;j++){
					if(Math.abs(mp[t].t_index-mp[j].t_index)==1&&(mp[t].o_index!=mp[j].o_index)){
						sest.add(j);
						t=j;
					}
				}
				if(sest.size()>=2) break;
				else sest.removeAllElements();
			}
		    if(sest.size()>=2){
		    	similarestTraj=new Trajectory[2];
		    	similarestTraj[0]=new Trajectory();
		    	similarestTraj[1]=new Trajectory();
		    	for(int i=0;i<sest.size();i++){
		    		int t1=mp[(int) sest.get(i)].t_index;
		    		int t2=mp[(int) sest.get(i)].o_index;
		    		similarestTraj[0].addPoint(testTraj.getPoints().get(t1));
		    		similarestTraj[1].addPoint(objTraj.getPoints().get(t2));
		    	}
		    }
		    else similarestTraj=null;
			
		}
	}

}

