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

public class Group extends GroupMetaData {
    final MartialDatabase db;
    static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    private Integer id, cityId;
    private String name;

    public Group(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public Group(int id, MartialDatabase...db) throws GroupNotFoundException {
        this(db);
        logger.debug("Creating Group instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from training_group where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new GroupNotFoundException("No Group found for id: " + id);
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

    public class GroupNotFoundException extends Exception {
        private static final long serialVersionUID = 2988745044975245455L;

        public GroupNotFoundException(String message) {
            super(message);
        }
    }
}
