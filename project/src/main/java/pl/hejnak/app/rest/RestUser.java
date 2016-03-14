/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestUser.java
 *
 * DESCRIPTION
 *      RestUser class
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * -------------------------------------------------------------------------------
 */
package pl.hejnak.app.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pl.hejnak.app.rest.session.webSession;

@Path("/user")
public class RestUser {

    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if (webSession.isValid(httpRequest)) {
            JsonObject json = Json.createObjectBuilder()
                .add("username", webSession.getFullUserName(httpRequest))
                .build();
            resp = Response.status(Response.Status.OK).entity(json.toString()).build();
        }
        return resp;
    }
}
