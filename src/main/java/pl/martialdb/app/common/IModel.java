package pl.martialdb.app.common;

import java.sql.ResultSet;

public interface IModel {
    /* Initialize object using data from the given resultset */
    public void init(ResultSet data);

    /* Add or update object in the db */
    public void save();
}
