package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bbdd.Connect;
import data.Link;
import data.UserList;
import data.User;

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

@Path("/users")
public class Users 
{
	@Context
	private UriInfo uriInfo;

	public Users() 
	{	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@QueryParam("namePattern") @DefaultValue("") String namePattern,
			@QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("10") String countStr)
	{
		try
		{
			Connection conn = Connect.getInstance().getConnection();
			
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			
			String sql = "SELECT userId FROM Soscorro.Users WHERE Users.userName LIKE \"%"+ namePattern + "%\" ORDER BY Users.userId ASC LIMIT "+ count +" OFFSET " + offset + ";";
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
				lista.add(new Link(uriInfo.getAbsolutePath() + uriStr + rs.getInt("userId"),"self"));
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
	
	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") String id) 
	{
		try 
		{
			Connection conn = Connect.getInstance().getConnection();
			int int_id = Integer.parseInt(id);
			String sql = "SELECT * FROM Soscorro.Users where userId = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) 
			{
				User user = new User(rs.getInt("userId"), rs.getString("userName"));
				return Response.status(Response.Status.OK).entity(user).build();
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
	@Path("{userId}")
	public Response deleteUser(@PathParam("userId") String id) {
		try {
			Connection conn = Connect.getInstance().getConnection();
			int int_id = Integer.parseInt(id);
			String sql = "DELETE FROM Soscorro.USers WHERE userId=" + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 1)
				return Response.status(Response.Status.NO_CONTENT).build();
			else
			return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();		
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo eliminar el usuario\n" + e.getStackTrace()).build();
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addUser(User	user) 
	{
		System.out.println(user);
		try {
			Connection conn = Connect.getInstance().getConnection();
			String sql = "INSERT INTO Soscorro.Users (userName) VALUES (\"" + user.getName() +"\");";
			PreparedStatement ps = conn.prepareStatement(sql);
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
				user.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + uriStr + user.getId();
				return Response.status(Response.Status.CREATED).entity(user).header("Location", location).header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD\n" + e.getStackTrace()).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") String userId, User newUser) 
	{
		try {
			Connection conn = Connect.getInstance().getConnection();
			int int_id = Integer.parseInt(userId);
			String sql = "SELECT * FROM Socorro.Users where userId = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) 
			{
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
			User oldUser = new User(rs.getInt("userId"), rs.getString("userName"));
			newUser.setId(oldUser.getId());
			sql = "UPDATE Soscorro.Usuarios SET `userName`='"+newUser.getName()+"' WHERE `userId`='"+newUser.getId()+"';";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			String location = uriInfo.getAbsolutePath().toString();
			return Response.status(Response.Status.OK).entity(newUser).header("Content-Location", location).build();			
		} 
		catch (SQLException e) 
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo actualizar el usuario\n" + e.getStackTrace()).build();
		}
	}
}

