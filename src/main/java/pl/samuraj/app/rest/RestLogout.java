/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 * NAME
 *      RestLogout.java
 *
 * DESCRIPTION
 *      RestLogout class
 *
 * MODIFICATION HISTORY
 * -----------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * -----------------------------------------------------------------------------
 */

package pl.samuraj.app.rest;


import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

import pl.samuraj.app.rest.session.webSession;

@Path("/logout")
public class RestLogout {

    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response signOut() throws NamingException {
        String msg_txt = "User was not logged in";
        Status status = Response.Status.FORBIDDEN;
        if (webSession.isValid(httpRequest)) {
            webSession.invalidate(httpRequest); 
            status = Response.Status.OK;
            msg_txt = "User logged out";
        }
        appLog.info(msg_txt);
        return Response.status(status).entity(msg_txt).build();
    }
}
