package daphnis.dataHandle;
import java.io.*;

public class ReadData {

	public static String[] getFilenames(String path){
		File dir=new File(path);
		String[] files=dir.list();
		return files;
	}
	
	public static void main(String[] args) throws IOException {
		String path="./src/daphnis/dataHandle/trajWithoutTime";
		String[] files=getFilenames(path);
		for(String fn:files){
			fn=path+'/'+fn;
			BufferedReader in=new BufferedReader(new FileReader(fn));
			in.readLine();
			String s;
			while((s=in.readLine())!=null){
				String[] jw=s.split(",");
				System.out.println(Double.parseDouble(jw[0])+"\t"+Double.parseDouble(jw[1]));
			}
			in.close();
		}
	}

}