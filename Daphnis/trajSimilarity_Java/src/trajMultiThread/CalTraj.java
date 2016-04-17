package trajMultiThread;
import java.util.Arrays;

import trajOriginal.*;
import trajOriginal.CallR.ExpectType;

public class CalTraj implements Runnable {
	private CallR callR;
	
	public CalTraj(){
		callR=new CallR();
	}
	public CalTraj(CallR cr){
		callR=cr;
	}
	
	@Override
	public void run() {
		System.out.println(Arrays.toString(
				(String[])callR.doRCmd("ls()",ExpectType.STRING_ARR)));
	}

}
