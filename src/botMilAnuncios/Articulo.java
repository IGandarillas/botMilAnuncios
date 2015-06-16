package botMilAnuncios;

/**
 * This class define an Item 
 * @author IGandarillas
 *
 */
public class Articulo {
	private boolean borrar; //delete or not delete
	private String autoRenoveSegs;//secs to autorrenovar
	private String autoRHour; //Hours to autorrenovar
	private String direccionIP; //Unused
	private String ID; //id reference
	private String titulo; // Name
	private int fila; //Position in excel
	
	/**
	 * Constructur and observers
	 */
	public Articulo(){
		
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
