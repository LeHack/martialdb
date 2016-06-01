package pl.martialdb.app.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import pl.martialdb.app.db.MartialDatabase;

public class CityCollection extends City {
    private final Collection<City> cities;

    public CityCollection(MartialDatabase...db) {
        super(db);
        this.cities = init();
    }

    private Collection<City> init() {
        Collection<City> data = new ArrayList<>();
        ResultSet rows = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from cities order by id"
        );
        try {
            while (rows.next()) {
                City k = new City(this.db);
                k.init(rows);
                data.add(k);
            }
        } catch (SQLException e) {
            logger.error("Error when creating City collection", e);
        }
        return data;
    }

    public Collection<City> getAll() {
        return this.cities;
    }
}
