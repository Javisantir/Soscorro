package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import bbdd.Connect;
import data.Link;
import data.MessageList;
import data.MessageObject;
import data.User;

@Path("/messages")

public class Message {

	@Context
	private UriInfo uriInfo;

	public Message() 
	{	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getAllMessages(@QueryParam("forumId") String userIdStr,
			@QueryParam("creatorId") @DefaultValue("-1") String idStr,
			@QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("10") String countStr,
			@QueryParam("startDate") @DefaultValue("1900-01-01") String startDateStr,
			@QueryParam("endDate") @DefaultValue("2100-01-01") String endDateStr,
			@QueryParam("contentPattern") @DefaultValue("") String contentPattern,
			@QueryParam("selectMessagesFromIdCreator") @DefaultValue("true") String selectMessagesFromIdCreator) // TODO si vale true = si faler false != en filter
	{
		try
		{
			Connection conn = Connect.getInstance().getConnection();
			
			int userId = Integer.parseInt(userIdStr);
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			int int_idStr = Integer.parseInt(idStr);
			boolean applyFilter = selectMessagesFromIdCreator.equals("true");
			String filter = "";
			if (int_idStr != -1)
			{
				if (applyFilter)
					filter = "creatorId=" + int_idStr + " AND ";
				else
					filter = "creatorId!=" + int_idStr + " AND ";
			}
			String sql = "SELECT * FROM Soscorro.Messages WHERE " + filter + " forumId= " + userId + " AND content LIKE \"%" + contentPattern + "%\" AND Soscorro.Messages.creationDate BETWEEN '" + startDateStr + "' AND '" + endDateStr + "' LIMIT "+ count +" OFFSET " + offset + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			MessageList messages = new MessageList();
			ArrayList<Link> lista = messages.getMessages();
			String uriStr = "/";
			if(uriInfo.getAbsolutePath().toString().endsWith("/"))
			{
				uriStr = "";
			}
			while (rs.next()) 
			{
				lista.add(new Link(uriInfo.getAbsolutePath() + uriStr + rs.getInt("messageId"),"self"));
			}
			return Response.status(Response.Status.OK).entity(messages).build(); 
		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números").build();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}

	@GET
    @Path("{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
	
    public Response getMessage(@PathParam("messageId") String id) 
    {
        try 
        {
            Connection conn = Connect.getInstance().getConnection();
            int int_id = Integer.parseInt(id);
            String sql = "SELECT * FROM Soscorro.Messages where messageId = " + int_id + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
            {
                MessageObject ms = new MessageObject(rs.getInt("messageId"), rs.getInt("creatorId"), rs.getInt("forumId"), rs.getString("lastModDate"), rs.getString("creationDate"), rs.getString("content"));
                return Response.status(Response.Status.OK).entity(ms).build();
            } 
            else 
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Mensaje no encontrado").build();
            }
        }
        catch (NumberFormatException e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("No se pudieron convertir los índices a números").build();
		} 
        catch (SQLException e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
        }
    }
	
	@DELETE
	@Path("{messageId}")
	
	public Response deleteMessage(@PathParam("messageId") String message_id) 
	{
		int int_message_id = Integer.parseInt(message_id);
		try {
			Connection conn = Connect.getInstance().getConnection();
			String sql = "DELETE FROM Soscorro.Messages WHERE messageID=" + int_message_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 1)
				return Response.status(Response.Status.NO_CONTENT).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();		
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo eliminar el mensaje\n" + e.getStackTrace()).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{messageId}")
	
	public Response updateMessage(@PathParam("messageId") String messageId, MessageObject newMs) 
	{
		try {
			Connection conn = Connect.getInstance().getConnection();
			int int_id = Integer.parseInt(messageId);
			String sql = "SELECT * FROM Soscorro.Messages where messageId = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) 
			{
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
			MessageObject oldMessage = new MessageObject(rs.getInt("messageId"), rs.getInt("creatorId"), rs.getInt("forumId"), rs.getString("lastModDate"), rs.getString("creationDate"), rs.getString("content"));
			newMs.setMessageId(oldMessage.getMessageId());
			sql = "UPDATE Soscorro.Messages SET `content`='"+ newMs.getMessageContent()+ "' WHERE `messageId`='"+ newMs.getMessageId()+"';";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			String location = uriInfo.getAbsolutePath().toString();
			return Response.status(Response.Status.OK).entity(newMs).header("Content-Location", location).build();			
		} 
		catch (SQLException e) 
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo actualizar el mensaje\n" + e.getStackTrace()).build();
		}
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	
	public Response createMessage(MessageObject ms) 
	{
		try {
			Connection conn = Connect.getInstance().getConnection();
			String sql = "INSERT INTO Soscorro.Messages (creatorId,forumId,lastModDate,creationDate,content) VALUES (" + ms.getCreatorID() + ", " + ms.getForumID() +", '" + ms.getLastModDate() + "', '" + ms.getCreationDate() + "', '" + ms.getMessageContent() + "');";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			
			// Obtener el ID del elemento recién creado. 
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) 
			{
				String uriStr = "/";
				if(uriInfo.getAbsolutePath().toString().endsWith("/"))
				{
					uriStr = "";
				}
				
				ms.setMessageId(generatedID.getInt(1));
				
				String location = uriInfo.getAbsolutePath() + uriStr + ms.getMessageId();
				return Response.status(Response.Status.CREATED).entity(ms).header("Location", location).header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD\n" + e.getStackTrace()).build();
		}
	}
}
