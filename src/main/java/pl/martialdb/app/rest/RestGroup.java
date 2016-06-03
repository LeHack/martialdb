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
 * 31-May-2016 Initial creation.
 * 01-Jun-2016 Added support for extracting single objects by id
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import java.util.Arrays;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pl.martialdb.app.model.Group;
import pl.martialdb.app.model.Group.GroupNotFoundException;
import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.GroupSerializer;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class RestGroup {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
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

    @GET
    @Path("{id:[0-9]+}")
    public Response getGroup(@PathParam("id") String id) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for Group: " + id);
            try {
                Group g = new Group(Integer.valueOf(id));
                Collection<Group> gc = Arrays.asList(g);
                GroupSerializer gs = new GroupSerializer();
                resp = Response.status(Response.Status.OK).entity( gs.asJSON( gc ) ).build();
            }
            catch (GroupNotFoundException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "not found").build();
                resp = Response.status(Response.Status.OK).entity( json.toString() ).build();
            }
        }
        return resp;
    }
}
