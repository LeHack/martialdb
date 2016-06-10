package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.BeforeClass;
import org.junit.Test;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
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
        Iterator<BaseModel> iter = pc.getIterator();
        assertEquals( 1, ((Presence)iter.next()).getKaratekaId() );
        iter.next(); // skip one
        assertEquals( 2, ((Presence)iter.next()).getKaratekaId() );
        assertEquals( 3, ((Presence)iter.next()).getKaratekaId() );
    }

    @Test
    public final void testGetStart() {
        Iterator<BaseModel> iter = pc.getIterator();
        assertEquals( "Mon May 09 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
        assertEquals( "Sat Jun 04 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
        iter.next(); iter.next(); iter.next(); // skip three
        assertEquals( "Sun Jun 05 00:00:00 CEST 2016", ((Presence)iter.next()).getStart().toString());
    }

    @Test
    public final void testGetPeriod() {
        Iterator<BaseModel> iter = pc.getIterator();
        assertEquals( PresencePeriod.WEEK, ((Presence)iter.next()).getPeriod() );
        assertEquals( PresencePeriod.DAY,  ((Presence)iter.next()).getPeriod() );
    }

    @Test
    public final void testGetCount() {
        Iterator<BaseModel> iter = pc.getIterator();
        assertEquals( 5, ((Presence)iter.next()).getCount() );
        assertEquals( 1, ((Presence)iter.next()).getCount() );
    }

    @Test
    public final void testGetType() {
        Iterator<BaseModel> iter = pc.getIterator();
        assertEquals( PresenceType.BASIC, ((Presence)iter.next()).getType() );
        iter.next(); iter.next(); iter.next(); iter.next(); // skip four
        assertEquals( PresenceType.EXTRA, ((Presence)iter.next()).getType() );
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        Presence p = new Presence(db)
            .set("karatekaId", 1)
            .set("start", asDate("2016-06-08"))
            .set("period", PresencePeriod.MONTH)
            .set("count", 10)
            .set("type", PresenceType.BASIC)
            .save();
        assertEquals(PresenceType.BASIC, p.getType());
        int pId = p.getId();
        assertEquals(9, pId);

        // load and update
        Presence p2 = new Presence(pId, db);
        assertEquals(PresenceType.BASIC, p2.getType());
        assertEquals(10, p2.getCount());
        assertEquals(PresencePeriod.MONTH, p2.getPeriod());
        p2.set("type", PresenceType.EXTRA);
        p2.set("period", PresencePeriod.WEEK);
        p2.set("count", 12);
        p2.save();

        Presence p3 = new Presence(pId, db);
        assertEquals(PresenceType.EXTRA, p3.getType());
        assertEquals(PresencePeriod.WEEK, p3.getPeriod());

        p2.delete();

        Throwable except = checkIfDeleted( db -> new Presence(pId, db) );
        assertEquals(ObjectNotFoundException.class,     except.getClass());
        assertEquals("No Presence found for id: " + pId,  except.getMessage());
    }
}
