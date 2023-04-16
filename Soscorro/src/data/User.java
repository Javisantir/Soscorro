package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {

	String name;
	int id; //TODO debemos pensar como meter esto, porque no debes mandar en el post el id
	
	public User()
	{	
		super();
	}
	
	public User(int id,String name)
	{
		this.id=id;
		this.name=name;
	}
	
	@XmlElement(name="name_user")
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	@XmlElement(name="id_user")
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}
	
	public String toString()
	{
		return "User with id="+ id +"; name="+name;
	}
}
