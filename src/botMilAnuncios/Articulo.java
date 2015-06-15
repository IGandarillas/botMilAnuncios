package botMilAnuncios;
import java.util.ArrayList;


public class Articulo {
	private boolean borrar;
	private String autoRenoveSegs;//Numero de horas para autorrenovar
	private String autoRHour;
	private String direccionIP;
	private String ID;
	private String titulo;
	private int fila;
	public Articulo(){
		
	}
	public Articulo(boolean borrar, String autoRenoveSegs, String direccionIP){
		this.borrar=borrar;
		this.autoRenoveSegs=autoRenoveSegs;
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
		
		autoRenoveSegs=rowElements.get(2);
	}
	
	public boolean isBorrar() {
		return borrar;
	}
	public void setBorrar(boolean borrar) {
		this.borrar = borrar;
	}
	public String getAutoRenoveSegs() {
		return autoRenoveSegs;
	}
	public void setAutoRenoveSegs(String autoRenoveHours) {
		this.autoRenoveSegs = autoRenoveHours;
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
	public String getAutoRHour() {
		return autoRHour;
	}
	public void setAutoRHour(String autoRHour) {
		this.autoRHour = autoRHour;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
