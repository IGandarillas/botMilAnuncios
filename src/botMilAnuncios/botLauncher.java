package botMilAnuncios;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class botLauncher {
	
	private static final String url = "http://www.milanuncios.com/mis-anuncios/";
	public static void main(String[] args) throws IOException {
		//Create Frame.
		View view = new View();
		view.main(args);

		String path = new File (".").getCanonicalPath(); //Path current folder
		GlobalVariables cred= new GlobalVariables(path);//Read credenciales
		System.out.println(cred.getRutaExcel());
		XMLReader read=new XMLReader(path,cred.getRutaExcel());
		System.out.println("Excel leido");
		System.out.println("Peticion HTTP al servidor");
		LogWriter log= new LogWriter(path);
		
		HTTPConnection connect= new HTTPConnection();	
		HtmlPage page = connect.loadURL(url);//Load URL

		HtmlPage page2 = connect.login(page,cred.getEmail(),cred.getPasswd());//Login Milanuncios	
		log.writeLog("\n","[LOGIN]\t"+cred.getEmail()+";");
		
		if(connect.isLogin()){
			System.out.println("Login exitoso. Email: "+cred.getEmail());
			System.out.println(("Articulos a modificar: "+read.getModCount()[1]+"\n"));
			
			ArrayList<Articulo> modArticulos = read.getArticulos();//Excel articles must be modified	
			
			int contDeleted=0;//Deleted items count
			int contAutoR=0; //Items autorrenovados
			int min= cred.getMin();
			int max=cred.getMax();
			
			for(Articulo a: modArticulos){		
				
				int delay = (int) Math.floor(Math.random()*(max-min)+min); //Random time between min max.
				String itemId = a.getID();
				String itemName=a.getTitulo();
				
				
				/**
				 * Funcionality, http://www.milanuncios.com/mis-anuncios/+ID+.htm
				 * know if an item is in mis-anuncios list
				 */
				if(itemId != null){
					//Delete and Autorrenove have mutual exclusion, Delete has more weight.
					if(a.isBorrar()){//Delete
						contDeleted++;
						connect.loadURL("http://www.milanuncios.com/cmd/?comando="+"borrar"+"&id="+itemId);
						page2=connect.loadURL(url);
						log.writeLog("[BORRADO]\t"+itemName+"\t"+itemId+";");
						System.out.println("Fila: "+a.getFila()+" BORRADO: "+itemName+" ha sido borrado"); 
						
					}else{//Autorrenove
						contAutoR++;						
						if(connect.autoRenovar(page2, a)){
							switch(a.getAutoRHour()){
								case "0": 
									log.writeLog("[AUTORRENOVADO]\tdesactivado\t"+itemName+"\t"+itemId+";");
									System.out.println("Fila "+(a.getFila()+1)+": AUTORRENOVADO desactivado: "+itemName);
									break;
								case "1": 
									log.writeLog("[AUTORRENOVADO]\tuna hora\t"+itemName+"\t"+itemId+";");
									System.out.println("Fila "+(a.getFila()+1)+": AUTORRENOVADO cada hora: "+itemName);
									break;								
								default: 
									log.writeLog("[AUTORRENOVADO]\t"+a.getAutoRHour()+" horas\t"+itemName+"\t"+itemId+";");
									System.out.println("Fila "+(a.getFila()+1)+": AUTORRENOVADO cada "+a.getAutoRHour()+" horas: "+itemName);
							}
						}
					}
				}else{
					//Item is not in mis-articulos.html
					System.out.println("Fila "+a.getFila()+": este item no esta entre sus anuncios");
				}
				try {
					//System.out.println("Espera aleatoria de "+delay+" segundos.");
					Thread.sleep(delay*1000);					
				} catch (InterruptedException e) {
					System.out.println("Catch thread sleep");
					e.printStackTrace();
				}

				
			} 
			System.out.println("");
			switch(contDeleted){
			case 0: System.out.println("No se ha eliminado ningun elemento");
				break;
			case 1: System.out.println("Se ha eliminado un elemento");
				break;
			default: System.out.println("Se han eliminado "+contDeleted+" elementos");
			}
			switch(contAutoR){
			case 0: System.out.println("No se ha autorrenovado ningun elemento");
				break;
			case 1: System.out.println("Se ha autorrenovado un elemento");
				break;
			default: System.out.println("Se han autorrenovado "+contAutoR+" elementos");
			}
		}else{
			log.writeLog("[LOGIN]\tdatos incorrectos\t");
		}
	}

}
