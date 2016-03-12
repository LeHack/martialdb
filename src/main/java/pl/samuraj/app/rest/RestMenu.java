/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 * NAME
 *      RestMenu.java
 *
 * DESCRIPTION
 *      RestMenu class
 *
 * MODIFICATION HISTORY
 * -----------------------------------------------------------------------------
 * 08 Mar 2016  Initial creation
 * -----------------------------------------------------------------------------
 */
package pl.samuraj.app.rest;

import pl.samuraj.app.rbac.RoleType;
import pl.samuraj.app.rbac.Roles;
import pl.samuraj.app.rest.session.webSession;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;

@Path("/menu")
public class RestMenu {

    private static final Logger appLog = Logger.getLogger("appLog");

    private static final long serialVersionUID = 1L;

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response menuData() {
        if ( webSession.hasRoles(httpRequest) ){
            JsonArrayBuilder menuItems = Json.createArrayBuilder();
            JsonArrayBuilder subMenuItems = Json.createArrayBuilder();
            
            if ( webSession.hasRole(httpRequest, RoleType.USERS) ){
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Users")
                        .add("url", "users")
                        .build());
                menuItems.add(Json.createObjectBuilder()
                          .add("name", "Users")
                          .add("cssClass", "active")
                          .add("sub", subMenuItems).build()
                );
            }
            if ( webSession.hasRole(httpRequest, RoleType.ADMINS) ){
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Admins")
                        .add("url", "admins")
                        .build());
                menuItems.add(Json.createObjectBuilder()
                          .add("name", "Admins")
                          .add("cssClass", "active")
                          .add("sub", subMenuItems).build()
                );
            }
            JsonObject menu = Json.createObjectBuilder().add("links", menuItems).build();
            return Response.status(Response.Status.OK).entity( menu.toString() ).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
}
