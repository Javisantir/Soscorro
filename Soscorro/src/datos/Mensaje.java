package datos;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mensaje")
public class Mensaje {
	
	int mensajeId; 
	String mensajeContenido;
	String mensajeFecha;
	
	public Mensaje() 
	{}
	
	public Mensaje(int id, String mensajeContenido)
	{
		this.mensajeId = id;
		this.mensajeFecha = LocalDate.now().toString(); //TODO mirar esto porque nos raya
		this.mensajeContenido = mensajeContenido;
	}
	
	@XmlElement(name="id_mensaje")
	public int getMensajeID()
	{
		return mensajeId;
	}
	
	public void setMensajeId(int id)
	{
		this.mensajeId=id;
	}
	
	@XmlElement(name="fecha_mensaje")
	public String getMensajeFecha()
	{
		return mensajeFecha;
	}
	
	public void setMensajeFecha(LocalDate fecha)
	{
		this.mensajeFecha=fecha.now().toString();
	}
	
	@XmlElement(name="contenido_mensaje")
	public String getMensajeContenido()
	{
		return mensajeContenido;
	}
	
	public void setMensajeContenido(String contenido)
	{
		this.mensajeContenido=contenido;
	}
}