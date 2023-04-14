package api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.naming.NamingContext;

import datos.Usuario;
import datos.Usuarios;
import datos.Amistad;
import datos.Link;
import datos.Mensaje;

@Path("/users")
public class Users 
{
	@Context
	private UriInfo uriInfo;

	private DataSource ds;
	private Connection conn;

	public Users() 
	{
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			NamingContext envCtx = (NamingContext) ctx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/GarajesyEmpleados");
			conn = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers()
	{
		Usuarios users = new Usuarios();
		ArrayList<Link> lista = users.getUsuarios();
		lista.add(new Link(uriInfo.getAbsolutePath() + "/" + "id","self"));
		lista.add(new Link(uriInfo.getAbsolutePath() + "/" + "id","self"));
		lista.add(new Link(uriInfo.getAbsolutePath() + "/" + "id","self"));
		
		return Response.status(Response.Status.OK).entity(users).build();
	}
}

