package datos;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mensaje")
public class mensaje {
	
	int mensajeId; //TODO ver como automatizar los ids
	String mensajeContenido;
	LocalDate mensajeFecha;
	
	public mensaje() 
	{}
	
	public mensaje(String mensajeContenido)
	{
		this.mensajeId = 0;
		this.mensajeFecha = mensajeFecha.now();
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
	public LocalDate get()
	{
		return mensajeFecha;
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
