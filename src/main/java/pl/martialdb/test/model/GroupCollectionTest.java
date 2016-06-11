package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Group;
import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.model.GroupFilter;
import pl.martialdb.app.test.Common;

public class GroupCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        GroupCollection gc = new GroupCollection(db);
        gc.filter();
        assertEquals( 3, gc.getSize() );
        Iterator<BaseModel> iter = gc.getIterator();
        assertEquals( "10 - 8 kyu",  ((Group)iter.next()).getName() );
        assertEquals( "7 - 5 kyu",   ((Group)iter.next()).getName() );
        assertEquals( "4 kyu - dan", ((Group)iter.next()).getName() );
    }

    @Test
    public final void testFilterByCity() {
        GroupCollection gc = new GroupCollection(db);
        GroupFilter f = new GroupFilter();
        f.set("cityId", 1);
        gc.filter(f);
        assertEquals( 2, gc.getSize() );
        Iterator<BaseModel> iter = gc.getIterator();
        assertEquals( "10 - 8 kyu", ((Group)iter.next()).getName() );
        assertEquals( "7 - 5 kyu",  ((Group)iter.next()).getName() );

        gc = new GroupCollection(db);
        f.set("cityId", 2);
        gc.filter(f);
        assertEquals( 1, gc.getSize() );
        iter = gc.getIterator();
        assertEquals( "4 kyu - dan", ((Group)iter.next()).getName() );
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        GroupCollection gc = new GroupCollection(
            Arrays.asList(
                new Group(db).set("name", "Gr1").set("cityId", 1),
                new Group(db).set("name", "Gr2").set("cityId", 2),
                new Group(db).set("name", "Gr3").set("cityId", 1)
            )
        );
        gc.save();

        Iterator<BaseModel> iter = gc.getIterator();
        Group g = (Group)iter.next();
        int gId = g.getId();
        assertEquals( "Gr1", g.getName() );
        assertEquals( 1,     g.getCityId() );
        assertEquals( "Gr2", ((Group)iter.next()).getName() );
        assertEquals( "Gr3", ((Group)iter.next()).getName() );

        Group e2 = new Group(gId, db);
        assertEquals( "Gr1", e2.getName() );
        assertEquals( 1,     e2.getCityId() );

        gc.delete();

        Throwable except = checkIfDeleted( db -> new Group(gId, db) );
        assertEquals(ObjectNotFoundException.class,       except.getClass());
        assertEquals("No Group found for id: " + gId, except.getMessage());
    }
}
