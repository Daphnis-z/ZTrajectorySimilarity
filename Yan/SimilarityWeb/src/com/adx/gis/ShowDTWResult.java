package com.adx.gis;

import java.util.Vector;

import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.dataHandle.ReadData;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ShowDTWResult extends ActionSupport{
	private String strPoints;	
	public String getStrPoints() {
		return strPoints;
	}
	public void setStrPoints(String strPoints) {
		this.strPoints = strPoints;
	}

	@Override
	public String execute() throws Exception {
		Vector<Trajectory> trajs=ReadData.readTaxiTrajs("./trajData/taxiData.txt");	
		SimularDef sd=new SimularDef();
		sd.setDtwDis_W(0.5);sd.setDtwDis_B(1000);
		sd.setEditDis_W(0.25);sd.setEditDis_B(1000);
		sd.setTsum_W(0);sd.setTsum_B(1000);
		sd.setShapeSum_W(0.25);sd.setShapeSum_B(1000);
		
		Trajectory traj1=trajs.get(2),traj2=trajs.get(11);
		SimilarityWithoutTime swt=new SimilarityWithoutTime(traj1,traj2,sd);
		int[][] match=swt.getMatch();
		int msize=swt.getMatchsize();
		String s="";
		for(int i=0;i<msize;++i){
			Point p1=traj2.getPoints().get(match[i][0]),
					p2=traj1.getPoints().get(match[i][1]);
			s+=p1.getLongitude()+","+p1.getLatitude()+",";
			s+=p2.getLongitude()+","+p1.getLatitude();
			if(i!=msize-1){
				s+=',';
			}
		}
		strPoints=s;
		
		return super.execute();
	}
}
