package com.daphnis.index;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.dataHandle.ReadTaxiData;

public class EigenValueTest {
	public static void filtrateByTrajLen(List<Trajectory> trajs,Trajectory traj){
		double lenThreshold=0;		
		Random rd=new Random(System.currentTimeMillis());
		for(int i=0;i<4;++i){
			lenThreshold+=trajs.get(rd.nextInt(trajs.size())).getTrajLen()-
					trajs.get(rd.nextInt(trajs.size())).getTrajLen();					
		}
		lenThreshold/=5;
		for(int i=0;i<trajs.size();++i){
			if(Math.abs(trajs.get(i).getTrajLen()-traj.getTrajLen())>lenThreshold){
				trajs.remove(i);
			}
		}
		trajs.size();
	}
	private static double getDisThreshold(List<Trajectory> trajs){
		Random rd=new Random(System.currentTimeMillis());
		double disSum=0;
		for(int i=0;i<4;++i){
			Point p1=trajs.get(rd.nextInt(trajs.size())).getCenterTraj(),
					p2=trajs.get(rd.nextInt(trajs.size())).getCenterTraj();
			double a=Math.pow(p1.getLongitude(), 2)+Math.pow(p1.getLatitude(), 2),
					b=Math.pow(p2.getLongitude(), 2)+Math.pow(p2.getLatitude(), 2);
			disSum+=Math.abs(a-b);
		}
		return disSum/5;
	}
	public static int calSimilarityEigenvalue(List<Trajectory> trajs,Trajectory traj,SimularDef sd){		
		List<Trajectory> subtrajs=new ArrayList<Trajectory>();	
		double trajDis=Math.pow(traj.getCenterTraj().getLongitude(), 2)+
				Math.pow(traj.getCenterTraj().getLatitude(), 2);
		double dis=Double.MAX_VALUE;
		final double DIS_THRESHOLD=getDisThreshold(trajs);
		for(int i=0;i<trajs.size();++i){
			Point p=trajs.get(i).getCenterTraj();
			double tDis=Math.pow(p.getLongitude(), 2)+Math.pow(p.getLatitude(), 2);
			double d=Math.abs(tDis-trajDis);
			if(Math.abs(d-dis)>DIS_THRESHOLD){
				if(d<dis){
					dis=d;
					subtrajs.clear();
					subtrajs.add(trajs.get(i));
				}
			}else{
				subtrajs.add(trajs.get(i));
			}
		}
		
		filtrateByTrajLen(subtrajs,traj);
		return calSimilarityNormal(subtrajs,traj,sd);
	}
	public static int calSimilarityNormal(List<Trajectory> trajs,Trajectory traj,SimularDef sd){
		double simi=-1;
		int trajID=-1;
		for(Trajectory t:trajs){
			SimilarityWithoutTime swt=new SimilarityWithoutTime(traj,t,sd);
			if(swt.getSimilarity()>simi&&t.ID!=traj.ID){
				simi=swt.getSimilarity();
				trajID=t.ID;
			}
		}
		System.out.print("相似度："+simi+"\t");
		return trajID;
	}

	public static void main(String[] args) throws IOException{
		SimularDef sd=new SimularDef();
		sd.setDtwDis_W(0.5);sd.setDtwDis_B(1000);
		sd.setEditDis_W(0.25);sd.setEditDis_B(1000);
		sd.setTsum_W(0);sd.setTsum_B(1000);
		sd.setShapeSum_W(0.25);sd.setShapeSum_B(1000);
		
//		System.out.println(System.getProperty("user.dir"));
		Vector<Trajectory> trajs=ReadTaxiData.readTrajs("./trajData/taxiData.txt");
		
		Trajectory traj=trajs.get(198);
		
		//普通循环
		System.out.print("普通循环>\t");
		long time=System.currentTimeMillis();
		int trajNum1=calSimilarityNormal(trajs,traj,sd);
		long duration=System.currentTimeMillis()-time;
		System.out.println("轨迹ID："+trajNum1+'\t'+duration+"ms");
		
		//特征值
		System.out.print("特征值>\t\t");
		time=System.currentTimeMillis();
		int trajNum2=calSimilarityEigenvalue(trajs,traj,sd);
		duration=System.currentTimeMillis()-time;
		System.out.println("轨迹ID："+trajNum2+'\t'+duration+"ms");


	}
}
