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
 * 04-Jun-2016 Moved most of the code to BaseRest
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import java.util.ArrayList;
import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.common.BaseRest;
import pl.martialdb.app.exceptions.MethodNotSupportedException;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.rest.session.webSession;
import pl.martialdb.app.serialize.CommonSerializer;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
public class RestCity extends BaseRest {
    public CityCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException {
        appLog.trace("Fetching data for City: " + id);
        return new CityCollection( new City(id) );
    }

    public CityCollection getObjectCollection() {
        appLog.trace("Fetching data for all Cities");
        CityCollection cc = new CityCollection();
        return cc;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getObjectById(final String input) {
        Response resp = Response.status(Response.Status.FORBIDDEN).entity("Forbidden").build();
        if ( webSession.hasRoles(httpRequest) ){
            String data = Json.createObjectBuilder().add("record", "saved").build().toString();
            try {
                CommonSerializer cs = new CommonSerializer();
                CityCollection cc = new CityCollection(new ArrayList<BaseModel>());
                cs.fromJSON( cc, input );
                cc.save();
            } catch (NumberFormatException | ObjectNotFoundException e) {
                appLog.debug("Error while trying to find record in db: " + e.getMessage());
                JsonObject json = Json.createObjectBuilder().add("record", "save failed: " + e.getMessage()).build();
                data = json.toString();
            }
            resp = Response.status(Response.Status.OK).entity( data ).build();
        }
        return resp;
    }

}
