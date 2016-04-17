package trajOriginal;

import org.rosuda.JRI.Rengine;

/***
 * remember to close R(closeR())
 * @author Daphnis
 *
 */
public class CallR {
	/**
	 * expect value type
	 */
	public enum ExpectType{
		INT,INT_ARR,
		STRING,STRING_ARR,
		DOUBLE,DOUBLE_ARR
	}
	private Rengine rengine;
	
	/***
	 * connect R
	 * @return boolean
	 */
	public boolean connect(){
		rengine=new Rengine(new String[]{"--vanilla"},false,null);
		if(!rengine.waitForR())
			return false;
		return true;		
	}
	
	/***
	 * close R
	 */
	public void closeR(){
		if(rengine!=null)
			rengine.end();
	}
	
	/***
	 * run R command
	 * @param cmd
	 * @param et (Expect return type)
	 * @return
	 */
	public Object doRCmd(String cmd,ExpectType et){
		switch(et){
		case INT:return rengine.eval(cmd).asInt();
		case INT_ARR:return rengine.eval(cmd).asIntArray();
		case STRING:return rengine.eval(cmd).asString();
		case STRING_ARR:return rengine.eval(cmd).asStringArray();
		case DOUBLE:return rengine.eval(cmd).asDouble();
		case DOUBLE_ARR:return rengine.eval(cmd).asDoubleArray();
			default:return null;
		}
	}
	
	/***
	 * run R command without return value
	 * @param cmd
	 */
	public void doRCmd(String cmd){
		rengine.eval(cmd);
	}
}

