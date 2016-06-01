/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestCity.java
 *
 * DESCRIPTION
 *      Fetch City objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 01-Jun-2016 Initial creation.
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

import pl.martialdb.app.model.City;
import pl.martialdb.app.model.City.CityNotFoundException;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.CitySerializer;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
public class RestCity {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    public Response getCities() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for all Cities");
            CityCollection cc = new CityCollection();
            CitySerializer cs = new CitySerializer();
            resp = Response.status(Response.Status.OK).entity( cs.asJSON( cc.getAll() ) ).build();
        }
        return resp;
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getCity(@PathParam("id") String id) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            appLog.trace("Fetching data for City: " + id);
            try {
                City c = new City(Integer.valueOf(id));
                Collection<City> cc = Arrays.asList(c);
                CitySerializer cs = new CitySerializer();
                resp = Response.status(Response.Status.OK).entity( cs.asJSON( cc ) ).build();
            }
            catch (CityNotFoundException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "not found").build();
                resp = Response.status(Response.Status.OK).entity( json.toString() ).build();
            }
        }
        return resp;
    }
}
