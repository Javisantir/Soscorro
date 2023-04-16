package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="friendship")
public class FriendShipObject {
	
	int userId;
	int friendId;
	
	public FriendShipObject()
	{	}
	
	public FriendShipObject(int userId, int friendId)
	{
		this.userId=userId;
		this.friendId=friendId;
	}
	
	@XmlElement(name="user")
	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int userId)
	{
		this.userId=userId;
	}
	
	@XmlElement(name="friend")
	public int getFriendId()
	{
		return friendId;
	}
	
	public void setFriendId(int friendId)
	{
		this.friendId=friendId;
	}
	
}
