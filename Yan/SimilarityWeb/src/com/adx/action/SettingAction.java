package com.adx.action;

import com.adx.resource.Constant;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SettingAction extends ActionSupport {
	private double dtwDis_B;	//dtw�ľ�������ֵ
	private double editDis_B;	//�༭��������ֵ
	private long tsum_B;	//ʱ��������ֵ
	private double shapeSum_B;	//��״�����ֵ
	private String msg;
	
	public String getMsg() {
		return msg;
	}

	public double getDtwDis_B() {
		return dtwDis_B;
	}

	public void setDtwDis_B(double dtwDis_B) {
		this.dtwDis_B = dtwDis_B;
	}

	public double getEditDis_B() {
		return editDis_B;
	}

	public void setEditDis_B(double editDis_B) {
		this.editDis_B = editDis_B;
	}

	public long getTsum_B() {
		return tsum_B;
	}

	public void setTsum_B(long tsum_B) {
		this.tsum_B = tsum_B;
	}

	public double getShapeSum_B() {
		return shapeSum_B;
	}

	public void setShapeSum_B(double shapeSum_B) {
		this.shapeSum_B = shapeSum_B;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(dtwDis_B==0||editDis_B==0||tsum_B==0||shapeSum_B==0){
			msg="����д���е�ֵ";
			return NONE;
		}
		Constant.dtwDis_B=dtwDis_B;
		Constant.editDis_B=editDis_B;
		Constant.shapeSum_B=shapeSum_B;
		Constant.tsum_B=tsum_B;;
		msg="���óɹ�����";
		return SUCCESS;
	}
	
}
