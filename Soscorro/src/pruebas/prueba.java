package pruebas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import datos.Usuario;
import datos.Amistad;
import datos.Mensaje;

@Path("/prueba")
public class prueba {	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response saludoXML()
	{
		Usuario user = new Usuario();
		user.setName("Andres");
		Usuario user1 = new Usuario();
		user.setName("javi");
		Mensaje ms = new Mensaje("Tu madre es una mujer especial");
		Amistad a = new Amistad(user,user1);
		return Response.status(Response.Status.OK).entity(ms).build();
	}
}
