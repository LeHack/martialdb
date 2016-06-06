/*
 ********************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 ********************************************************************************
 *
 * NAME
 *      RestKarateka.java
 *
 * DESCRIPTION
 *      Fetch Karateka objects as json
 *
 * MODIFICATION HISTORY
 * -------------------------------------------------------------------------------
 * 29-May-2016 Initial creation.
 * 01-Jun-2016 Added support for extracting single objects by id
 * 04-Jun-2016 Moved most of the code to BaseRest
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
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;

@Path("/karateka")
@Produces(MediaType.APPLICATION_JSON)
public class RestKarateka extends BaseRest {
    public BaseCollection getObject(int id) throws ObjectNotFoundException, MethodNotSupportedException {
        appLog.trace("Fetching data for Karateka: " + id);
        return new KaratekaCollection( new Karateka(id) );
    }

    public BaseCollection getObjectCollection() {
        appLog.trace("Fetching data for all Karateka");
        KaratekaCollection kc = new KaratekaCollection();
        kc.filter();
        return kc;
    }
}
