package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.model.Presence;
import pl.martialdb.app.model.Presence.PresencePeriod;
import pl.martialdb.app.model.Presence.PresenceType;
import pl.martialdb.app.model.PresenceCollection;
import pl.martialdb.app.test.Common;

@SuppressWarnings("unchecked")
public class PresenceTest extends Common {
    private static PresenceCollection pc;

    @BeforeClass
    public static void setUpObject() throws Exception {
        pc = new PresenceCollection(db);
    }

    @Test
    public final void testGetKaratekaId() {
        Iterator<Presence> iter = (Iterator<Presence>)pc.getIterator();
        assertEquals( 1, iter.next().getKaratekaId() );
        iter.next(); // skip one
        assertEquals( 2, iter.next().getKaratekaId() );
        assertEquals( 3, iter.next().getKaratekaId() );
    }

    @Test
    public final void testGetStart() {
        Iterator<Presence> iter = (Iterator<Presence>)pc.getIterator();
        assertEquals( "Mon May 09 00:00:00 CEST 2016", iter.next().getStart().toString());
        assertEquals( "Sat Jun 04 00:00:00 CEST 2016", iter.next().getStart().toString());
        iter.next(); iter.next(); iter.next(); // skip three
        assertEquals( "Sun Jun 05 00:00:00 CEST 2016", iter.next().getStart().toString());
    }

    @Test
    public final void testGetPeriod() {
        Iterator<Presence> iter = (Iterator<Presence>)pc.getIterator();
        assertEquals( PresencePeriod.WEEK, iter.next().getPeriod() );
        assertEquals( PresencePeriod.DAY, iter.next().getPeriod() );
    }

    @Test
    public final void testGetCount() {
        Iterator<Presence> iter = (Iterator<Presence>)pc.getIterator();
        assertEquals( 5, iter.next().getCount() );
        assertEquals( 1, iter.next().getCount() );
    }

    @Test
    public final void testGetType() {
        Iterator<Presence> iter = (Iterator<Presence>)pc.getIterator();
        assertEquals( PresenceType.BASIC, iter.next().getType() );
        iter.next(); iter.next(); iter.next(); iter.next(); // skip four
        assertEquals( PresenceType.EXTRA, iter.next().getType() );
    }

}
