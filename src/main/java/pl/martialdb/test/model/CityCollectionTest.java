package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.test.Common;

public class CityCollectionTest extends Common {

    @Test
    public final void testGetAll() {
        CityCollection cc = new CityCollection(db);
        assertEquals(2, cc.getSize());
        Iterator<BaseModel> iter = cc.getIterator();
        City c = (City)iter.next();
        assertEquals("Kołobrzeg", c.getName());
        c = (City)iter.next();
        assertEquals("Kraków", c.getName());
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        CityCollection cc = new CityCollection(
            Arrays.asList(
                new City(db).set("name", "Miasto 1"),
                new City(db).set("name", "Miasto 2"),
                new City(db).set("name", "Miasto 3")
            )
        );
        cc.save();

        Iterator<BaseModel> iter = cc.getIterator();
        City c = (City)iter.next();
        assertEquals( "Miasto 1", c.getName() );
        assertEquals( "Miasto 2", ((City)iter.next()).getName() );
        assertEquals( "Miasto 3", ((City)iter.next()).getName() );

        int cityId = c.getId();
        City c2 = new City(cityId, db);
        assertEquals( "Miasto 1", c2.getName() );

        cc.delete();

        Throwable except = checkIfDeleted( db -> new City(3, db) );
        assertEquals(ObjectNotFoundException.class,     except.getClass());
        assertEquals("No City found for id: " + cityId, except.getMessage());
    }
}
