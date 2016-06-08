package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.model.KaratekaFilter;
import pl.martialdb.app.test.Common;

public class KaratekaCollectionTest extends Common {
    @Test
    public final void testFilterDefaults() {
        KaratekaCollection kc = new KaratekaCollection(db);
        kc.filter();
        assertEquals( 3, kc.getSize() );
        Iterator<IModel> iter = kc.getIterator();
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
        Iterator<IModel> iter = kc.getIterator();
        assertEquals( "Walery Nietutejszy", ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testFilterByGroup() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 1);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        Iterator<IModel> iter = kc.getIterator();
        assertEquals( "Zdzisław Nowy", ((Karateka)iter.next()).getFullName() );

        kc = new KaratekaCollection(db);
        f.set("groupId", 2);
        kc.filter(f);
        assertEquals( 1, kc.getSize() );
        iter = kc.getIterator();
        assertEquals( "Marian Nowak", ((Karateka)iter.next()).getFullName() );
    }

    @Test
    public final void testFilterByGroupAndStatus() {
        KaratekaCollection kc = new KaratekaCollection(db);
        KaratekaFilter f = new KaratekaFilter(); 
        f.set("groupId", 2);
        f.set("status", null);
        kc.filter(f);
        assertEquals( 2, kc.getSize() );
        Iterator<IModel> iter = kc.getIterator();
        assertEquals( "Marian Nowak",       ((Karateka)iter.next()).getFullName() );
        assertEquals( "Walery Nietutejszy", ((Karateka)iter.next()).getFullName() );
    }
}
