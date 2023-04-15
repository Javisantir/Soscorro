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

import bbdd.Conexion;
import datos.Link;
import datos.Mensajes;
import datos.Usuarios;

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
			Connection conn = Conexion.getInstancia().getConexion();
			
			int userId = Integer.parseInt(userIdStr);
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			
			String sql = "SELECT * FROM mensajes WHERE forumId= " + userId + " AND fechaCreacion BETWEEN '" + startDateStr + "' AND '" + endDateStr + "' LIMIT "+ count +" OFFSET " + offset + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Mensajes messages = new Mensajes();
			ArrayList<Link> lista = messages.getUsuarios();
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
	
	@DELETE
	public Response deleteUser(@PathParam("userId") String id,
			@QueryParam("messageId") @DefaultValue("-1") String message_id) {
		if(message_id.equals("-1"))
			return Response.status(Response.Status.BAD_REQUEST).entity("No se envio el id del mensaje a eliminar").build();
		try {
			Connection conn = Conexion.getInstancia().getConexion();
			String sql = "DELETE FROM Soscorro.mensajes WHERE messageID=" + message_id + ";";
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
