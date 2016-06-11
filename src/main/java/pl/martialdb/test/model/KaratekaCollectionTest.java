package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.model.KaratekaFilter;
import pl.martialdb.app.model.Rank;
import pl.martialdb.app.model.Rank.RankType;
import pl.martialdb.app.test.Common;

public class KaratekaCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        KaratekaCollection kc = new KaratekaCollection(db);
        kc.filter();
        assertEquals( 3, kc.getSize() );
        Iterator<BaseModel> iter = kc.getIterator();
        assertEquals( "Jan Kowalski",  ((Karateka)iter.next()).getFullName() );
        assertEquals( "Marian Nowak",  ((Karateka)iter.next()).getFullName() );
        assertEquals( "Zdzisław Nowy", ((Karateka)iter.next()).getFullName() );

        // first use defaults
        kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        kc.filter(f);
        assertEquals( 3, kc.getSize() );
        iter = kc.getIterator();
        assertEquals( "Jan Kowalski",  ((Karateka)iter.next()).getFullName() );
        assertEquals( "Marian Nowak",  ((Karateka)iter.next()).getFullName() );
        assertEquals( "Zdzisław Nowy", ((Karateka)iter.next()).getFullName() );

        kc = new KaratekaCollection(db);
        f.set("status", null);
        kc.filter(f);
        assertEquals( 4, kc.getSize() );
        iter = kc.getIterator();
        assertEquals( "Jan Kowalski",       ((Karateka)iter.next()).getFullName() );
        assertEquals( "Marian Nowak",       ((Karateka)iter.next()).getFullName() );
        assertEquals( "Walery Nietutejszy", ((Karateka)iter.next()).getFullName() );
        assertEquals( "Zdzisław Nowy",      ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testFilterByStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("status", false);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        Iterator<BaseModel> iter = kc.getIterator();
        assertEquals( "Walery Nietutejszy", ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testFilterByKarateka() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 1);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        Iterator<BaseModel> iter = kc.getIterator();
        assertEquals( "Zdzisław Nowy", ((Karateka)iter.next()).getFullName() );

        kc = new KaratekaCollection(db);
        f.set("groupId", 2);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        iter = kc.getIterator();
        assertEquals( "Marian Nowak", ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testFilterByKaratekaAndStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 2);
        f.set("status", null);
        kc.filter(f);
        assertEquals( 2, kc.getSize() );
        Iterator<BaseModel> iter = kc.getIterator();
        assertEquals( "Marian Nowak",       ((Karateka)iter.next()).getFullName() );
        assertEquals( "Walery Nietutejszy", ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        KaratekaCollection kc = new KaratekaCollection(
            Arrays.asList(
                new Karateka(db)
                    .set("groupId", 1)
                    .set("name", "Alen")
                    .set("surname", "Woody")
                    .set("telephone","123")
                    .set("email","woody@alen.com")
                    .set("address", "Archers street 15")
                    .set("city", "New York")
                    .set("signupDate", asDate("1997-11-12"))
                    .set("birthdate", asDate("1974-05-12"))
                    .set("status", true)
                    .set("rank", new Rank(RankType.DAN, 3)),
                new Karateka(db)
                    .set("groupId", 2)
                    .set("name", "Waldo")
                    .set("surname", "Hidden")
                    .set("telephone","456")
                    .set("email","waldo@hidden.com")
                    .set("address", "Gunmen street 87")
                    .set("city", "Los Angeles")
                    .set("signupDate", asDate("2004-05-08"))
                    .set("birthdate", asDate("1994-05-20"))
                    .set("status", false)
                    .set("rank", new Rank(RankType.KYU, 2))
            )
        );
        kc.save();

        Iterator<BaseModel> iter = kc.getIterator();
        Karateka k = (Karateka)iter.next();
        int kId = k.getId();
        
        assertEquals( "Alen Woody", k.getFullName() );
        assertEquals( "New York",   k.getCity() );
        assertEquals( "Waldo Hidden", ((Karateka)iter.next()).getFullName() );

        Karateka k2 = new Karateka(kId, db);
        assertEquals( "Alen Woody", k2.getFullName() );
        assertEquals( "New York",   k2.getCity() );
        k2.set("name", "Alaner").set("city", "Nowy Jork").save();

        Karateka k3 = new Karateka(kId, db);
        assertEquals( "Alaner Woody", k3.getFullName() );
        assertEquals( "Nowy Jork",    k3.getCity() );

        kc.delete();

        Throwable except = checkIfDeleted( db -> new Karateka(kId, db) );
        assertEquals(ObjectNotFoundException.class,       except.getClass());
        assertEquals("No Karateka found for id: " + kId, except.getMessage());
    }
}
