/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestGroup.java
 *
 * DESCRIPTION
 *      Fetch Group objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 31-May-2016 Initial creation.
 * 01-Jun-2016 Added support for extracting single objects by id
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
import pl.martialdb.app.model.Group;
import pl.martialdb.app.model.GroupCollection;

@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class RestGroup extends BaseRest {
    public GroupCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException {
        appLog.trace("Fetching data for Group: " + id);
        return new GroupCollection( new Group(id) );
    }

    public GroupCollection getObjectCollection() {
        appLog.trace("Fetching data for all Groups");
        GroupCollection gc = new GroupCollection();
        gc.filter();
        return gc;
    }
}
