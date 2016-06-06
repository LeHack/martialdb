/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestEvent.java
 *
 * DESCRIPTION
 *      Fetch event objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 04-Jun-2016 Initial creation.
 * 04-Jun-2016 Moved most of the code to BaseRest
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.martialdb.app.common.BaseRest;
import pl.martialdb.app.exceptions.MethodNotSupportedException;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventCollection;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class RestEvent extends BaseRest {
    public EventCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException {
        appLog.trace("Fetching data for Event: " + id);
        return new EventCollection( new Event(id) );
    }

    public EventCollection getObjectCollection() {
        appLog.trace("Fetching data for all Events");
        EventCollection ec = new EventCollection();
        ec.filter();
        return ec;
    }
}
