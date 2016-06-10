/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      Group.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Training Groups
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  31-May-2016  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class Group extends BaseModel {
    public Group(MartialDatabase...db){
        super(db);
        this.meta = new GroupMetaData();
    }

    public Group(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating Group instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No Group found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing group object", e);
        }
        init( row );
    }

    public void init(ResultSet data) {
        try {
            this
                .set("id",      data.getInt("id"))
                .set("cityId",  data.getInt("city_id"))
                .set("name",    data.getString("name"));
        } catch (SQLException e) {
            logger.error("Error when initializing group", e);
        }
    }

    public Group set(String param, Object value) {
        return (Group)super.set(param, value);
    }
    public Group save() {
        return (Group)super.save();
    }

    public int getId() {
        return (int)get("id");
    }

    public int getCityId() {
        return (int)get("cityId");
    }

    public String getName() {
        return (String)get("name");
    }
}
