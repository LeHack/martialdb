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

import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class Group extends GroupMetaData {
    final MartialDatabase db;

    private Integer id, cityId;
    private String name;

    public Group(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public Group(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        logger.debug("Creating Group instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from " + tblName + " where id = ?", id
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
            this.id          = data.getInt("id");
            this.cityId      = data.getInt("city_id"); // TODO: replace with object?
            this.name        = data.getString("name");
        } catch (SQLException e) {
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
}
