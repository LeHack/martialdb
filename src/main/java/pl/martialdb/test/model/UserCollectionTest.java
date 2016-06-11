package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.User;
import pl.martialdb.app.model.UserCollection;
import pl.martialdb.app.rbac.RoleType;
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
        assertEquals( u.getRole(), RoleType.ADMIN );
    }

    @Test
    public final void testFilterByStatus() {
        UserCollection uc = new UserCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("role", RoleType.ADMIN);
        uc.filter(f);
        assertEquals( 2, uc.getSize() );
        Iterator<BaseModel> iter = uc.getIterator();
        assertEquals( ((User)iter.next()).getName(), "John Wayne");
        assertEquals( ((User)iter.next()).getName(), "Clint Eastwood");

        uc = new UserCollection(db);
        f.set("role", RoleType.USER);
        uc.filter(f);
        assertEquals( 1, uc.getSize() );
        iter = uc.getIterator();
        assertEquals( ((User)iter.next()).getName(), "Gary Cooper");
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        UserCollection uc = new UserCollection(
            Arrays.asList(
                new User(db)
                    .set("login", "awoody")
                    .set("pass", "Passwordly")
                    .set("name", "Alen")
                    .set("surname", "Woody")
                    .set("email","woody@alen.com")
                    .set("defaultCityId", 1)
                    .set("role", RoleType.ADMIN),
                new User(db)
                    .set("login", "whidden")
                    .set("pass", "Hiddenly")
                    .set("name", "Waldo")
                    .set("surname", "Hidden")
                    .set("email","waldo@hidden.com")
                    .set("defaultCityId", 2)
                    .set("role", RoleType.USER)
            )
        );
        uc.save();

        Iterator<BaseModel> iter = uc.getIterator();
        User u = (User)iter.next();
        int uId = u.getId();
        
        assertEquals( "Alen Woody", u.getName() );
        assertEquals( 1, u.getDefaultCity() );
        assertEquals( RoleType.ADMIN, u.getRole() );
        assertEquals( "Waldo Hidden", ((User)iter.next()).getName() );

        User u2 = new User(uId, db);
        assertEquals( "Alen Woody", u2.getName() );
        assertEquals( 1, u2.getDefaultCity() );
        u2.set("name", "Alaner").set("role", RoleType.USER).save();

        User u3 = new User(uId, db);
        assertEquals( "Alaner Woody", u3.getName() );
        assertEquals( RoleType.USER,  u3.getRole() );

        uc.delete();

        Throwable except = checkIfDeleted( db -> new User(uId, db) );
        assertEquals(ObjectNotFoundException.class,       except.getClass());
        assertEquals("No User found for id: " + uId, except.getMessage());
    }
}
