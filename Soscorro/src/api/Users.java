package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bbdd.Conexion;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.naming.NamingContext;

import bbdd.Conexion;
import datos.Usuario;
import datos.Usuarios;
import datos.Amistad;
import datos.Link;

@Path("/users")
public class Users 
{
	@Context
	private UriInfo uriInfo;

	public Users() 
	{
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@QueryParam("namePattern") @DefaultValue("") String namePattern,
			@QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("10") String countStr)
	{
		try
		{
			Connection conn = Conexion.getInstancia().getConexion();
			
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			
			String sql = "SELECT Usuarios.userId FROM Usuarios WHERE Usuarios.userName LIKE \"%"+ namePattern + "%\" ORDER BY Usuarios.userId ASC LIMIT "+ count +" OFFSET " + offset + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Usuarios users = new Usuarios();
			ArrayList<Link> lista = users.getUsuarios();
			while (rs.next()) 
			{
				lista.add(new Link(uriInfo.getAbsolutePath() + "/" + rs.getInt("userId"),"self"));
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
	public Response getGaraje(@PathParam("userId") String id) 
	{
		try 
		{
			Connection conn = Conexion.getInstancia().getConexion();
			int int_id = Integer.parseInt(id);
			String sql = "SELECT * FROM Usuarios where userId = " + int_id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) 
			{
				Usuario user = new Usuario(rs.getInt("userId"), rs.getString("userName"));
				return Response.status(Response.Status.OK).entity(user).build();
			} 
			else 
			{
				return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();
			}
		}
		catch (NumberFormatException e) 
		{
			return Response.status(Response.Status.BAD_REQUEST).entity("No puedo parsear a entero").build();
		} 
		catch (SQLException e)
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD").build();
		}
	}
	
	@DELETE
	@Path("{userId}")
	public Response deleteUser(@PathParam("userId") String id) {
		try {
			Connection conn = Conexion.getInstancia().getConexion();
			String sql = "DELETE FROM Soscorro.Usuarios WHERE userId=" + id + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 1)
				return Response.status(Response.Status.NO_CONTENT).build();
			else
			return Response.status(Response.Status.NOT_FOUND).entity("Elemento no encontrado").build();		
		} catch (SQLException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo eliminar el usuario\n" + e.getStackTrace()).build();
		}
	}
	
	//TODO
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response addUser(Usuario	user) 
	{
		
		System.out.println(user);
		try {
			Connection conn = Conexion.getInstancia().getConexion();
			String sql = "INSERT INTO Usuarios (userId, userName) VALUES (" + user.getID() + ", \"" + user.getName() + "\");";
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			int affectedRows = ps.executeUpdate();
			
			// Obtener el ID del elemento recién creado. 
			// Necesita haber indicado Statement.RETURN_GENERATED_KEYS al ejecutar un statement.executeUpdate() o al crear un PreparedStatement
			ResultSet generatedID = ps.getGeneratedKeys();
			if (generatedID.next()) 
			{
				user.setId(generatedID.getInt(1));
				String location = uriInfo.getAbsolutePath() + "/" + user.getID();
				return Response.status(Response.Status.CREATED).entity(user).header("Location", location).header("Content-Location", location).build();
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("No se pudo crear el usuario").build();
			
		} 
		catch (SQLException e)
		{
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error de acceso a BBDD\n" + e.getStackTrace()).build();
		}
	}
}

