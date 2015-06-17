package botMilAnuncios;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
	String path="";
	public LogWriter(String path){
		this.path=path;
	}
	public void writeLog(String msg){
		writeLog("",msg);
	}
	public void writeLog(String headmsg, String msg){
		 FileWriter fichero = null;
	        PrintWriter pw = null;
	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        try
	        {
	            fichero = new FileWriter(path+"\\log.txt",true);
	            pw = new PrintWriter(fichero);
	 
	                 pw.println(headmsg+dateFormat.format(date)+"\t"+msg);
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           try {
	           // Nuevamente aprovechamos el finally para 
	           // asegurarnos que se cierra el fichero.
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
	}
}
