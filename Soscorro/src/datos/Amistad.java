package datos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="amistad")
public class Amistad {
	
	Usuario usuario;
	Usuario amigo;
	
	public Amistad()
	{	}
	
	public Amistad(Usuario usuario, Usuario amigo)
	{
		this.usuario=usuario;
		this.amigo=amigo;
	}
	
	@XmlElement(name="usuario")
	public int getUsuarioID()
	{
		return usuario.id;
	}
	
	public void setUsuario(Usuario usuario)
	{
		this.usuario=usuario;
	}
	
	@XmlElement(name="amigo")
	public int getAmigoID()
	{
		return amigo.id;
	}
	
	public void setAmigo(Usuario amigo)
	{
		this.usuario=amigo;
	}
	
}
