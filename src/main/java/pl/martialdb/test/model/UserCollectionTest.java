package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.model.User;
import pl.martialdb.app.model.UserCollection;
import pl.martialdb.app.test.Common;

public class UserCollectionTest extends Common {
    @Test
    public final void testFindByPassword() {
        UserCollection uc = new UserCollection(db);
        assertNull( uc.findByPassword("", new StringBuffer("")) );
        assertNull( uc.findByPassword("john", new StringBuffer("")) );
        assertNull( uc.findByPassword("john", new StringBuffer("whatever")) );
        
        User u = uc.findByPassword("john", new StringBuffer("admin"));
        assertEquals( u.getId(), 1 );
        assertEquals( u.getName(), "John Wayne" );
        assertEquals( u.getRole(), "ADMIN" );
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testFilterByStatus() {
        UserCollection uc = new UserCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("role", "ADMIN");
        uc.filter(f);
        assertEquals( 2, uc.getSize() );
        Iterator<User> iter = (Iterator<User>) uc.getIterator();
        assertEquals( iter.next().getName(), "John Wayne");
        assertEquals( iter.next().getName(), "Clint Eastwood");

        uc = new UserCollection(db);
        f.set("role", "USER");
        uc.filter(f);
        assertEquals( 1, uc.getSize() );
        iter = (Iterator<User>) uc.getIterator();
        assertEquals( iter.next().getName(), "Gary Cooper");
}
}
