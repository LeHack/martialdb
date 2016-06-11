/*
 *******************************************************************************
 *                        F I L E   S P E C I F I C A T I O N
 *******************************************************************************
 *
 *  NAME
 *      City.java
 *
 *  DESCRIPTION
 *      Class describing MartialDB Training group cities
 *
 *  MODIFICATION HISTORY
 *  ----------------------------------------------------------------------------
 *  01-Jun-2016  Initial
 *  ----------------------------------------------------------------------------
 */
package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class City extends BaseModel {
    public City(MartialDatabase...db){
        super(db);
        this.meta = new CityMetaData();
    }

    public City(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating City instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName() + " where id = ?", id
        );
        try {
            if (row.isClosed())
                throw new ObjectNotFoundException("No City found for id: " + id);
            row.next();
        } catch (SQLException e) {
            logger.error("Error when constructing city object", e);
        }
        init( row );
    }

    public void init(ResultSet data) {
        try {
            this
                .set("id",   data.getInt("id"))
                .set("name", data.getString("name"));
        } catch (SQLException e) {
            logger.error("Error when initializing city", e);
        }
    }

    public City set(String param, Object value) {
        return (City)super.set(param, value);
    }
    public City save() {
        return (City)super.save();
    }

    public int getId() {
        return (int)this.get("id");
    }

    public String getName() {
        return (String)this.get("name");
    }
}
