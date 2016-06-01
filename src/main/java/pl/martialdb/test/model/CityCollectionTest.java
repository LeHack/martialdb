package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.test.Common;

public class CityCollectionTest extends Common {

    @Test
    public final void testGetAll() {
        CityCollection cc = new CityCollection(db);
        Collection<City> data = cc.getAll();
        assertEquals(2, data.size());
        Iterator<City> iter = data.iterator();
        assertEquals("Kołobrzeg", iter.next().getName());
        assertEquals("Kraków", iter.next().getName());
    }

}
