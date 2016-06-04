package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.test.Common;

@SuppressWarnings("unchecked")
public class CityCollectionTest extends Common {

    @Test
    public final void testGetAll() {
        CityCollection cc = new CityCollection(db);
        assertEquals(2, cc.getSize());
        Iterator<City> iter = (Iterator<City>) cc.getIterator();
        assertEquals("Kołobrzeg", iter.next().getName());
        assertEquals("Kraków", iter.next().getName());
    }

}
