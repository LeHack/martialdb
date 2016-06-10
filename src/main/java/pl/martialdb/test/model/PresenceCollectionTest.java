package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Presence;
import pl.martialdb.app.model.PresenceCollection;
import pl.martialdb.app.model.Presence.PresencePeriod;
import pl.martialdb.app.model.Presence.PresenceType;
import pl.martialdb.app.test.Common;

public class PresenceCollectionTest extends Common {

    @Test
    public final void testPresenceFilterByKarateka() {
        PresenceCollection pc = new PresenceCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("karatekaId", 1);
        pc.filter(f);
        assertEquals( 4, pc.getSize() );
        Iterator<BaseModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        assertEquals( 1, p.getKaratekaId());
        assertEquals( PresenceType.BASIC, p.getType());
        assertEquals( PresencePeriod.WEEK, p.getPeriod());
    }

    @Test
    public final void testPresenceFilterByPeriod() {
        PresenceCollection pc = new PresenceCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("period", PresencePeriod.DAY);
        pc.filter(f);
        assertEquals( 7, pc.getSize() );
        Iterator<BaseModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        assertEquals( 1, p.getKaratekaId());
        assertEquals( PresenceType.BASIC, p.getType());
        assertEquals( PresencePeriod.DAY, p.getPeriod());
    }

    @Test
    public final void testPresenceFilterByType() {
        PresenceCollection pc = new PresenceCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("type", PresenceType.EXTRA);
        pc.filter(f);
        assertEquals( 2, pc.getSize() );
        Iterator<BaseModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        assertEquals( 1, p.getKaratekaId());
        assertEquals( 2, p.getCount());
        assertEquals( PresenceType.EXTRA, p.getType());
        assertEquals( PresencePeriod.DAY, p.getPeriod());
    }

    @Test
    public final void testPresenceFilterMixed() {
        PresenceCollection pc = new PresenceCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("karatekaId", 1);
        f.set("period", PresencePeriod.DAY);
        f.set("type", PresenceType.EXTRA);
        pc.filter(f);
        assertEquals( 1, pc.getSize() );
        Iterator<BaseModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        assertEquals( 1, p.getKaratekaId());
        assertEquals( 2, p.getCount());
        assertEquals( PresenceType.EXTRA, p.getType());
        assertEquals( PresencePeriod.DAY, p.getPeriod());
        assertEquals( "Sun Jun 05 00:00:00 CEST 2016", p.getStart().toString());
    }

    @Test
    public final void testSaveAndDelete() throws ObjectNotFoundException {
        PresenceCollection pc = new PresenceCollection(
            Arrays.asList(
                new Presence(db).set("karatekaId", 1).set("start", asDate("2016-05-01")).set("count", 20),
                new Presence(db).set("karatekaId", 2).set("start", asDate("2016-06-01")).set("period", PresencePeriod.MONTH).set("count", 3),
                new Presence(db).set("karatekaId", 3).set("count", 300)
            )
        );
        pc.save();

        Iterator<BaseModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        int pId = p.getId();
        assertEquals( 1,  p.getKaratekaId() );
        assertEquals( 20, p.getCount() );
        assertEquals( 2, ((Presence)iter.next()).getKaratekaId() );
        assertEquals( 3, ((Presence)iter.next()).getKaratekaId() );
        p.set("karatekaId", 3).set("count", 10).save();

        Presence p2 = new Presence(pId, db);
        assertEquals( 3,    p2.getKaratekaId() );
        assertEquals( 10,   p2.getCount() );
        assertEquals( asDate("2016-05-01"), p2.getStart() );

        pc.delete();

        Throwable except = checkIfDeleted( db -> new Presence(pId, db) );
        assertEquals(ObjectNotFoundException.class,      except.getClass());
        assertEquals("No Presence found for id: " + pId, except.getMessage());
    }
}
