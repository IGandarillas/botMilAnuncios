package botMilAnuncios;
import java.util.ArrayList;


public class Articulo {
	private boolean borrar;
	private String autoRenoveHours;//Numero de horas para autorrenovar
	private String direccionIP;
	private String ID;
	private int fila;
	public Articulo(){
		
	}
	public Articulo(boolean borrar, String autoRenoveHours, String direccionIP){
		this.borrar=borrar;
		this.autoRenoveHours=autoRenoveHours;
		this.direccionIP=direccionIP;
	}
	
	public Articulo(ArrayList<String> rowElements) {
		parseList(rowElements);
	}
	/**
	 * Configurar atributos del artículo.
	 * @param rowElements
	 */
	public void parseList(ArrayList<String> rowElements){
		direccionIP=rowElements.get(0);
		if(rowElements.get(1).equals("d")){
			borrar=true;
		}else{
			borrar=false;
		}
			
					
		
		autoRenoveHours=rowElements.get(2);
	}
	
	public boolean isBorrar() {
		return borrar;
	}
	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
	public String getAutoRenoveHours() {
		return autoRenoveHours;
	}
	public void setAutoRenoveHours(String autoRenoveHours) {
		this.autoRenoveHours = autoRenoveHours;
	}
	public String getDireccionIP() {
		return direccionIP;
	}
	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
}
