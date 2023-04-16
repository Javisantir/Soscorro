package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="friendship")
public class FriendShip {
	
	User user;
	User friend;
	
	public FriendShip()
	{	}
	
	public FriendShip(User user, User friend)
	{
		this.user=user;
		this.friend=friend;
	}
	
	@XmlElement(name="user")
	public int getUserId()
	{
		return user.id;
	}
	
	public void setUser(User user)
	{
		this.user=user;
	}
	
	@XmlElement(name="friend")
	public int getFriendId()
	{
		return friend.id;
	}
	
	public void setAmigo(User friend)
	{
		this.user=friend;
	}
	
}
