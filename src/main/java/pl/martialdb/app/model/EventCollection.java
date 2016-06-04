package pl.martialdb.app.model;


import java.sql.ResultSet;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.db.MartialDatabase;

public class EventCollection extends BaseCollection {

    // Standard constructor
    public EventCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new EventMetaData()
        );
    }

    // Single object collection
    public EventCollection(Event e) {
        super(e);
    }

    @Override
    protected Object initObject(ResultSet row) {
        Event e = new Event(db);
        e.init(row);
        return e;
    }

    @Override
    protected BaseFilter getDefaultFilter() {
        return new EventFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        Event e = (Event) obj;
        boolean result = true;

        if (!f.check( "cityId", e.getCityId() )) {
            result = false;
        }
        else if (!f.check( "type", e.getType() )) {
            result = false;
        }

        return result;
    }
}
