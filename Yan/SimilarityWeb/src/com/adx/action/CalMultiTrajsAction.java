/**
 * 
 */
package com.adx.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.adx.datahandler.EigenvalueFilter;
import com.adx.datahandler.Utility;
import com.adx.entity.Point;
import com.adx.entity.SimularDef;
import com.adx.entity.Trajectory;
import com.adx.similaralg.Similarity;
import com.adx.similaralg.SimilarityWithTime;
import com.adx.similaralg.SimilarityWithoutTime;
import com.daphnis.dataHandle.DataUpload;
import com.daphnis.dataHandle.ReadData;
import com.daphnis.gis.ShowTraj;
import com.daphnis.kMeans.KMeans;
import com.opensymphony.xwork2.ActionSupport;

/**
 * file: CalMultiTrajsAction.java
 * note: 执行多轨迹的计算
 * author: Daphnis
 * date: 2016年8月10日 下午4:40:50
 */
@SuppressWarnings("serial")
public class CalMultiTrajsAction extends ActionSupport{
	private final String DATA_PATH="./data/trajData/";
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private double[] similarity;
	private String actionResult;
	private int[] indexes;
	private Trajectory[] similarestTraj;
	private Point[] similarestPoint;
	
	/** 请求的计算类型 */
	private String reqType;
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	/** 服务器端数据集名称 */
	private String dataName;	
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	//显示结果
	private String result;	
	public String getResult() {
		return result;
	}
	//可视化原轨迹
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	//可视化最相似的子轨迹
	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}	
	//可视化最相似的轨迹点
	private String strPoints;	
	public String getStrPoints() {
		return strPoints;
	}
	
	public Trajectory[] getSimilarestTraj() {
		return similarestTraj;
	}

	public Point[] getSimilarestPoint() {
		return similarestPoint;
	}
	
	public int[] getIndexes() {
		return indexes;
	}
	
	public String getActionResult() {
		return actionResult;
	}

	public File getObjectfile() {
		return objectfile;
	}

	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}

	public double[] getSimilarity() {
		return similarity;
	}
	
	private List<Trajectory> dataPreprocessing(List<Trajectory> trajs,Trajectory objtraj){
		for(Trajectory traj:trajs){
	    	KMeans kmeans = new KMeans(traj.getPoints());
	    	if(kmeans.init()){
		    	kmeans.calculate();
		    	kmeans.removeUnusefulPoints();	    	
		    	kmeans.dataCompression();
	    	}
		}
		List<Trajectory> subtrajs=EigenvalueFilter.filtrateTraj(trajs, objtraj);
		return subtrajs;
	}
							
	@Override
	public String execute() throws Exception {	
		List<Trajectory> trajs=null;
		Trajectory objTraj=null;
		if(reqType==null){
			actionResult=SUCCESS;
			return actionResult;
		}else if(reqType.equals("cal")){//读取缓存里的数据
			trajs=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef);
			objTraj=trajs.get(trajs.size()-1);//获取目标轨迹
			trajs.remove(trajs.size()-1);//移除目标轨迹
		}else{//读取服务器端的数据
			String path=DATA_PATH+dataName+"/";
			File dir=new File(path);
			if(!dir.exists()){
				actionResult=ERROR;
				return actionResult;
			}
			objTraj=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef).get(0);
			trajs=dataPreprocessing(ReadData.readSomeTrajs(path, -1),objTraj);
		}
		
		indexes=new int[trajs.size()];
		similarity=new double[trajs.size()];
		ArrayList<Similarity> dtwExample=new ArrayList<Similarity>();
		Similarity dtw;
		for (int i=0;i<trajs.size();i++){
			Trajectory traj=trajs.get(i);
			if(simularDef.getTimeStamp()!=1){
				 dtw=new SimilarityWithoutTime(objTraj, traj,simularDef);
			}else{
				 dtw=new SimilarityWithTime(objTraj,traj,simularDef);
			}
			dtwExample.add(dtw);
			similarity[i]=dtw.getSimilarity();
		}
		indexes=Utility.orderByValue(similarity);
		similarestTraj=dtwExample.get(indexes[0]).getSimilarestTraj();
		similarestPoint=dtwExample.get(indexes[0]).getSimilarestPoint();
		createResult(trajs,objTraj.name);
		readyForViewTrajs(objTraj, trajs);
		
		actionResult=SUCCESS;
	    return actionResult;
	}
	
	/**
	 * 创建在前台显示的计算结果
	 * @param trajs
	 * @param objname
	 */
	private void createResult(List<Trajectory> trajs,String objname){
 		result="目标轨迹：\r\n&nbsp"+objname+"\r\n\r\n测试轨迹群按相似度排序如下：\r\n";
 		int size=similarity.length>10? 10:similarity.length;
 		for(int i=0;i<size;i++){
 			result+=(i+1)+"）.目标轨迹与测试轨迹（"+trajs.get(indexes[i]).name+"）的相似度为："+
 					similarity[indexes[i]]+"%\r\n";
 		}
	}

	/**
	 * 为可视化轨迹做好准备
	 * @param objTraj
	 * @param trajs
	 */
	private void readyForViewTrajs(Trajectory objTraj, List<Trajectory> trajs) {
		strTrajs=packageTrajs(objTraj,trajs);
		strSubtrajs=packageSubTrajs(similarestTraj[0],similarestTraj[1]);
		strPoints=packagePoints(similarestPoint);
	}	
	/**
	 * 打包待可视化的轨迹
	 * @param objTraj
	 * @param trajs
	 * @return
	 */
	private String packageTrajs(Trajectory objTraj, List<Trajectory> trajs){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(objTraj);
		for(int i=0;i<indexes.length&i<10;++i){
			vt.addElement(trajs.get(indexes[i]));
		}
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包待可视化的子轨迹
	 * @param traj1
	 * @param traj2
	 * @return
	 */
	private String packageSubTrajs(Trajectory traj1,Trajectory traj2){
		Vector<Trajectory> vt=new Vector<Trajectory>();
		vt.addElement(traj1);
		vt.addElement(traj2);
		return ShowTraj.convertSomeTrajs(vt);
	}
	/**
	 * 打包最相似的轨迹点
	 * @param points
	 * @return
	 */
	private String packagePoints(Point[] points){
		Trajectory traj=new Trajectory();
		for(int i=0;i<points.length;++i){
			traj.addPoint(points[i]);
		}
		return ShowTraj.convertTraj(traj);
	}
	
}

