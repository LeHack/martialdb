package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.model.User;
import pl.martialdb.app.test.Utils;

public class UserTest {
    static MartialDatabase db;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cleanup();
        db = Utils.initTestDB();
    }

    @AfterClass
    public static void cleanup() {
        Utils.dropTestDB();
    }

    @Test
    public final void testUserIntMartialDatabaseArray() {
        User u1 = new User(1, db);
        assertEquals(u1.getLogin(), "john");

        User u2 = new User(2, db);
        assertEquals(u2.getLogin(), "clint");

        User u3 = new User(3, db);
        assertEquals(u3.getLogin(), "gary");
    }

    @Test
    public final void testGetId() {
        User u1 = new User(1, db);
        assertEquals(u1.getId(), 1);
        User u3 = new User(3, db);
        assertEquals(u3.getId(), 3);
    }

    @Test
    public final void testGetName() {
        User u1 = new User(1, db);
        assertEquals(u1.getName(), "John Wayne");
        User u3 = new User(3, db);
        assertEquals(u3.getName(), "Gary Cooper");
    }

    @Test
    public final void testGetEmail() {
        User u1 = new User(1, db);
        assertEquals(u1.getEmail(), "john@wayne.com");
        User u2 = new User(2, db);
        assertEquals(u2.getEmail(), "clint@eastwood.com");
}

    @Test
    public final void testGetDefaultCity() {
        User u2 = new User(2, db);
        assertEquals(u2.getDefaultCity(), "Los Angeles");
        User u3 = new User(3, db);
        assertEquals(u3.getDefaultCity(), "Hadleyville");
    }

    @Test
    public final void testGetRole() {
        User u1 = new User(1, db);
        assertEquals(u1.getRole(), "ADMIN");
        User u3 = new User(3, db);
        assertEquals(u3.getRole(), "USER");
    }

    @Test
    public final void testGetLogin() {
        User u1 = new User(1, db);
        assertEquals(u1.getLogin(), "john");
        User u2 = new User(2, db);
        assertEquals(u2.getLogin(), "clint");
    }

    @Test
    public final void testGetLastLogin() {
        User u1 = new User(1, db);
        assertEquals(u1.getLastLogin().toString(), "Sat May 28 11:42:21 CEST 2016");
        User u2 = new User(2, db);
        assertEquals(u2.getLastLogin().toString(), "Sat May 28 11:44:28 CEST 2016");
    }

    @Test
    public final void testComparePassword() {
        User u1 = new User(1, db);
        assertFalse(u1.comparePassword(new StringBuffer("whatever")));
        assertTrue(u1.comparePassword(new StringBuffer("admin")));

        User u2 = new User(2, db);
        assertFalse(u2.comparePassword(new StringBuffer("whatever")));
        assertTrue(u2.comparePassword(new StringBuffer("admin")));
    }

}
