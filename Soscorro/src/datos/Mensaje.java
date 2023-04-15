package datos;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mensaje")
public class Mensaje {
	
	int mensajeId; 
	String contenido;
	String fechaCreacion;
	String fechaUltimaModificacion;
	int creatorId;
	int forumId;
	
	public Mensaje() 
	{}
	
	public Mensaje(int id, String contenido, String fechaCreacion, String fechaUltimaModificacion, int creatorId, int forumId)
	{
		this.mensajeId = id;
		this.contenido = contenido;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.creatorId = creatorId;
		this.forumId = forumId;
	}
	
	@XmlElement(name="id_mensaje")
	public int getMensajeID()
	{
		return this.mensajeId;
	}
	
	public void setMensajeId(int id)
	{
		this.mensajeId=id;
	}
	
	@XmlElement(name="forum_id_mensaje")
	public int getForumID()
	{
		return this.forumId;
	}
	
	public void setForumId(int id)
	{
		this.forumId=id;
	}
	
	@XmlElement(name="creator_id_mensaje")
	public int getCreatorID()
	{
		return this.creatorId;
	}
	
	public void setCreatorId(int id)
	{
		this.creatorId=id;
	}
	
	@XmlElement(name="fecha_creacion_mensaje")
	public String getFechaCreacion()
	{
		return this.fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}
	
	@XmlElement(name="fecha_ultima_modificacion_mensaje")
	public String getFechaUltimaModificacion()
	{
		return this.fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(String fechaCreacion)
	{
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	
	@XmlElement(name="contenido_mensaje")
	public String getMensajeContenido()
	{
		return this.contenido;
	}
	
	public void setMensajeContenido(String contenido)
	{
		this.contenido=contenido;
	}
	
	public static LocalDate getCurrentDate()
	{
		return LocalDate.now(); //TODO mirar esto porque nos raya
	}
}