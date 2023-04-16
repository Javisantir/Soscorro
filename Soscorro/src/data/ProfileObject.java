package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="profile")
public class ProfileObject 
{
	User profileUser;
	MessageObject lastUserMessage;
	int friendsCount;
	MessageList lastFriendsMessages;
	
	public ProfileObject()
	{
		super();
	}
	
	public ProfileObject(User profileUser, MessageObject lastUserMessage, int friendsCount, MessageList lastFriendsMessages)
	{
		this.profileUser = profileUser;
		this.lastUserMessage = lastUserMessage;
		this.friendsCount = friendsCount;
		this.lastFriendsMessages = lastFriendsMessages;
	}
	
	@XmlElement(name="profileUser")
	public User getProfileUser()
	{
		return this.profileUser;
	}
	
	public void setName(User profileUser)
	{
		this.profileUser=profileUser;
	}
	
	@XmlElement(name="lastUserMessage")
	public MessageObject getLastUserMessage()
	{
		return this.lastUserMessage;
	}
	
	public void setLastUserMessage(MessageObject lastUserMessage)
	{
		this.lastUserMessage=lastUserMessage;
	}
	
	@XmlElement(name="friendsCount")
	public int getFriendsCount()
	{
		return this.friendsCount;
	}
	
	public void setFriendsCount(int friendsCount)
	{
		this.friendsCount=friendsCount;
	}
	
	@XmlElement(name="profile_user")
	public MessageList getLastFriendsMessages()
	{
		return this.lastFriendsMessages;
	}
	
	public void setLastFriendsMessages(MessageList lastFriendsMessages)
	{
		this.lastFriendsMessages=lastFriendsMessages;
	}
}
