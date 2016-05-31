package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Group;
import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.model.GroupFilter;
import pl.martialdb.app.test.Common;

public class GroupCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        GroupCollection gc = new GroupCollection(db);

        Collection<Group> groups = gc.filter();
        assertEquals( 3, groups.size() );
        Iterator<Group> iter = groups.iterator();
        assertEquals( iter.next().getName(), "10 - 8 kyu");
        assertEquals( iter.next().getName(), "7 - 5 kyu");
        assertEquals( iter.next().getName(), "4 kyu - dan");
    }

    @Test
    public final void testFilterByCity() {
        GroupCollection gc = new GroupCollection(db);

        GroupFilter f = new GroupFilter();
        f.set("cityId", 1);
        Collection<Group> groups = gc.filter(f);
        assertEquals( 2, groups.size() );
        Iterator<Group> iter = groups.iterator();
        assertEquals( iter.next().getName(), "10 - 8 kyu");
        assertEquals( iter.next().getName(), "7 - 5 kyu");

        f.set("cityId", 2);
        groups = gc.filter(f);
        assertEquals( 1, groups.size() );
        iter = groups.iterator();
        assertEquals( iter.next().getName(), "4 kyu - dan");
    }

}
