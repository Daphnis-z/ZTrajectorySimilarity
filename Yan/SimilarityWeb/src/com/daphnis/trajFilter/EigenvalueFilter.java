/**
 * 利用轨迹特征值进行过滤，加速多轨迹的查询
 * @author Daphnis
 * 20160726
 */
package com.daphnis.trajFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;

import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.dataHandle.ReadData;
import com.daphnis.kMeans.KMeans;

public class EigenvalueFilter {		
	/**
	 * 利用轨迹的中心点进行过滤
	 * @param trajs
	 * @param traj
	 * @param sd
	 * @return
	 */
	private static List<Trajectory> filtrateByCenterPoint(List<Trajectory> trajs,Trajectory traj){		
		List<Trajectory> subTrajs=new ArrayList<Trajectory>();//存放与目标轨迹较相似的轨迹群	
		double trajDis=Math.pow(traj.getCenterTraj().getLongitude(), 2)+
				Math.pow(traj.getCenterTraj().getLatitude(), 2);
		double disThreshold=getDisThreshold(trajs,traj);
		
		final int NUM_THRESHOLD=(int)(90+trajs.size()*0.2)%500;
		do{
			List<Trajectory> tmpTrajs=subTrajs.size()>0? subTrajs:trajs;
			subTrajs=new ArrayList<Trajectory>();
			for(int i=0;i<tmpTrajs.size();++i){
				Point p=tmpTrajs.get(i).getCenterTraj();
				double tDis=Math.pow(p.getLongitude(), 2)+Math.pow(p.getLatitude(), 2);
				if(Math.abs(trajDis-tDis)<disThreshold){
					subTrajs.add(tmpTrajs.get(i));
				}
			}
			disThreshold/=1.2;
		}while(subTrajs.size()>NUM_THRESHOLD);
		return subTrajs;
	}
	
	//计算距离的阈值
	private static double getDisThreshold(List<Trajectory> trajs,Trajectory traj){
		Point p1=traj.getCenterTraj();
		double a=Math.pow(p1.getLongitude(), 2)+Math.pow(p1.getLatitude(), 2);
		double disSum=0;
		for(int i=1;i<=4;++i){
			int ix=(int)(trajs.size()/(i+0.1));
			Point p2=trajs.get(ix).getCenterTraj();
			double b=Math.pow(p2.getLongitude(), 2)+Math.pow(p2.getLatitude(), 2);
			disSum+=Math.abs(a-b);
		}
		return disSum/5;
	}
	
	
	/**
	 * 利用轨迹长度做过滤
	 * @param trajs
	 * @param traj
	 */
	private static List<Trajectory> filtrateByTrajLen(List<Trajectory> trajs,Trajectory traj){
		List<Trajectory> subTrajs=new ArrayList<Trajectory>();	
		double lenThreshold=0;	
		for(int i=1;i<=5;++i){
			int ix=(int)(trajs.size()/(i+0.1));
			lenThreshold+=Math.abs(trajs.get(ix).getTrajLen()-traj.getTrajLen());					
		}
		lenThreshold/=5;
		for(int i=0;i<trajs.size();++i){
			if(Math.abs(trajs.get(i).getTrajLen()-traj.getTrajLen())<lenThreshold){
				subTrajs.add(trajs.get(i));
			}
		}
		return subTrajs;
	}
	
	/**
	 * 过滤非常不相似轨迹
	 * @param trajs
	 * @param traj
	 * @return
	 */
	public static List<Trajectory> filtrateTraj(List<Trajectory> trajs,Trajectory traj){
		if(trajs.size()<100){//为提高精度，少于100条轨迹不进行过滤
			return trajs;
		}
		List<Trajectory> subTrajs=filtrateByCenterPoint(trajs,traj);
		subTrajs=filtrateByTrajLen(subTrajs,traj);
		return subTrajs;
	}
	
	//普通循环进行多轨迹的查询
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
	//使用过滤器以后的多轨迹查询
	public static int calSimilarityEigenvalue(List<Trajectory> trajs,Trajectory traj,SimularDef sd){		
		List<Trajectory> st=filtrateByCenterPoint(trajs,traj);
		st=filtrateByTrajLen(st,traj);
		return calSimilarityNormal(st,traj,sd);
	}
	
	//测试函数
	public static void testFileter(List<Trajectory> trajs,SimularDef sd){
		for(int i=0;i<trajs.size();++i){
			Trajectory traj=trajs.get(i);
			System.out.println("目标轨迹ID："+traj.ID);

			//普通循环
			System.out.print("普通计算>\t");
			long time=System.currentTimeMillis();
			int trajNum1=calSimilarityNormal(trajs,traj,sd);
			long duration=System.currentTimeMillis()-time;
			System.out.println("与目标轨迹最相似的轨迹ID："+trajNum1+
					"\t耗时："+duration+"ms");
			
			//使用特征值过滤后
			System.out.print("使用轨迹过滤器>\t");
			time=System.currentTimeMillis();
			int trajNum2=calSimilarityEigenvalue(trajs,traj,sd);
			duration=System.currentTimeMillis()-time;
			System.out.println("与目标轨迹最相似的轨迹ID："+trajNum2+
					"\t耗时："+duration+"ms\n");
		}				
	}
	
	//演示代码
	public static void main(String[] args) throws IOException{			
		SimularDef sd=new SimularDef();
		sd.setDtwDis_W(0.5);sd.setDtwDis_B(1000);
		sd.setEditDis_W(0.25);sd.setEditDis_B(1000);
		sd.setTsum_W(0);sd.setTsum_B(1000);
		sd.setShapeSum_W(0.25);sd.setShapeSum_B(1000);
		
//		System.out.println(System.getProperty("user.dir"));
//		Vector<Trajectory> trajs=ReadData.readTaxiTrajs("./trajData/taxiData.txt");			
		Vector<Trajectory> trajs=ReadData.readSomeTrajs("./trajData/geolife/",18670);	
		
		//去除离群点和冗余点
		long te=System.currentTimeMillis();
		for(int i=0;i<trajs.size();++i){
			Trajectory traj=trajs.get(i);
	    	KMeans kmeans = new KMeans(traj.getPoints());
	    	if(kmeans.init()){
		    	kmeans.calculate();
		    	kmeans.removeUnusefulPoints();	    	
		    	kmeans.dataCompression();
	    	}
		}
		System.out.println("去除离群点和冗余点耗时："+(System.currentTimeMillis()-te)+"ms");

//		SimilarityWithoutTime swt=new SimilarityWithoutTime(trajs.get(1234),trajs.get(9115),sd);
//		System.out.println(swt.getSimilarity());
		
//		testFileter(trajs,sd);
		
		Trajectory traj=trajs.get(1014);
		System.out.println("目标轨迹ID："+traj.ID);
				
		//普通循环
		System.out.print("普通计算>\t");
		long time=System.currentTimeMillis();
		int trajNum1=calSimilarityNormal(trajs,traj,sd);
		long duration=System.currentTimeMillis()-time;
		System.out.println("与目标轨迹最相似的轨迹ID："+trajNum1+
				"\t耗时："+duration+"ms");
		
		//使用特征值过滤后
		System.out.print("使用轨迹过滤器>\t");
		time=System.currentTimeMillis();
		int trajNum2=calSimilarityEigenvalue(trajs,traj,sd);
		duration=System.currentTimeMillis()-time;
		System.out.println("与目标轨迹最相似的轨迹ID："+trajNum2+
				"\t耗时："+duration+"ms");
	}

}

