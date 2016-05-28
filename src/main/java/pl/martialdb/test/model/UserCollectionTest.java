package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.model.User;
import pl.martialdb.app.model.UserCollection;
import pl.martialdb.app.test.Utils;

public class UserCollectionTest {
    static MartialDatabase db;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Utils.dropTestDB();
        db = Utils.initTestDB();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Utils.dropTestDB();
    }

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
