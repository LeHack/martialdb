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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.el.MethodNotFoundException;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.db.MartialDatabase;

public class Presence extends PresenceMetaData implements IModel {
    public enum PresenceType { BASIC, EXTRA };
    public enum PresencePeriod { DAY, WEEK, MONTH };
    final MartialDatabase db;

    private Integer karatekaId, count;
    private Date start;
    private PresenceType type;
    private PresencePeriod period;

    public Presence(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void init(ResultSet data) {
        try {
            this.karatekaId = data.getInt("karateka_id");
            this.start      = dateFormat.parse( data.getString("start") );
            this.period     = PresencePeriod.valueOf( data.getString("period") );
            this.count      = data.getInt("count");
            this.type       = PresenceType.valueOf( data.getString("type") );
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing group", e);
        }
    }

    // Stub
    public void save() {
        throw new MethodNotFoundException("Saving Presence is not yet available");
    }

    public int getKaratekaId() {
        return this.karatekaId;
    }

    public Date getStart() {
        return this.start;
    }

    public PresencePeriod getPeriod() {
        return this.period;
    }

    public int getCount() {
        return this.count;
    }

    public PresenceType getType() {
        return this.type;
    }
}
