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
import java.util.Arrays;
import java.util.List;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class City extends CityMetaData implements IModel {
    final MartialDatabase db;

    private boolean newObject = true;
    private Integer id;
    private String name;

    public City(MartialDatabase...db){
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    public City(int id, MartialDatabase...db) throws ObjectNotFoundException {
        this(db);
        this.newObject = false;
        logger.debug("Creating City instance for id: " + id);
        ResultSet row = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from " + tblName + " where id = ?", id
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
            this.id          = data.getInt("id");
            this.name        = data.getString("name");
        } catch (SQLException e) {
            logger.error("Error when initializing city", e);
        }
    }

    // Simple approach first, optimize later
    public void save() {
        List<Object> params;
        String query = "";

        if (this.newObject) {
            query = "INSERT INTO " + tblName + " ('name') VALUES (?)";
            params = Arrays.asList(this.getName());
        }
        else {
            query = "UPDATE " + tblName + " set 'name' = ? WHERE id = ?";
            params = Arrays.asList(this.getName(), this.getId());
        }

        this.db.runQuery(query, params);
        this.newObject = false;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
