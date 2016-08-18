/**
 * 利用轨迹特征值进行过滤，加速多轨迹的查询
 * @author Daphnis
 * 20160726
 */
package com.adx.datahandler;
import java.util.ArrayList;
import java.util.List;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.SimilarityWithoutTime;

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
		double disThreshold=getDisThreshold(trajs,traj);
		
		final int NUM_THRESHOLD=(int)(90+trajs.size()*0.2)%500;
		do{
			List<Trajectory> tmpTrajs=subTrajs.size()>0? subTrajs:trajs;
			subTrajs=new ArrayList<Trajectory>();
			for(int i=0;i<tmpTrajs.size();++i){
				if(calCenterDis(traj, tmpTrajs.get(i))<disThreshold){
					subTrajs.add(tmpTrajs.get(i));
				}
			}
			disThreshold/=1.2;
		}while(subTrajs.size()>NUM_THRESHOLD);
		return subTrajs;
	}
	
	/**
	 * 计算距离的阈值
	 * @param trajs
	 * @param traj
	 * @return
	 */
	private static double getDisThreshold(List<Trajectory> trajs,Trajectory traj){
		int num=5,len=trajs.size();
		int diff=len/num;
		double disSum=0;
		for(int i=0;i<len;i+=diff){
			disSum+=calCenterDis(traj, trajs.get(i));
		}
		return disSum/num;
	}
	/**
	 * 计算两条轨迹中心点的距离
	 * @param traj1
	 * @param traj2
	 * @return
	 */
	private static double calCenterDis(Trajectory traj1,Trajectory traj2){
		Point p1=traj1.getCenterTraj(),p2=traj2.getCenterTraj();
		return Math.pow(p1.getLongitude()-p2.getLongitude(), 2)+
				Math.pow(p1.getLatitude()-p2.getLatitude(), 2);
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
		if(subTrajs.size()>40){
			subTrajs=filtrateByTrajLen(subTrajs,traj);
		}
		return subTrajs;
	}
	
	//普通循环进行多轨迹的查询
	public static int calSimilarityNormal(List<Trajectory> trajs,Trajectory traj,SimularDef sd){
		double simi=-1;
		int trajID=-1;
		for(Trajectory t:trajs){
			SimilarityWithoutTime swt=new SimilarityWithoutTime(traj,t,sd);
			if(swt.getSimilarity()>simi){
				simi=swt.getSimilarity();
				trajID=t.ID;
			}
		}
		System.out.print("相似度："+simi+"\t");
		return trajID;
	}
	//使用过滤器以后的多轨迹查询
	public static int calSimilarityEigenvalue(List<Trajectory> trajs,Trajectory traj,SimularDef sd){		
		List<Trajectory> st=filtrateTraj(trajs, traj);
		return calSimilarityNormal(st,traj,sd);
	}
}

