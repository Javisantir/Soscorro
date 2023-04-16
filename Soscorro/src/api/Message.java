package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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

@Path("/users/{userId}/messages")

public class Message {

	@Context
	private UriInfo uriInfo;

	public Message() 
	{	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@PathParam("userId") String userIdStr,
			@QueryParam("idCreator") @DefaultValue("") String idStr,
			@QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("10") String countStr,
			@QueryParam("startDate") @DefaultValue("1900-01-01") String startDateStr,
			@QueryParam("endDate") @DefaultValue("2100-01-01") String endDateStr)
	{
		try
		{
			Connection conn = Connect.getInstance().getConnection();
			
			int userId = Integer.parseInt(userIdStr);
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			
			String sql = "SELECT * FROM Soscorro.Messages WHERE forumId= " + userId + " AND Soscorro.Messages.creationDate BETWEEN '" + startDateStr + "' AND '" + endDateStr + "' LIMIT "+ count +" OFFSET " + offset + ";";
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
    public Response getUser(@PathParam("messageId") String id) 
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
                return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
            }
        }
        catch (NumberFormatException e) 
        {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
        }
    }
	
	@DELETE
	@Path("{messageId}")
	public Response deleteUser(@PathParam("userId") String id,
			@PathParam("messageId") String message_id) 
	{
		int int_message_id = Integer.parseInt(message_id);
		try {
			Connection conn = Connect.getInstance().getConnection();
			String sql = "DELETE FROM Soscorro.mensajes WHERE messageID=" + int_message_id + ";";
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
}
