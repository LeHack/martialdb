/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Karateka.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Karatekas
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  28-May-2016  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class Karateka extends BaseModel {
    public Karateka(MartialDatabase...db){
        super(db);
        this.meta = new KaratekaMetaData();
    }

    public Karateka(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating Karateka instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No Karateka found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("SQL Error when constructing karateka object", e);
        }
        init( row );
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void init(ResultSet data) {
        try {
            this
                .set("id",          data.getInt("id"))
                .set("groupId",     data.getInt("group_id"))
                .set("name",        data.getString("name"))
                .set("surname",     data.getString("surname"))
                .set("telephone",   data.getString("telephone"))
                .set("email",       data.getString("email"))
                .set("address",     data.getString("address"))
                .set("city",        data.getString("city"))
                .set("signupDate",  dateFormat.parse( data.getString("signup") ))
                .set("birthdate",   dateFormat.parse( data.getString("birthdate") ))
                // data.getBoolean() doesn't work correctly with SQLite
                .set("status",      "true".equals( data.getString("status") ))
                .set("rank",        new Rank( data.getString("rank_type"), data.getInt("rank_level") ));
        } catch (SQLException | ParseException e) {
            logger.error("Error when initializing karateka", e);
        }
    }

    public Karateka set(String param, Object value) {
        return (Karateka)super.set(param, value);
    }
    public Karateka save() {
        return (Karateka)super.save();
    }
    protected Object getMappedVal(String param) {
        Object out = null;
        switch (param) {
            case "rank_type":
                out = getRank().type.toString();
                break;
            case "rank_level":
                out = getRank().level;
                break;
            default:
                out = super.getMappedVal(param);
                break;
        };
        return out;
    }

    public int getId() {
        return (int)get("id");
    }

    public int getGroupId() {
        return (int)get("groupId");
    }

    public String getName() {
        return (String)get("name");
    }

    public String getFullName() {
        return (String)get("name") + " " + (String)get("surname");
    }

    public String getEmail() {
        return (String)get("email");
    }

    public String getTelephone() {
        return (String)get("telephone");
    }

    public String getAddress() {
        return (String)get("address");
    }

    public String getCity() {
        return (String)get("city");
    }

    public Rank getRank() {
        return (Rank)get("rank");
    }

    public boolean getStatus() {
        return (boolean)get("status");
    }

    public Date getSignupDate() {
        return (Date)get("signupDate");
    }

    public Date getBirthdate() {
        return (Date)get("birthdate");
    }
}
