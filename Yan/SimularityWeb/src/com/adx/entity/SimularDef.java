package com.adx.entity;

/**
 * SimularDef
 * 相似度的定义模型的实体
 * @author agnes
 *2016.4.17
 */
public class SimularDef {
	private double dtwDis_B;	//dtw的距离度量最坏值
	private double editDis_B;	//编辑距离度量最坏值
	private double tsum_B;	//时间戳度量最坏值
	private double shapeSum_B;	//形状度量最坏值
	private double dtwDis_W;	//dtw的距离度量权重
	private double editDis_W;	//编辑距离度量权重
	private double tsum_W;	//时间戳度量权重
	private double shapeSum_W;	//形状度量权重
	
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
	public double getEditDis_W() {
		return editDis_W;
	}
	public void setEditDis_W(double editDis_W) {
		this.editDis_W = editDis_W;
	}
	public double getTsum_B() {
		return tsum_B;
	}
	public void setTsum_B(double tsum_B) {
		this.tsum_B = tsum_B;
	}
	public double getShapeSum_B() {
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
