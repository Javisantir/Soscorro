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
import data.UserList;

@Path("/users/{userId}/friends")

public class FriendShip 
{
	@Context
	private UriInfo uriInfo;

	public FriendShip() 
	{	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFriends(@PathParam("userId") String idStr, @QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("10") String countStr)
	{
		try
		{
			Connection conn = Connect.getInstance().getConnection();
			
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			int id = Integer.parseInt(idStr);
			
			String sql = "SELECT friendId FROM Users_has_friends WHERE userId=" + id + " ORDER BY friendId ASC LIMIT "+ count +" OFFSET " + offset + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			UserList users = new UserList();
			ArrayList<Link> lista = users.getUsers();
			String uriStr = "/";
			if(uriInfo.getAbsolutePath().toString().endsWith("/"))
			{
				uriStr = "";
			}
			while (rs.next()) 
			{
				lista.add(new Link(uriInfo.getAbsolutePath() + uriStr + rs.getInt("friendId"),"self"));
			}
			return Response.status(Response.Status.OK).entity(users).build(); // No se puede devolver el ArrayList (para generar XML)
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
			@QueryParam("friendId") @DefaultValue("-1") String friend_id) 
	{
		int int_friend_id = Integer.parseInt(friend_id);
		int int_user_id = Integer.parseInt(id);
		if(int_friend_id == -1)
			return Response.status(Response.Status.BAD_REQUEST).entity("No se envio el id del amigo a eliminar").build();
		try {
			Connection conn = Connect.getInstance().getConnection();
			String sql = "DELETE FROM Soscorro.Users_has_friends WHERE userId=" + int_user_id + " and friendId=" + int_friend_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 1)
				return Response.status(Response.Status.NO_CONTENT).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();		
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo eliminar el amigo\n" + e.getStackTrace()).build();
		}
	}
}
