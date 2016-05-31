/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestGroup.java
 *
 * DESCRIPTION
 *      Fetch Group objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 31-May-2016  Initial creation.
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.GroupSerializer;

@Path("/group")
public class RestGroup {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroups() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for all Groups");
            GroupCollection gc = new GroupCollection();
            GroupSerializer gs = new GroupSerializer();
            resp = Response.status(Response.Status.OK).entity( gs.asJSON( gc.filter() ) ).build();
        }
        return resp;
    }
}
