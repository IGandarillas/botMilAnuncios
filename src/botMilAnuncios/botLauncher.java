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
		
		XMLReader read=new XMLReader(path);
		System.out.println("Excel leido");
		
					
		HTTPConnection connect= new HTTPConnection();	
		HtmlPage page = connect.loadURL(url);//Load URL
		

		Credenciales cred= new Credenciales(path);//Read credenciales
		HtmlPage page2 = connect.login(page,cred.getEmail(),cred.getPasswd());//Login Milanuncios	
		
		if(connect.isLogin()){
			System.out.println("Login exitoso");
			System.out.println(("De un total de "+read.getModCount()[0]+" filas, se van a modificar "+read.getModCount()[1]));
			
			ArrayList<Articulo> modArticulos = read.getArticulos();//Excel articles must be modified	
			
			int contDeleted=0;//Deleted items count
			int contAutoR=0;
			for(Articulo a: modArticulos){			
				String itemId = a.getID();

				if(itemId != null){		
					if(a.isBorrar()){
						contDeleted++;
						page = connect.loadURL(
							"http://www.milanuncios.com/cmd/?comando="+"borrar"+"&id="+itemId);
						System.out.println("Fila: "+a.getFila()+" BORRADO: "+itemId+" ha sido borrado"); 
						
					}else{
						contAutoR++;
						a.setID(itemId);
						if(connect.autoRenovar(page2, a))
							System.out.println("Fila "+a.getFila()+": AUTORRENOVADO: "+itemId+" cada "+a.getAutoRenoveHours());
					}
				}else{
					//Si el artículo no se ha podido encontrar
					System.out.println("Fila "+a.getFila()+": este item no esta entre sus anuncios");
				}

				
			} 
			
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
		}
	}

}
