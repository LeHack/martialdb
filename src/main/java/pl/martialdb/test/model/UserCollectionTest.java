package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
