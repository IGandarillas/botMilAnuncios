package botMilAnuncios;
import java.io.IOException;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;


public class HTTPConnection {
	private WebClient wb;
	private boolean login;//True si hace login
	public HTTPConnection(){
		//Config webClient
		wb = new WebClient();
		wb.waitForBackgroundJavaScript(1000);
		wb.getCookieManager().setCookiesEnabled(true);//!important
		wb.getOptions().setJavaScriptEnabled(true);//!Important
		wb.getOptions().setCssEnabled(false);
	    wb.getOptions().setUseInsecureSSL(true);
	    wb.getOptions().setThrowExceptionOnScriptError(false);
		wb.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wb.setAjaxController(new NicelyResynchronizingAjaxController()); //!Imporant
		//Avoid warning log
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
		
		@SuppressWarnings("unused")
		//Event, when posible modal alert do...
		ConfirmHandler acceptHandler = new ConfirmHandler(){
			@Override
            public boolean handleConfirm(Page page, String message) {
					System.out.println("Se ha confirmado");
                   return false;
            }
		};
		//Event, when alert show alert.
		wb.setAlertHandler(new AlertHandler() {            
            @Override
            public void handleAlert(Page page, String message) {
            	undefined(message);
                login(message);
                	
                 
            }
            //Alerta Login incorrecto.
            public boolean login(String message){
            	login = !message.startsWith("El email o la ", 0);
            	if(!login)
            		 System.out.println("Contraseña o email incorrectos");
            	return login;
            }
            public void undefined(String message){            	
            	if(message.startsWith("undefined", 0))
            		 System.out.println("Este articulo no esta en su lista de articulos");
            	
            }
        });
	}
	//Load an url
	public HtmlPage loadURL(String url){
		try {
			return wb.getPage(url);
		
		} catch (FailingHttpStatusCodeException | IOException e) {
			System.out.println("Error al cargar la página");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * De la página que se le pasa obtiene los campos email, contraseña y enviar.
	 * Rellena los dos primeros campos y hace submit al último.
	 * @param page HtmlPage
	 * @param email String
	 * @param passwd String
	 * @return HtmlPage tras hacer submit.
	 * @throws IOException
	 */
	public HtmlPage login(HtmlPage page,String email, String passwd) throws IOException{
		
		HtmlInput userInput = (HtmlInput) page.getElementById("email");
		userInput.setValueAttribute(email);
		HtmlInput passwdInput = (HtmlInput) page.getElementById("contra");
		passwdInput.setValueAttribute(passwd);		
		HtmlForm form = page.getForms().get(0);
		HtmlSubmitInput submit = form.getInputByValue("ENVIAR >>");
		setLogin(true);
		return (HtmlPage) submit.click();
	}
	
	public HtmlAnchor getArticulo(HtmlPage page,String href) {
		try{
			return page.getAnchorByHref(href);
		}catch(com.gargoylesoftware.htmlunit.ElementNotFoundException e){
			System.out.println("No se ha encontrado "+href+" en Page");
		}
		return null;
		
	}
	
	public WebClient getWb(){
		return wb;
	}
	

	public boolean autoRenovar(HtmlPage page, Articulo a){
		
		ScriptResult result = page.executeJavaScript("javascript:ventana('renovar/?id=','"+a.getID()+"')");		
		HtmlPage pageRenove = (HtmlPage) result.getNewPage();			
		HtmlSelect userInput = (HtmlSelect) pageRenove.getElementById("cada");
		try{	
			userInput.setSelectedAttribute(a.getAutoRenoveSegs(), true);
			return true;
		}catch(NullPointerException e){
			System.out.println("No se pudo elegir opción");
		}
		return false;
	}
	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
}
