package pl.martialdb.app.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pl.martialdb.app.db.MartialDatabase;

public class EventCollection extends Event {
    private final Collection<Event> events;

    public EventCollection(MartialDatabase...db) {
        super(db);
        this.events = getAll();
    }

    private Collection<Event> getAll() {
        Collection<Event> evs = new ArrayList<>();
        ResultSet rows = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from events order by id"
        );
        try {
            while (rows.next()) {
                Event e = new Event(this.db);
                e.init(rows);
                evs.add(e);
            }
        } catch (SQLException e) {
            logger.error("Error when creating Event collection", e);
        }
        return evs;
    }

    public Collection<Event> filter(EventFilter...filter) {
        Collection<Event> result = new ArrayList<>();
        Iterator<Event> iter = this.events.iterator();

        EventFilter f = (filter.length > 0 ? filter[0] : new EventFilter());
        while (iter.hasNext()) {
            Event e = iter.next();
            if (!f.check( "cityId", e.getCityId() )) {
                continue;
            }
            if (!f.check( "type", e.getType() )) {
                continue;
            }
            result.add( e );
        }

        return result;
    }
}
