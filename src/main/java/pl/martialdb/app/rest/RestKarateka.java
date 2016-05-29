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
 * 29-May-2016  Initial creation.
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

import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.KaratekaSerializer;

@Path("/karateka")
public class RestKarateka {
    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
}
