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
import java.text.ParseException;
import java.util.Date;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class Event extends BaseModel {
    public Event(MartialDatabase...db){
        super(db);
        this.meta = new EventMetaData();
    }

    public Event(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating Event instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No Event found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing event object", e);
        }
        init( row );
    }

    public void init(ResultSet data) {
        try {
            this
                .set("id",      data.getInt("id"))
                .set("cityId",  data.getInt("city_id"))
                .set("name",    data.getString("name"))
                .set("date",    dateFormat.parse( data.getString("date") ))
                .set("type",    EventType.valueOf( data.getString("type") ));
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing group", e);
        }
    }

    public Event set(String param, Object value) {
        return (Event)super.set(param, value);
    }

    public int getId() {
        return (int)this.get("id");
    }

    public int getCityId() {
        return (int)this.get("cityId");
    }

    public String getName() {
        return (String)this.get("name");
    }

    public Date getDate() {
        return (Date)this.get("date");
    }

    public EventType getType() {
        return (EventType)this.get("type");
    }
}
