package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.User;
import pl.martialdb.app.rbac.RoleType;
import pl.martialdb.app.test.Common;

public class UserTest extends Common {
    @Test
    public final void testUserIntMartialDatabaseArray() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getLogin(), "john");

        User u2 = new User(2, db);
        assertEquals(u2.getLogin(), "clint");

        User u3 = new User(3, db);
        assertEquals(u3.getLogin(), "gary");

        Throwable userNotFound = null;
        try {
            new User(20, db);
        }
        catch (ObjectNotFoundException e) {
            userNotFound = e;
        }
        assertNotNull(userNotFound);
        assertEquals(userNotFound.getClass(), ObjectNotFoundException.class);
        assertEquals(userNotFound.getMessage(), "No User found for id: 20");
    }

    @Test
    public final void testGetId() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getId(), 1);
        User u3 = new User(3, db);
        assertEquals(u3.getId(), 3);
    }

    @Test
    public final void testGetName() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getName(), "John Wayne");
        User u3 = new User(3, db);
        assertEquals(u3.getName(), "Gary Cooper");
    }

    @Test
    public final void testGetEmail() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getEmail(), "john@wayne.com");
        User u2 = new User(2, db);
        assertEquals(u2.getEmail(), "clint@eastwood.com");
}

    @Test
    public final void testGetDefaultCity() throws ObjectNotFoundException {
        User u2 = new User(2, db);
        assertEquals(u2.getDefaultCity(), 1);
        User u3 = new User(3, db);
        assertEquals(u3.getDefaultCity(), 2);
    }

    @Test
    public final void testGetRole() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getRole(), RoleType.ADMIN);
        User u3 = new User(3, db);
        assertEquals(u3.getRole(), RoleType.USER);
    }

    @Test
    public final void testGetLogin() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getLogin(), "john");
        User u2 = new User(2, db);
        assertEquals(u2.getLogin(), "clint");
    }

    @Test
    public final void testGetLastLogin() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertEquals(u1.getLastLogin().toString(), "Sat May 28 11:42:21 CEST 2016");
        User u2 = new User(2, db);
        assertEquals(u2.getLastLogin().toString(), "Sat May 28 11:44:28 CEST 2016");
    }

    @Test
    public final void testComparePassword() throws ObjectNotFoundException {
        User u1 = new User(1, db);
        assertFalse(u1.comparePassword(new StringBuffer("whatever")));
        assertTrue(u1.comparePassword(new StringBuffer("admin")));

        User u2 = new User(2, db);
        assertFalse(u2.comparePassword(new StringBuffer("whatever")));
        assertTrue(u2.comparePassword(new StringBuffer("admin")));
    }

    @Test
    public final void testUpdateLoginStamp() throws ObjectNotFoundException {
        User u = new User(1, db);
        long stamp = System.currentTimeMillis();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}

        u.updateLoginStamp();
        Date prevStamp = u.getLastLogin();
        User uNew = new User(1, db);
        Date newStamp = uNew.getLastLogin();
        assertNotEquals(newStamp.toString(), prevStamp.toString());
        assertTrue(newStamp.after(new Date(stamp)) && newStamp.before(new Date(stamp + 5000)));
    }
}
