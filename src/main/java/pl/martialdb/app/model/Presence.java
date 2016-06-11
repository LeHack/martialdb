/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Presence.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Training Presences
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  05-Jun-2016  Initial
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

public class Presence extends BaseModel {
    public enum PresenceType { BASIC, EXTRA };
    public enum PresencePeriod { DAY, WEEK, MONTH };

    public Presence(MartialDatabase...db){
        super(db);
        this.meta = new PresenceMetaData();
    }
    public Presence(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating Presence instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No Presence found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing presence object", e);
        }
        init( row );
    }

    public void init(ResultSet data) {
        try {
            this
                .set("id",          data.getInt("id"))
                .set("karatekaId",  data.getInt("karateka_id"))
                .set("start",       dateFormat.parse( data.getString("start") ))
                .set("period",      PresencePeriod.valueOf( data.getString("period") ))
                .set("count",       data.getInt("count"))
                .set("type",        PresenceType.valueOf( data.getString("type") ));
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing group", e);
        }
    }

    public Presence set(String param, Object value) {
        return (Presence)super.set(param, value);
    }
    public Presence save() {
        return (Presence)super.save();
    }

    public int getId() {
        return (int)get("id");
    }

    public int getKaratekaId() {
        return (int)get("karatekaId");
    }

    public Date getStart() {
        return (Date)get("start");
    }

    public PresencePeriod getPeriod() {
        return (PresencePeriod)get("period");
    }

    public int getCount() {
        return (int)get("count");
    }

    public PresenceType getType() {
        return (PresenceType)get("type");
    }

    public String toString() {
        return getId() + ", " + getKaratekaId() + " [" + getCount() + "]";
    }
}
