package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.IModel;
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
        Iterator<IModel> iter = gc.getIterator();
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
        Iterator<IModel> iter = gc.getIterator();
        assertEquals( "10 - 8 kyu", ((Group)iter.next()).getName() );
        assertEquals( "7 - 5 kyu",  ((Group)iter.next()).getName() );

        gc = new GroupCollection(db);
        f.set("cityId", 2);
        gc.filter(f);
        assertEquals( 1, gc.getSize() );
        iter = gc.getIterator();
        assertEquals( "4 kyu - dan", ((Group)iter.next()).getName() );
    }
}
