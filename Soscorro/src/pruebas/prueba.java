package pruebas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import datos.Usuario;

@Path("/prueba")
public class prueba {	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response saludoXML()
	{
		Usuario user = new Usuario();
		user.setName("Andres");
		user.setId(0);
		return Response.status(Response.Status.OK).entity(user).build();
	}
}
