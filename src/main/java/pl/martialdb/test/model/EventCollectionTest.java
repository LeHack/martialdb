package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventCollection;
import pl.martialdb.app.model.EventFilter;
import pl.martialdb.app.model.EventType;
import pl.martialdb.app.test.Common;

public class EventCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        EventCollection ec = new EventCollection(db);

        Collection<Event> events = ec.filter();
        assertEquals( 3, events.size() );
        Iterator<Event> iter = events.iterator();
        assertEquals( iter.next().getName(), "14ty staż w Krakowie");
        assertEquals( iter.next().getName(), "40ty obóz sportowy w Zakopanem");
        assertEquals( iter.next().getName(), "Pokaz na konwencie kultury Japońskiej");
    }

    @Test
    public final void testFilterByCity() {
        EventCollection ec = new EventCollection(db);

        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        Collection<Event> events = ec.filter(f);
        assertEquals( 1, events.size() );
        Iterator<Event> iter = events.iterator();
        assertEquals( iter.next().getId(), 2);

        f.set("cityId", 2);
        events = ec.filter(f);
        assertEquals( 2, events.size() );
        iter = events.iterator();
        assertEquals( iter.next().getId(), 1);
        assertEquals( iter.next().getId(), 3);
    }

    @Test
    public final void testFilterByType() {
        EventCollection ec = new EventCollection(db);

        EventFilter f = new EventFilter();
        f.set("type", EventType.CAMP);
        Collection<Event> events = ec.filter(f);
        assertEquals( 1, events.size() );
        Iterator<Event> iter = events.iterator();
        assertEquals( iter.next().getId(), 2);

        f.set("type", EventType.SEMINAR);
        events = ec.filter(f);
        assertEquals( 1, events.size() );
        iter = events.iterator();
        assertEquals( iter.next().getId(), 1);
    }

    @Test
    public final void testFilterByTypeAndCity() {
        EventCollection ec = new EventCollection(db);

        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        f.set("type", EventType.CAMP);
        Collection<Event> events = ec.filter(f);
        assertEquals( 1, events.size() );
        Iterator<Event> iter = events.iterator();
        assertEquals( iter.next().getId(), 2);

        f = new EventFilter();
        f.set("cityId", 2);
        f.set("type", EventType.CAMP);
        events = ec.filter(f);
        assertEquals( 0, events.size() );

        f = new EventFilter();
        f.set("cityId", 2);
        f.set("type", EventType.SHOW);
        events = ec.filter(f);
        assertEquals( 1, events.size() );
        iter = events.iterator();
        assertEquals( iter.next().getId(), 3);
    }
}
