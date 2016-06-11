package pl.martialdb.app.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jersey.repackaged.com.google.common.base.Joiner;
import pl.martialdb.app.db.MartialDatabase;

public abstract class BaseModel {
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");
    protected MartialDatabase db;
    protected BaseMetaData meta;
    protected boolean newObject = true;
    public Map<String, Object> data = new HashMap<>();
    protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BaseModel(MartialDatabase... db) {
        this.db = (db.length > 0 ? db[0] : new MartialDatabase());
    }

    /* Initialize object using data from the given resultset */
    public abstract void init(ResultSet data);

    /* Add or update object in the db */
    public BaseModel save() {
        List<Object> params = new ArrayList<>();
        List<String> tokens = new ArrayList<>(), sqlFields = new ArrayList<>();

        String query = "";
        for (String field : meta.getSQLfields()) {
            if ("id".equals(field)) { continue; }
            Object v = getMappedVal( field );
            if (v instanceof Date) {
                v = dateFormat.format(v);
            }
            if (v == null) { continue; }
            sqlFields.add( field );
            params.add( v );
            tokens.add("?");
        }

        if (this.newObject) {
            query = String.format(
                "INSERT INTO %s ('%s') VALUES (%s)",
                meta.getTblName(),
                Joiner.on("','").join( sqlFields ),
                Joiner.on(",").join( tokens )
            );
        }
        else {
            query = String.format(
                "UPDATE %s set '%s' = ? WHERE id = ?",
                meta.getTblName(),
                Joiner.on("' = ?,'").join( sqlFields )
            );
            params.add( get("id") );
        }

        ResultSet rs = db.runUpdate(query, params);
        try {
            if (this.newObject && !rs.isClosed()) {
                rs.next();
                // update the id
                set("id", rs.getInt(1));
                this.newObject = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void delete() {
        if (!this.newObject) {
            String query = String.format(
                "DELETE FROM %s WHERE id = ?", meta.getTblName()
            );
            ResultSet rs = db.runUpdate(query, Arrays.asList( get("id") ));
            try {
                // update newObject state
                this.newObject = !rs.isClosed();
            } catch (SQLException ignored) {}
        }
    }

    protected BaseModel set(String param, Object value) {
        data.put(param, value);
        return this;
    }
    protected Object get(String param) {
        return data.get(param);
    }
    protected Object getMappedVal(String param) {
        return data.get( meta.getMappedField( param ) );
    }
}
