package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventType;
import pl.martialdb.app.test.Common;

public class EventTest extends Common {
    @Test
    public final void testEventIntMartialDatabaseArray() throws ObjectNotFoundException {
        Event c1 = new Event(1, db);
        assertEquals(c1.getName(), "14ty staż w Krakowie");

        Event c2 = new Event(2, db);
        assertEquals(c2.getName(), "40ty obóz sportowy w Zakopanem");

        Throwable eventNotFound = null;
        try {
            new Event(10, db);
        }
        catch (ObjectNotFoundException e) {
            eventNotFound = e;
        }
        assertNotNull(eventNotFound);
        assertEquals(eventNotFound.getClass(), ObjectNotFoundException.class);
        assertEquals(eventNotFound.getMessage(), "No Event found for id: 10");
    }

    @Test
    public final void testGetId() throws ObjectNotFoundException {
        Event e1 = new Event(1, db);
        assertEquals(e1.getId(), 1);

        Event e2 = new Event(2, db);
        assertEquals(e2.getId(), 2);
    }

    @Test
    public final void testGetCityId() throws ObjectNotFoundException {
        Event e1 = new Event(1, db);
        assertEquals(e1.getCityId(), 2);

        Event e2 = new Event(2, db);
        assertEquals(e2.getCityId(), 1);
    }

    @Test
    public final void testGetName() throws ObjectNotFoundException {
        Event e1 = new Event(1, db);
        assertEquals(e1.getName(), "14ty staż w Krakowie");

        Event e3 = new Event(3, db);
        assertEquals(e3.getName(), "Pokaz na konwencie kultury Japońskiej");
    }

    @Test
    public final void testGetDate() throws ObjectNotFoundException {
        Event e1 = new Event(1, db);
        assertEquals(e1.getDate().toString(), "Sat May 14 00:00:00 CEST 2016");

        Event e3 = new Event(3, db);
        assertEquals(e3.getDate().toString(), "Sat Aug 13 00:00:00 CEST 2016");
    }

    @Test
    public final void testGetType() throws ObjectNotFoundException {
        Event e1 = new Event(1, db);
        assertEquals(e1.getType(), EventType.SEMINAR);

        Event e2 = new Event(2, db);
        assertEquals(e2.getType(), EventType.CAMP);

        Event e3 = new Event(3, db);
        assertEquals(e3.getType(), EventType.SHOW);
    }
}
