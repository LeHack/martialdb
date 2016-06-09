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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

public class City extends CityMetaData implements IModel {
    final MartialDatabase db;

    private boolean newObject = true;
    private Map<String, Object> data = new HashMap<>();

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

    public City set(String param, Object value) {
        data.put(param, value);
        return this;
    }

    public Object get(String param) {
        return data.get(param);
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

    // Simple approach first, optimize later
    public void save() {
        List<Object> params;
        String query = "";

        if (this.newObject) {
            query = "INSERT INTO " + tblName + " ('name') VALUES (?)";
            params = Arrays.asList(get("name"));
        }
        else {
            query = "UPDATE " + tblName + " set 'name' = ? WHERE id = ?";
            params = Arrays.asList(get("name"), get("id"));
        }

        ResultSet rs = db.runUpdate(query, params);
        try {
            if (!rs.isClosed()) {
                rs.next();
                // update the id
                set("id", rs.getInt(1));
                this.newObject = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return (int)this.get("id");
    }

    public String getName() {
        return (String)this.get("name");
    }
}
