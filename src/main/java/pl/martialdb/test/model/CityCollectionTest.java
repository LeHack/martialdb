package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.test.Common;

public class CityCollectionTest extends Common {

    @Test
    public final void testGetAll() {
        CityCollection cc = new CityCollection(db);
        assertEquals(2, cc.getSize());
        Iterator<IModel> iter = cc.getIterator();
        City c = (City)iter.next();
        assertEquals("Kołobrzeg", c.getName());
        c = (City)iter.next();
        assertEquals("Kraków", c.getName());
    }

}
