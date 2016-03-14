/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 * NAME
 *      RestLogin.java
 *
 * DESCRIPTION
 *      RestLogin class
 *
 * MODIFICATION HISTORY
 * -----------------------------------------------------------------------------
 * 08-Mar-2016  Initial creation.
 * -----------------------------------------------------------------------------
 */
package pl.hejnak.app.rest;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pl.hejnak.app.rest.session.webAuth;
import pl.hejnak.app.rest.session.webSession;

@Path("/login")
public class RestLogin {

    private static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticate(@FormParam("username") String username, @FormParam("password") String password) throws NamingException {
        Response resp = Response.status(Response.Status.UNAUTHORIZED).entity("Failed to authenticate").build();

        webAuth auth = new webAuth();
        boolean authenticated = auth.isValidUser(username, new StringBuffer(password));
        appLog.debug("Authentication result for user: " + username + " is: " + authenticated);

        if (authenticated) {
            appLog.debug("RestLogin: fullUserName: " + username);
            
            if (webSession.isValid(httpRequest, username)) {
                resp = Response.status(Response.Status.ACCEPTED).entity("User is currently logged in. Valid active session detected.").build();
                appLog.info("User is currently logged in. Valid active session detected.");
            } else {
                webSession.invalidate(httpRequest);
                webSession.create(httpRequest, username);
                resp = Response.status(Response.Status.ACCEPTED).entity("User: " + username + " authenticated successfully").build();
                webSession.setRoles(httpRequest, auth.getRoles());
                appLog.info("200 OK. User " + username + " authenticated. Has following roles: " + auth.getRoles().getRoles());
            }
        } else {
            appLog.warn("401 UNAUTHORIZED. Authentication for user " + username + " failed");
        }
        return resp;
    }
}
