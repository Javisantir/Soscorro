package pruebas;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import datos.Usuario;
import datos.mensaje;

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
		mensaje ms = new mensaje("Tu madre es una mujer especial");
		return Response.status(Response.Status.OK).entity(ms).build();
	}
}
