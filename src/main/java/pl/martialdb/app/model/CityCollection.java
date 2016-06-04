package pl.martialdb.app.model;


import java.sql.ResultSet;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.db.MartialDatabase;

public class CityCollection extends BaseCollection {

    // Standard constructor
    public CityCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new CityMetaData()
        );
    }

    // Single object collection
    public CityCollection(City c) {
        super(c);
    }

    @Override
    protected Object initObject(ResultSet row) {
        City c = new City(db);
        c.init(row);
        return c;
    }

    /*
     * Filtering is disabled for Cities, as there's nothing to filter by
     */
    @Override
    protected BaseFilter getDefaultFilter() {
        return new BaseFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        return true;
    }
}
