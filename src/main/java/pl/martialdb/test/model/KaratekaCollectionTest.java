package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.model.KaratekaFilter;
import pl.martialdb.app.test.Common;

public class KaratekaCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        KaratekaCollection kc = new KaratekaCollection(db);

        Collection<Karateka> karatekas = kc.filter();
        assertEquals( 3, karatekas.size() );
        Iterator<Karateka> iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        // first use defaults
        KaratekaFilter f = new KaratekaFilter(); 
        karatekas = kc.filter(f);
        assertEquals( 3, karatekas.size() );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        f.set("status", null);
        karatekas = kc.filter(f);
        assertEquals( 4, karatekas.size() );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");
    }

    @Test
    public final void testFilterByStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);

        KaratekaFilter f = new KaratekaFilter(); 
        f.set("status", false);
        Collection<Karateka> karatekas = kc.filter(f);
        assertEquals( 1, karatekas.size() );
        Iterator<Karateka> iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
    }

    @Test
    public final void testFilterByGroup() {
        KaratekaCollection kc = new KaratekaCollection(db);

        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 1);
        Collection<Karateka> karatekas = kc.filter(f);
        assertEquals( 1, karatekas.size() );
        Iterator<Karateka> iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        f.set("groupId", 2);
        karatekas = kc.filter(f);
        assertEquals( 1, karatekas.size() );
        iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Marian Nowak");
    }

    @Test
    public final void testFilterByGroupAndStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 2);
        f.set("status", null);
        Collection<Karateka> karatekas = kc.filter(f);
        assertEquals( 2, karatekas.size() );
        Iterator<Karateka> iter = karatekas.iterator();
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
    }
}
