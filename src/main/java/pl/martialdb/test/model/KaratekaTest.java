package pl.martialdb.test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.Rank;
import pl.martialdb.app.model.Rank.RankType;
import pl.martialdb.app.test.Common;

public class KaratekaTest extends Common {
    @Test
    public final void testKaratekaIntMartialDatabaseArray() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getName(), "Jan");

        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getName(), "Marian");

        Karateka k3 = new Karateka(3, db);
        assertEquals(k3.getName(), "Walery");

        Throwable karatekaNotFound = null;
        try {
            new Karateka(20, db);
        }
        catch (ObjectNotFoundException e) {
            karatekaNotFound = e;
        }
        assertNotNull(karatekaNotFound);
        assertEquals(karatekaNotFound.getClass(), ObjectNotFoundException.class);
        assertEquals(karatekaNotFound.getMessage(), "No Karateka found for id: 20");
    }

    @Test
    public final void testGetId() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getId(), 1);

        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getId(), 2);
    }

    @Test
    public final void testGetGroupId() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getGroupId(), 3);

        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getGroupId(), 2);
    }

    @Test
    public final void testGetName() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getName(), "Jan");

        Karateka k3 = new Karateka(3, db);
        assertEquals(k3.getName(), "Walery");
    }

    @Test
    public final void testGetFullName() throws ObjectNotFoundException {
        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getFullName(), "Marian Nowak");

        Karateka k3 = new Karateka(3, db);
        assertEquals(k3.getFullName(), "Walery Nietutejszy");
    }

    @Test
    public final void testGetEmail() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getEmail(), "jan@kowalski.com");

        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getEmail(), "marian@nowak.pl");
    }

    @Test
    public final void testGetTelephone() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getTelephone(), "123456789");

        Karateka k3 = new Karateka(3, db);
        assertNull(k3.getTelephone());
    }

    @Test
    public final void testGetAddress() throws ObjectNotFoundException {
        Karateka k2 = new Karateka(2, db);
        assertEquals(k2.getAddress(), "ul. Testowa");

        Karateka k3 = new Karateka(3, db);
        assertNull(k3.getAddress());
    }

    @Test
    public final void testGetCity() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(k1.getCity(), "Gdzieśtamtowo");

        Karateka k3 = new Karateka(3, db);
        assertNull(k3.getCity());
    }

    @Test
    public final void testGetRank() throws ObjectNotFoundException {
        Rank r1 = new Karateka(1, db).getRank();
        assertEquals(r1.type,  RankType.DAN);
        assertEquals(r1.level, 1);

        Rank r2 = new Karateka(2, db).getRank();
        assertEquals(r2.type,  RankType.KYU);
        assertEquals(r2.level, 5);

        Rank r3 = new Karateka(3, db).getRank();
        assertEquals(r3.type, RankType.KYU);
        assertEquals(r3.level, 2);
    }

    @Test
    public final void testGetStatus() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertTrue(k1.getStatus());

        Karateka k3 = new Karateka(3, db);
        assertFalse(k3.getStatus());
    }

    @Test
    public final void testGetSignupDate() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(dateFormat.format( k1.getSignupDate() ), "2016-05-28");

        Karateka k3 = new Karateka(3, db);
        assertEquals(dateFormat.format( k3.getSignupDate() ), "2005-09-28");
    }

    @Test
    public final void testGetBirthdate() throws ObjectNotFoundException {
        Karateka k1 = new Karateka(1, db);
        assertEquals(dateFormat.format( k1.getBirthdate() ), "2000-01-01");

        Karateka k3 = new Karateka(3, db);
        assertEquals(dateFormat.format( k3.getBirthdate() ), "1991-04-01");
    }

}
