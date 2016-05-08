package com.daphnis.gis;

import com.opensymphony.xwork2.Action;

public class PolylineDemo {
	private double[] trajs={
			118.81761,31.98705,
			118.82761,31.97705,
			118.83761,31.96705,
			118.84761,31.95705,
			118.85761,31.94705,
			118.86761,31.93705,
			118.88761,31.92705,
			118.89761,31.91705,
			118.90761,31.90705,
			118.91761,31.80705,
			118.92761,31.81705,
			118.93761,31.82705
	};
	private String strTrajs;
	
	public String getStrTrajs() {
		return strTrajs;
	}
	public void setStrTrajs(String strTrajs) {
		this.strTrajs = strTrajs;
	}

	public String execute(){
		String straj="";
		for(int i=0;i<trajs.length;++i){
			straj+=trajs[i]+",";
		}
		strTrajs=straj.substring(0,straj.length()-1);
		
		return Action.SUCCESS;
	}
}
