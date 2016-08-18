package com.adx.chart;

import java.util.Vector;

import com.adx.datahandler.KMeans;
import com.adx.entity.Trajectory;
import com.adx.gis.ShowTraj;
import com.adx.dataread.ReadData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * file: AmCharts.java
 * note: ͼ��չʾ����
 * author: Daphnis
 * date: 2016��8��4�� ����4:47:35
 */
@SuppressWarnings("serial")
public class AmCharts extends ActionSupport {
	/** ��Ź켣���ݵ��ַ��� */
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
