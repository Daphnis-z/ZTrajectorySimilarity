package trajOriginal;

import trajOriginal.CallR.ExpectType;

public class DemoRJava {
	
	public static void main(String[] args) {		
		CallR cr=new CallR();
		if(cr.connect())
			System.out.println("Connect R success..");
		
		//输出当前R环境相关信息
		System.out.println((String)cr.doRCmd("R.version.string",ExpectType.STRING));		
		cr.doRCmd("setwd('Y:/PROGRAMMING/others/trajSimilarity/trajSimilarity_R')");
		System.out.println("current working dir : \n\t"+
				cr.doRCmd("getwd()",ExpectType.STRING)+'\n');
		
		//调用R进行计算并计时
		long t1=System.currentTimeMillis();
		cr.doRCmd("source('trajTest.r')");
		long t2=System.currentTimeMillis();
		int trajNum=(int)cr.doRCmd("trajNum",ExpectType.INT);
		
		//结果输出
		System.out.printf("EditDist(%d): \n",trajNum);
		System.out.println("\ttotal: "+(t2-t1)+" ms");
		System.out.println("\tper: "+((t2-t1)/trajNum)+" ms");
				
		cr.closeR();
	}

}

