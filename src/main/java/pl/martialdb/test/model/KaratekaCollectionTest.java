package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.model.KaratekaFilter;
import pl.martialdb.app.test.Common;

@SuppressWarnings("unchecked")
public class KaratekaCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        KaratekaCollection kc = new KaratekaCollection(db);
        kc.filter();
        assertEquals( 3, kc.getSize() );
        Iterator<Karateka> iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        // first use defaults
        kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        kc.filter(f);
        assertEquals( 3, kc.getSize() );
        iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Jan Kowalski");
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        kc = new KaratekaCollection(db);
        f.set("status", null);
        kc.filter(f);
        assertEquals( 4, kc.getSize() );
        iter = (Iterator<Karateka>) kc.getIterator();
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
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        Iterator<Karateka> iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
    }

    @Test
    public final void testFilterByGroup() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 1);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        Iterator<Karateka> iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Zdzisław Nowy");

        kc = new KaratekaCollection(db);
        f.set("groupId", 2);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Marian Nowak");
    }

    @Test
    public final void testFilterByGroupAndStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 2);
        f.set("status", null);
        kc.filter(f);
        assertEquals( 2, kc.getSize() );
        Iterator<Karateka> iter = (Iterator<Karateka>) kc.getIterator();
        assertEquals( iter.next().getFullName(), "Marian Nowak");
        assertEquals( iter.next().getFullName(), "Walery Nietutejszy");
    }
}
