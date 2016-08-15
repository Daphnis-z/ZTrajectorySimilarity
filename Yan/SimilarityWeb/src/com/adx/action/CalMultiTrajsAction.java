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
 * note: ִ�ж�켣�ļ���
 * author: Daphnis
 * date: 2016��8��10�� ����4:40:50
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
	
	/** ����ļ������� */
	private String reqType;
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	/** �����������ݼ����� */
	private String dataName;	
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	
	//��ʾ���
	private String result;	
	public String getResult() {
		return result;
	}
	//���ӻ�ԭ�켣
	private String strTrajs;
	public String getStrTrajs() {
		return strTrajs;
	}
	//���ӻ������Ƶ��ӹ켣
	private String strSubtrajs;	
	public String getStrSubtrajs() {
		return strSubtrajs;
	}	
	//���ӻ������ƵĹ켣��
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
		}else if(reqType.equals("cal")){//��ȡ�����������
			trajs=ReadData.readCacheData(DataUpload.SAVE_PATH,simularDef);
			objTraj=trajs.get(trajs.size()-1);//��ȡĿ��켣
			trajs.remove(trajs.size()-1);//�Ƴ�Ŀ��켣
		}else{//��ȡ�������˵�����
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
	 * ������ǰ̨��ʾ�ļ�����
	 * @param trajs
	 * @param objname
	 */
	private void createResult(List<Trajectory> trajs,String objname){
 		result="Ŀ��켣��\r\n&nbsp"+objname+"\r\n\r\n���Թ켣Ⱥ�����ƶ��������£�\r\n";
 		int size=similarity.length>10? 10:similarity.length;
 		for(int i=0;i<size;i++){
 			result+=(i+1)+"��.Ŀ��켣����Թ켣��"+trajs.get(indexes[i]).name+"�������ƶ�Ϊ��"+
 					similarity[indexes[i]]+"%\r\n";
 		}
	}

	/**
	 * Ϊ���ӻ��켣����׼��
	 * @param objTraj
	 * @param trajs
	 */
	private void readyForViewTrajs(Trajectory objTraj, List<Trajectory> trajs) {
		strTrajs=packageTrajs(objTraj,trajs);
		strSubtrajs=packageSubTrajs(similarestTraj[0],similarestTraj[1]);
		strPoints=packagePoints(similarestPoint);
	}	
	/**
	 * ��������ӻ��Ĺ켣
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
	 * ��������ӻ����ӹ켣
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
	 * ��������ƵĹ켣��
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

