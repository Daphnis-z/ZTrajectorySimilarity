package com.adx.entity;


/**
 * SimularDef
 * ���ƶȵĶ���ģ�͵�ʵ��
 * @author agnes
 *2016.4.17
 */
public class SimularDef {

	private double dtwDis_B=2;	//dtw�ľ�������ֵ
	private double editDis_B=100;	//�༭��������ֵ
	private long tsum_B=1440;	//ʱ��������ֵ
	private double shapeSum_B=100;	//��״�����ֵ
	private double dtwDis_W;	//dtw�ľ������Ȩ��
	private double editDis_W;	//�༭�������Ȩ��
	private double tsum_W;	//ʱ�������Ȩ��
	private double shapeSum_W;	//��״����Ȩ��
	private int timeStamp;	//�Ƿ��ʱ�����1Ϊ����0Ϊ����
		
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getDtwDis_B() {
		dtwDis_B=Constant.dtwDis_B;
		return dtwDis_B;
	}
	public void setDtwDis_B(double dtwDis_B) {
		this.dtwDis_B = dtwDis_B;
	}
	public double getEditDis_B() {
		editDis_B=Constant.editDis_B;
		return editDis_B;
	}
	public void setEditDis_B(double editDis_B) {
		this.editDis_B = editDis_B;
	}
	public double getEditDis_W() {
		return editDis_W;
	}
	public void setEditDis_W(double editDis_W) {
		this.editDis_W = editDis_W;
	}
	public long getTsum_B() {
		tsum_B=Constant.tsum_B;
		return tsum_B;
	}
	public void setTsum_B(long tsum_B) {
		this.tsum_B = tsum_B;
	}
	public double getShapeSum_B() {
		shapeSum_B=Constant.shapeSum_B;
		return shapeSum_B;
	}
	public void setShapeSum_B(double shapeSum_B) {
		this.shapeSum_B = shapeSum_B;
	}
	public double getDtwDis_W() {
		return dtwDis_W;
	}
	public void setDtwDis_W(double dtwDis_W) {
		this.dtwDis_W = dtwDis_W;
	}
	public double getTsum_W() {
		return tsum_W;
	}
	public void setTsum_W(double tsum_W) {
		this.tsum_W = tsum_W;
	}
	public double getShapeSum_W() {
		return shapeSum_W;
	}
	public void setShapeSum_W(double shapeSum_W) {
		this.shapeSum_W = shapeSum_W;
	}
}