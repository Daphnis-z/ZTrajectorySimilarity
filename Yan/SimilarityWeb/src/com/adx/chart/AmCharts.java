package com.adx.chart;

import java.util.Vector;

import com.adx.datahandler.KMeans;
import com.adx.entity.Trajectory;
import com.adx.gis.ShowTraj;
import com.adx.dataread.ReadData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * file: AmCharts.java
 * note: 图表展示功能
 * author: Daphnis
 * date: 2016年8月4日 下午4:47:35
 */
@SuppressWarnings("serial")
public class AmCharts extends ActionSupport {
	/** 存放轨迹数据的字符串 */
	private String strTrajs;	
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}


	@Override
	public String execute() throws Exception {
		Vector<Trajectory> trajs=ReadData.readSomeTrajs("./trajData/geolife/",6,0);	
		for(int i=0;i<trajs.size();++i){
	    	KMeans kmeans = new KMeans(trajs.get(i).getPoints());
	    	kmeans.run();
		}
		strTrajs=ShowTraj.convertSomeTrajs(trajs);
		return SUCCESS;
	}
}
