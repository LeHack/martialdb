/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Event.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Events
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  04-Jun-2016  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.martialdb.app.db.MartialDatabase;

public class Event extends EventMetaData {
    final MartialDatabase db;
    static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    private Integer id, cityId;
    private Date date;
    private String name;
    private EventType type;

    public Event(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public Event(int id, MartialDatabase...db) throws EventNotFoundException {
        this(db);
        logger.debug("Creating Event instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from events where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new EventNotFoundException("No Event found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing event object", e);
        }
        init( row );
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void init(ResultSet data) {
        try {
            this.id          = data.getInt("id");
            this.cityId      = data.getInt("city_id");
            this.name        = data.getString("name");
            this.date        = dateFormat.parse( data.getString("date") );
            this.type        = EventType.valueOf( data.getString("type") );
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing group", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public int getCityId() {
        return this.cityId;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date;
    }

    public EventType getType() {
        return this.type;
    }

    public class EventNotFoundException extends Exception {
        private static final long serialVersionUID = 5078582624142838847L;

        public EventNotFoundException(String message) {
            super(message);
        }
    }
}
