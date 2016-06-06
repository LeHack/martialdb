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
package pl.martialdb.app.common;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pl.martialdb.app.exceptions.MethodNotSupportedException;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.CommonSerializer;

public abstract class BaseRest {
    protected static final Logger appLog = Logger.getLogger("appLog");

    @Context
    private HttpServletRequest httpRequest;

    @GET
    public Response getAllObjects() {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            BaseCollection data = getObjectCollection();
            CommonSerializer cs = new CommonSerializer();
            resp = Response.status(Response.Status.OK).entity( cs.asJSON( data ) ).build();
        }
        return resp;
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getObjectById(@PathParam("id") String id) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            String data = null;
            try {
                BaseCollection obj = getObject(Integer.valueOf(id));
                CommonSerializer cs = new CommonSerializer();
                data = cs.asJSON( obj );
            }
            catch (ObjectNotFoundException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "not found").build();
                data = json.toString();
            }
            catch (MethodNotSupportedException e) {
                appLog.debug("Error while trying to fetch record by id: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("method", "not supported").build();
                data = json.toString();
            }
            resp = Response.status(Response.Status.OK).entity( data ).build();
        }
        return resp;
    }

    public abstract BaseCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException;
    public abstract BaseCollection getObjectCollection();
}
