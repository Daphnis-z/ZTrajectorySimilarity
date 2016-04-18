package com.adx.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.adx.entity.SimularDef;
import com.adx.resource.Constant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class DoubleTrajAction extends ActionSupport implements ModelDriven<SimularDef>{
	private SimularDef simularDef=new SimularDef();
	private File objectfile;
	private File testfile;
	public File getObjectfile() {
		return objectfile;
	}

	public void setObjectfile(File objectfile) {
		this.objectfile = objectfile;
	}

	public File getTestfile() {
		return testfile;
	}

	public void setTestfile(File testfile) {
		this.testfile = testfile;
	}

	private void setSimularDef(){
		Constant.simularDef=simularDef;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		setSimularDef();
		System.out.println(simularDef.getDtwDis_B());
		System.out.println(simularDef.getEditDis_B());
		System.out.println(simularDef.getShapeSum_B());
		try {
			BufferedReader br = new BufferedReader(new FileReader(objectfile));
			// 读取直到最后一行
			String line = "";
			while ((line = br.readLine()) != null) {
				// 把一行数据分割成多个字段
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					// 每一行的多个字段用TAB隔开表示
					System.out.print(st.nextToken() + "\t");
				}
				System.out.println();
			}
				br.close();
		} catch (FileNotFoundException e) {
			// 捕获File对象生成时的异常
			e.printStackTrace();
		} catch (IOException e) {
			// 捕获BufferedReader对象关闭时的异常
			e.printStackTrace();
		} 
		return SUCCESS;
	}

	//模型驱动实现请求数据的封装
	@Override
	public SimularDef getModel() {
		// TODO Auto-generated method stub
		return simularDef;
	}
}
