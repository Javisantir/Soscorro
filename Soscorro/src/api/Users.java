package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bbdd.Conexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.naming.NamingContext;

import datos.Usuario;
import datos.Usuarios;
import datos.Amistad;
import datos.Link;

@Path("/users")
public class Users 
{
	@Context
	private UriInfo uriInfo;
	private Connection conn;

	public Users() 
	{
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(@QueryParam("namePattern") @DefaultValue("") String namePattern,
			@QueryParam("offset") @DefaultValue("0") String offsetStr,
			@QueryParam("count") @DefaultValue("-1") String countStr)
	{
		try
		{
			conn = Conexion.getInstancia().getConexion();
			
			int offset = Integer.parseInt(offsetStr);
			int count = Integer.parseInt(countStr);
			
			String paginationStr = count==-1 ? "" : "LIMIT "+offset+", "+ count+ ";";
			String sql = "SELECT Usuarios.userId FROM Usuarios WHERE Usuarios.userName LIKE \"%"+ namePattern + "%\" ORDER BY Usuarios.userId" + paginationStr;
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
}

