package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Group;
import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.model.GroupFilter;
import pl.martialdb.app.test.Common;

@SuppressWarnings("unchecked")
public class GroupCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        GroupCollection gc = new GroupCollection(db);
        gc.filter();
        assertEquals( 3, gc.getSize() );
        Iterator<Group> iter = (Iterator<Group>)gc.getIterator();
        assertEquals( iter.next().getName(), "10 - 8 kyu");
        assertEquals( iter.next().getName(), "7 - 5 kyu");
        assertEquals( iter.next().getName(), "4 kyu - dan");
    }

    @Test
    public final void testFilterByCity() {
        GroupCollection gc = new GroupCollection(db);
        GroupFilter f = new GroupFilter();
        f.set("cityId", 1);
        gc.filter(f);
        assertEquals( 2, gc.getSize() );
        Iterator<Group> iter = (Iterator<Group>)gc.getIterator();
        assertEquals( iter.next().getName(), "10 - 8 kyu");
        assertEquals( iter.next().getName(), "7 - 5 kyu");

        gc = new GroupCollection(db);
        f.set("cityId", 2);
        gc.filter(f);
        assertEquals( 1, gc.getSize() );
        iter = (Iterator<Group>)gc.getIterator();
        assertEquals( iter.next().getName(), "4 kyu - dan");
    }
}
