package data;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="message")
public class MessageObject {
	
	int messageId; 
	String content;
	String creationDate;
	String lastModDate;
	int creatorId;
	int forumId;
	
	public MessageObject() 
	{}
	
	public MessageObject(int id,int creatorId, int forumIdString, String lastModDate, String creationDate, String content)
	{
		this.messageId = id;
		this.content = content;
		this.creationDate = creationDate;
		this.lastModDate = lastModDate;
		this.creatorId = creatorId;
		this.forumId = forumId;
	}
	
	@XmlElement(name="id_message")
	public int getMessageId()
	{
		return this.messageId;
	}
	
	public void setMessageId(int id)
	{
		this.messageId=id;
	}
	
	@XmlElement(name="forum_id_message")
	public int getForumID()
	{
		return this.forumId;
	}
	
	public void setForumId(int id)
	{
		this.forumId=id;
	}
	
	@XmlElement(name="creator_id_message")
	public int getCreatorID()
	{
		return this.creatorId;
	}
	
	public void setCreatorId(int id)
	{
		this.creatorId=id;
	}
	
	@XmlElement(name="creation_date_message")
	public String getCreationDate()
	{
		return this.creationDate;
	}
	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}
	
	@XmlElement(name="last_mod_date_message")
	public String getLastModDate()
	{
		return this.lastModDate;
	}
	public void setLastModDate(String lastModDate)
	{
		this.lastModDate = lastModDate;
	}
	
	@XmlElement(name="content_message")
	public String getMessageContent()
	{
		return this.content;
	}
	
	public void setMessageContent(String content)
	{
		this.content=content;
	}
	
	public static LocalDate getCurrentDate()
	{
		return LocalDate.now();
	}
}