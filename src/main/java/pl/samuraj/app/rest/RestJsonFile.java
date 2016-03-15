/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 * NAME
 *      RestJsonFile.java
 *
 * DESCRIPTION
 *      RestJsonFile class
 *
 * MODIFICATION HISTORY
 * -----------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * -----------------------------------------------------------------------------
 */
package pl.samuraj.app.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

import pl.samuraj.app.rest.session.webSession;

@Path("/")
public class RestJsonFile {
    private static final Logger appLog = Logger.getLogger("appLog");    
    
    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Path("/{resource}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResourceData(@PathParam("resource") String BaseFileName, @Context ServletContext context, @Context UriInfo uriInfo) {
        String fileName = context.getRealPath( uriInfo.getBaseUri().getPath().replaceFirst(context.getContextPath(), "") + BaseFileName + ".json" );
        Response response = Response.status( Response.Status.NOT_FOUND ).build();

        File file = new File(fileName);
        if ( file.exists() ){
            if ( webSession.hasRoles(httpRequest) ){
                try
                { 
                    byte [] data = Files.readAllBytes(Paths.get(fileName) );
                    response = Response.status( Response.Status.OK ).entity( new String(data) ).build();
                } catch ( IOException e ) {
                    appLog.error("Unable to read file: " + fileName, e);
                }
            } else {
                response = Response.status( Response.Status.FORBIDDEN ).build();
            }
        }

        return response;
    }    
}
