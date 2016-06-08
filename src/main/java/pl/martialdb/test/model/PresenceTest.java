package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.common.IModel;
import pl.martialdb.app.model.Presence;
import pl.martialdb.app.model.Presence.PresencePeriod;
import pl.martialdb.app.model.Presence.PresenceType;
import pl.martialdb.app.model.PresenceCollection;
import pl.martialdb.app.test.Common;

public class PresenceTest extends Common {
    private static PresenceCollection pc;

    @BeforeClass
    public static void setUpObject() throws Exception {
        pc = new PresenceCollection(db);
    }

    @Test
    public final void testGetKaratekaId() {
        Iterator<IModel> iter = pc.getIterator();
        assertEquals( 1, ((Presence)iter.next()).getKaratekaId() );
        iter.next(); // skip one
        assertEquals( 2, ((Presence)iter.next()).getKaratekaId() );
        assertEquals( 3, ((Presence)iter.next()).getKaratekaId() );
    }

    @Test
    public final void testGetStart() {
        Iterator<IModel> iter = pc.getIterator();
        assertEquals( "Mon May 09 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
        assertEquals( "Sat Jun 04 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
        iter.next(); iter.next(); iter.next(); // skip three
        assertEquals( "Sun Jun 05 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
    }

    @Test
    public final void testGetPeriod() {
        Iterator<IModel> iter = pc.getIterator();
        assertEquals( PresencePeriod.WEEK, ((Presence)iter.next()).getPeriod() );
        assertEquals( PresencePeriod.DAY,  ((Presence)iter.next()).getPeriod() );
    }

    @Test
    public final void testGetCount() {
        Iterator<IModel> iter = pc.getIterator();
        assertEquals( 5, ((Presence)iter.next()).getCount() );
        assertEquals( 1, ((Presence)iter.next()).getCount() );
    }

    @Test
    public final void testGetType() {
        Iterator<IModel> iter = pc.getIterator();
        assertEquals( PresenceType.BASIC, ((Presence)iter.next()).getType() );
        iter.next(); iter.next(); iter.next(); iter.next(); // skip four
        assertEquals( PresenceType.EXTRA, ((Presence)iter.next()).getType() );
    }

}
