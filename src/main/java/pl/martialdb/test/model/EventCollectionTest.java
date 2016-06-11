package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventCollection;
import pl.martialdb.app.model.EventFilter;
import pl.martialdb.app.model.EventType;
import pl.martialdb.app.test.Common;

public class EventCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        EventCollection ec = new EventCollection(db);

        ec.filter();
        assertEquals( 3, ec.getSize() );
        Iterator<BaseModel> iter = ec.getIterator();
        assertEquals( ((Event)iter.next()).getName(), "14ty staż w Krakowie");
        assertEquals( ((Event)iter.next()).getName(), "40ty obóz sportowy w Zakopanem");
        assertEquals( ((Event)iter.next()).getName(), "Pokaz na konwencie kultury Japońskiej");
    }

    @Test
    public final void testFilterByCity() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<BaseModel> iter = ec.getIterator();
        assertEquals( 2, ((Event)iter.next()).getId());

        ec = new EventCollection(db);
        f.set("cityId", 2);
        ec.filter(f);
        assertEquals( 2, ec.getSize() );
        iter = ec.getIterator();
        assertEquals( 1, ((Event)iter.next()).getId());
        assertEquals( 3, ((Event)iter.next()).getId());
    }

    @Test
    public final void testFilterByType() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("type", EventType.CAMP);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<BaseModel> iter = ec.getIterator();
        assertEquals( 2, ((Event)iter.next()).getId());

        ec = new EventCollection(db);
        f.set("type", EventType.SEMINAR);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        iter = ec.getIterator();
        assertEquals( 1, ((Event)iter.next()).getId());
    }

    @Test
    public final void testFilterByTypeAndCity() {
        EventCollection ec = new EventCollection(db);
        EventFilter f = new EventFilter();
        f.set("cityId", 1);
        f.set("type", EventType.CAMP);
        ec.filter(f);
        assertEquals( 1, ec.getSize() );
        Iterator<BaseModel> iter = ec.getIterator();
        assertEquals( 2, ((Event)iter.next()).getId());

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
        iter = ec.getIterator();
        assertEquals( 3, ((Event)iter.next()).getId());
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        EventCollection ec = new EventCollection(
            Arrays.asList(
                new Event(db).set("name", "Ev1").set("cityId", 1).set("date", asDate("2016-07-15")).set("type", EventType.CAMP),
                new Event(db).set("name", "Ev2").set("cityId", 1).set("date", asDate("2016-08-15")).set("type", EventType.EXAM),
                new Event(db).set("name", "Ev3").set("cityId", 2).set("date", asDate("2016-09-15")).set("type", EventType.SHOW)
            )
        );
        ec.save();

        Iterator<BaseModel> iter = ec.getIterator();
        Event e = (Event)iter.next();
        int eventId = e.getId();
        assertEquals( "Ev1", e.getName() );
        assertEquals( EventType.CAMP, e.getType() );
        assertEquals( "Ev2", ((Event)iter.next()).getName() );
        assertEquals( "Ev3", ((Event)iter.next()).getName() );

        Event e2 = new Event(eventId, db);
        assertEquals( "Ev1", e2.getName() );
        assertEquals( 1, e2.getCityId() );
        assertEquals( asDate("2016-07-15"), e2.getDate() );

        ec.delete();

        Throwable except = checkIfDeleted( db -> new Event(eventId, db) );
        assertEquals(ObjectNotFoundException.class,       except.getClass());
        assertEquals("No Event found for id: " + eventId, except.getMessage());
    }
}
