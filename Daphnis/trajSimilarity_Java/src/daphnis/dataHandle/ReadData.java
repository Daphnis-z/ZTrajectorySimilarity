package daphnis.dataHandle;
import java.io.*;

public class ReadData {

	public static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}
	
	public static void main(String[] args) {
		String path="./src/daphnis/dataHandle/trajWithoutTime";
		String[] files=getFilenames(path);
		for(String s:files)
			System.out.println(s);
	}

}
