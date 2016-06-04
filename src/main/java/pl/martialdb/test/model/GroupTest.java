package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Group;
import pl.martialdb.app.test.Common;

public class GroupTest extends Common {
    @Test
    public final void testGroupIntMartialDatabaseArray() throws ObjectNotFoundException {
        Group g1 = new Group(1, db);
        assertEquals(g1.getName(), "10 - 8 kyu");

        Group g2 = new Group(2, db);
        assertEquals(g2.getName(), "7 - 5 kyu");

        Group g3 = new Group(3, db);
        assertEquals(g3.getName(), "4 kyu - dan");

        Throwable groupNotFound = null;
        try {
            new Group(10, db);
        }
        catch (ObjectNotFoundException e) {
            groupNotFound = e;
        }
        assertNotNull(groupNotFound);
        assertEquals(groupNotFound.getClass(), ObjectNotFoundException.class);
        assertEquals(groupNotFound.getMessage(), "No Group found for id: 10");
    }

    @Test
    public final void testGetId() throws ObjectNotFoundException {
        Group g1 = new Group(1, db);
        assertEquals(g1.getId(), 1);

        Group g2 = new Group(2, db);
        assertEquals(g2.getId(), 2);
    }

    @Test
    public final void testGetCityId() throws ObjectNotFoundException {
        Group g1 = new Group(1, db);
        assertEquals(g1.getCityId(), 1);

        Group g3 = new Group(3, db);
        assertEquals(g3.getCityId(), 2);
    }

    @Test
    public final void testGetName() throws ObjectNotFoundException {
        Group g1 = new Group(1, db);
        assertEquals(g1.getName(), "10 - 8 kyu");

        Group g2 = new Group(2, db);
        assertEquals(g2.getName(), "7 - 5 kyu");
    }

}
