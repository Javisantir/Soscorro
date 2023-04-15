package datos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mensajes")
public class Mensajes {
	private ArrayList<Link> mensajes;
	
	public Mensajes()
	{
		this.mensajes = new ArrayList<Link>();
	}
	
	@XmlElement(name="mensaje")
	public ArrayList<Link> getUsuarios()
	{
		return mensajes;
	}
	
	public void setUsuarios(ArrayList<Link> mensajes)
	{
		this.mensajes = mensajes;
	}

}