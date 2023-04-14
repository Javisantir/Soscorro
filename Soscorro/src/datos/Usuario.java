package datos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="usuario")
public class Usuario {

	String name;
	int id; //TODO debemos pensar como meter esto, porque no debes mandar en el post el id
	
	public Usuario()
	{	
		super();
	}
	
	public Usuario(int id,String name)
	{
		this.id=id;
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
	
	public String toString()
	{
		return "Usuario con id="+ id +"; name="+name;
	}
}
