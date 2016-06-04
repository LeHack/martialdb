/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestEvent.java
 *
 * DESCRIPTION
 *      Fetch event objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 04-Jun-2016 Initial creation.
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

import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.Event.EventNotFoundException;
import pl.martialdb.app.model.EventCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.EventSerializer;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class RestEvent {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    public Response getEvents() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for all Events");
            EventCollection ec = new EventCollection();
            EventSerializer es = new EventSerializer();
            resp = Response.status(Response.Status.OK).entity( es.asJSON( ec.filter() ) ).build();
        }
        return resp;
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getEvent(@PathParam("id") String id) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for Event: " + id);
            try {
                Event e = new Event(Integer.valueOf(id));
                Collection<Event> ec = Arrays.asList(e);
                EventSerializer es = new EventSerializer();
                resp = Response.status(Response.Status.OK).entity( es.asJSON( ec ) ).build();
            }
            catch (EventNotFoundException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "not found").build();
                resp = Response.status(Response.Status.OK).entity( json.toString() ).build();
            }
        }
        return resp;
    }
}
