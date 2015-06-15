package botMilAnuncios;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Lee las credenciales de un fichero .txt y las almacena.
 * @author IGandarillas
 *
 */
public class Credenciales {
	private String email;
	private String passwd;
	private String path;
	public Credenciales(String path){
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
	        archivo = new File (path+"\\login.txt");
	        fr = new FileReader (archivo);
	        br = new BufferedReader(fr);

	        // Lectura del fichero
	        String linea;
	        for(int i = 0; i < 2; i++){
	        	if((linea=br.readLine())!=null){
	        		if(i==0){
	        			email=linea;
	        			//System.out.println(linea);
	        		}else{
	        			passwd=linea;
	        			//System.out.println(linea);
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
	
}
