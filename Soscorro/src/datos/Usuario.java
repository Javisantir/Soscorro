package datos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="usuario")
public class Usuario {

	String name;
	int id; //TODO debemos pensar como meter esto, porque no debes mandar en el post el id
	
	public Usuario()
	{	}
	
	public Usuario(String name)
	{
		this.id=0;
		this.name=name;
	}
	
	@XmlElement(name="nombre_usuario")
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	@XmlElement(name="id_usuario")
	public int getID()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
}
