package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventCollection;
import pl.martialdb.app.model.EventFilter;
import pl.martialdb.app.model.EventType;
import pl.martialdb.app.test.Common;

@SuppressWarnings("unchecked")
public class EventCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        EventCollection ec = new EventCollection(db);

        ec.filter();
        assertEquals( 3, ec.getSize() );
        Iterator<Event> iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getName(), "14ty staż w Krakowie");
        assertEquals( iter.next().getName(), "40ty obóz sportowy w Zakopanem");
        assertEquals( iter.next().getName(), "Pokaz na konwencie kultury Japońskiej");
    }

    @Test
    public final void testFilterByCity() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<Event> iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 2);

        ec = new EventCollection(db);
        f.set("cityId", 2);
        ec.filter(f);
        assertEquals( 2, ec.getSize() );
        iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 1);
        assertEquals( iter.next().getId(), 3);
    }

    @Test
    public final void testFilterByType() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("type", EventType.CAMP);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<Event> iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 2);

        ec = new EventCollection(db);
        f.set("type", EventType.SEMINAR);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 1);
    }

    @Test
    public final void testFilterByTypeAndCity() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        f.set("type", EventType.CAMP);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<Event> iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 2);

        ec = new EventCollection(db);
        f  = new EventFilter();
        f.set("cityId", 2);
        f.set("type", EventType.CAMP);
        ec.filter(f);
        assertEquals( 0, ec.getSize() );

        ec = new EventCollection(db);
        f  = new EventFilter();
        f.set("cityId", 2);
        f.set("type", EventType.SHOW);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        iter = (Iterator<Event>) ec.getIterator();
        assertEquals( iter.next().getId(), 3);
    }
}
