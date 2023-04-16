package data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user_list")
public class UserList {
	private ArrayList<Link> users;
	
	public UserList()
	{
		this.users = new ArrayList<Link>();
	}
	
	@XmlElement(name="user")
	public ArrayList<Link> getUsers()
	{
		return users;
	}
	
	public void setUsers(ArrayList<Link> users)
	{
		this.users = users;
	}

}
