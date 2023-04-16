package data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "messages")
public class Messages {
	private ArrayList<Link> messages;
	
	public Messages()
	{
		this.messages = new ArrayList<Link>();
	}
	
	@XmlElement(name="message")
	public ArrayList<Link> getMessages()
	{
		return messages;
	}
	
	public void setMessages(ArrayList<Link> mensajes)
	{
		this.messages = mensajes;
	}

}