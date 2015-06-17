package botMilAnuncios;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Read credentials and other global var from a .txt file, and storage it.
 * @author IGandarillas
 *
 */
public class GlobalVariables {
	private String email;
	private String passwd;
	private String path;//Path where is .txt file, same that .jar and .xls
	//Wait time between operation and operation.
	private int min; //Min wait time
	private int max; //Max wait time
	private String rutaExcel;
	public GlobalVariables(String path){
		this.setPath(path);
		openFile(path);
	}
	
	/**
	 * Leer credenciales
	 */
	public void openFile(String path){
		 File archivo = null;
	     FileReader fr = null;
	     BufferedReader br = null;

	     try {
	        // Apertura del fichero y creacion de BufferedReader para poder
	        // hacer una lectura comoda (disponer del metodo readLine()).
	        archivo = new File (path+"\\variables.txt");
	        fr = new FileReader (archivo);
	        br = new BufferedReader(fr);

	        // Lectura del fichero
	        String linea;
	        for(int i = 0; i < 5; i++){
	        	if((linea=br.readLine())!=null){
	        		switch(i){
		        		case 0:email=linea;
		        		break;
		        		case 1:passwd=linea;
		        		break;
		        		case 2:setMin(Integer.parseInt(linea));
		        		break;
		        		case 3:setMax(Integer.parseInt(linea));
		        		break;
		        		case 4:setRutaExcel(linea);
		        		break;
	        		}
	        	}
	        }
	   
	           
	     }
	     catch(Exception e){
	        e.printStackTrace();
	     }finally{
	        // En el finally cerramos el fichero, para asegurarnos
	        // que se cierra tanto si todo va bien como si salta 
	        // una excepcion.
	        try{                    
	           if( null != fr ){   
	              fr.close();     
	           }                  
	        }catch (Exception e2){ 
	           e2.printStackTrace();
	        }
	     }
	}
//Observers
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getRutaExcel() {
		return rutaExcel;
	}

	public void setRutaExcel(String rutaExcel) {
		this.rutaExcel = rutaExcel;
	}
	
}
