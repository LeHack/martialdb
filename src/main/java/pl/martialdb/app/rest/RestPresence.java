/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestPresence.java
 *
 * DESCRIPTION
 *      Fetch Presence objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 06-Jun-2016 Initial creation.
 * -------------------------------------------------------------------------------
 */
package pl.martialdb.app.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseRest;
import pl.martialdb.app.exceptions.MethodNotSupportedException;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.PresenceCollection;

@Path("/presence")
@Produces(MediaType.APPLICATION_JSON)
public class RestPresence extends BaseRest {
    /* This method will have a few filtering methods (fetch by X) but non by id */
    public BaseCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException {
        throw new MethodNotSupportedException("Cannot fetch Presence by id");
    }

    public BaseCollection getObjectCollection() {
        appLog.trace("Fetching data for all Presence objects");
        PresenceCollection pc = new PresenceCollection();
        pc.filter();
        return pc;
    }
}
