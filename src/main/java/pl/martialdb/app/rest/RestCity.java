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

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.martialdb.app.common.BaseRest;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;

@Path("/city")
@Produces(MediaType.APPLICATION_JSON)
public class RestCity extends BaseRest {
    public CityCollection getObject(int id) throws ObjectNotFoundException {
        appLog.trace("Fetching data for City: " + id);
        return new CityCollection( new City(id) );
    }

    public CityCollection getObjectCollection() {
        appLog.trace("Fetching data for all Cities");
        CityCollection cc = new CityCollection();
        return cc;
    }
}
