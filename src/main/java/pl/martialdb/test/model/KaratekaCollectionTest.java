package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.model.KaratekaFilter;
import pl.martialdb.app.test.Utils;

public class KaratekaCollectionTest {
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
    public final void testFilter() {
        KaratekaCollection kc = new KaratekaCollection(db);

        Collection<Karateka> karatekas = kc.filter();
        assertEquals( karatekas.size(), 2 );
        Iterator<Karateka> iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");

        // first use defaults
        KaratekaFilter f = new KaratekaFilter(); 
        karatekas = kc.filter(f);
        assertEquals( karatekas.size(), 2 );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");

        f.set("status", null);
        karatekas = kc.filter(f);
        assertEquals( 3, karatekas.size() );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");

        f.set("status", false);
        karatekas = kc.filter(f);
        assertEquals( 1, karatekas.size() );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
    }

}
