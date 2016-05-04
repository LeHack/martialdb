/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 * NAME
 *      RestAppInfo.java
 *
 * DESCRIPTION
 *      RestAppInfo class
 *
 * MODIFICATION HISTORY
 * -----------------------------------------------------------------------------
 * 08-Mar-2016  Initial
 * -----------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;

@Path("/appinfo")
public class RestAppInfo {
    private static final Logger appLog = Logger.getLogger("appLog");
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppInfo( @Context ServletContext context, @Context UriInfo uriInfo) {
        String fileName = context.getRealPath( uriInfo.getBaseUri().getPath().replaceFirst(context.getContextPath(), "") + "appinfo.json" );
        Response response = Response.status( Response.Status.NOT_FOUND ).build();
        try
        { 
            byte [] data = Files.readAllBytes(Paths.get(fileName) );
            response = Response.status( Response.Status.OK ).entity( new String(data) ).build();
        } catch ( IOException e ) {
            appLog.error("Unable to read file: "+fileName, e);
        }

        return response;
    }    
}
