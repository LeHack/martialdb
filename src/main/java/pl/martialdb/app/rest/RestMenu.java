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
package pl.martialdb.app.rest;

import pl.martialdb.app.rbac.RoleType;
import pl.martialdb.app.rbac.Roles;
import pl.martialdb.app.rest.session.webSession;

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
            
            if ( webSession.hasRole(httpRequest, RoleType.USER) ){
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Karatecy")
                        .add("url", "fighters")
                        .build());
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Grupy")
                        .add("url", "groups")
                        .build());
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Miasta")
                        .add("url", "cities")
                        .build());
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Wydarzenia")
                        .add("url", "events")
                        .build());

                menuItems.add(Json.createObjectBuilder()
                          .add("name", "Szkoła")
                          .add("cssClass", "active")
                          .add("sub", subMenuItems).build()
                );
            }
            if ( webSession.hasRole(httpRequest, RoleType.ADMIN) ){
                subMenuItems.add(Json.createObjectBuilder()
                        .add("name", "Użytkownicy")
                        .add("url", "users")
                        .build());
                menuItems.add(Json.createObjectBuilder()
                          .add("name", "Administracja")
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
