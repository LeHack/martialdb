package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.City;
import pl.martialdb.app.test.Common;

public class CityTest extends Common {
    @Test
    public final void testCityIntMartialDatabaseArray() throws ObjectNotFoundException {
        City c1 = new City(1, db);
        assertEquals(c1.getName(), "Kołobrzeg");

        City c2 = new City(2, db);
        assertEquals(c2.getName(), "Kraków");

        Throwable cityNotFound = null;
        try {
            new City(10, db);
        }
        catch (ObjectNotFoundException e) {
            cityNotFound = e;
        }
        assertNotNull(cityNotFound);
        assertEquals(cityNotFound.getClass(), ObjectNotFoundException.class);
        assertEquals(cityNotFound.getMessage(), "No City found for id: 10");
    }

    @Test
    public final void testGetId() throws ObjectNotFoundException {
        City c1 = new City(1, db);
        assertEquals(c1.getId(), 1);

        City c2 = new City(2, db);
        assertEquals(c2.getId(), 2);
    }

    @Test
    public final void testGetName() throws ObjectNotFoundException {
        City c1 = new City(1, db);
        assertEquals(c1.getName(), "Kołobrzeg");

        City c2 = new City(2, db);
        assertEquals(c2.getName(), "Kraków");
    }
}
