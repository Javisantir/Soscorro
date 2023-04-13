package datos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="amistad")
public class amistad {
	
	Usuario usuario;
	Usuario amigo;
	
	public amistad()
	{	}
	
	public amistad(Usuario usuario, Usuario amigo)
	{
		this.usuario=usuario;
		this.amigo=amigo;
	}
	
	@XmlElement(name="usuario")
	public Usuario getUsuario()
	{
		return usuario;
	}
	
	public void setUsuario(Usuario usuario)
	{
		this.usuario=usuario;
	}
	
	@XmlElement(name="amigo")
	public Usuario getAmigo()
	{
		return amigo;
	}
	
	public void setAmigo(Usuario amigo)
	{
		this.usuario=amigo;
	}
	
}
