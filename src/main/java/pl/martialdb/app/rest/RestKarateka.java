/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestKarateka.java
 *
 * DESCRIPTION
 *      Fetch Karateka objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 29-May-2016 Initial creation.
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

import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.Karateka.NoSuchKaratekaException;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.KaratekaSerializer;

@Path("/karateka")
@Produces(MediaType.APPLICATION_JSON)
public class RestKarateka {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    public Response getKaratekas() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for all Karateka");
            KaratekaCollection kc = new KaratekaCollection();
            KaratekaSerializer ks = new KaratekaSerializer();
            resp = Response.status(Response.Status.OK).entity( ks.asJSON( kc.filter() ) ).build();
        }
        return resp;
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getKarateka(@PathParam("id") String id) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for Karateka: " + id);
            try {
                Karateka k = new Karateka(Integer.valueOf(id));
                Collection<Karateka> kc = Arrays.asList(k);
                KaratekaSerializer ks = new KaratekaSerializer();
                resp = Response.status(Response.Status.OK).entity( ks.asJSON( kc ) ).build();
            }
            catch (NoSuchKaratekaException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "not found").build();
                resp = Response.status(Response.Status.OK).entity( json.toString() ).build();
            }
        }
        return resp;
    }
}
