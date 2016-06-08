package pl.martialdb.test.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.common.IModel;
import pl.martialdb.app.model.Presence;
import pl.martialdb.app.model.Presence.PresencePeriod;
import pl.martialdb.app.model.Presence.PresenceType;
import pl.martialdb.app.model.PresenceCollection;
import pl.martialdb.app.test.Common;

public class PresenceCollectionTest extends Common {

    @Test
    public final void testPresenceFilterByKarateka() {
        PresenceCollection pc = new PresenceCollection(db);
        BaseFilter f = new BaseFilter();
        f.set("karatekaId", 1);
        pc.filter(f);
        assertEquals( 4, pc.getSize() );
        Iterator<IModel> iter = pc.getIterator();
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
        Iterator<IModel> iter = pc.getIterator();
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
        Iterator<IModel> iter = pc.getIterator();
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
        Iterator<IModel> iter = pc.getIterator();
        Presence p = (Presence)iter.next();
        assertEquals( 1, p.getKaratekaId());
        assertEquals( 2, p.getCount());
        assertEquals( PresenceType.EXTRA, p.getType());
        assertEquals( PresencePeriod.DAY, p.getPeriod());
        assertEquals( "Sun Jun 05 00:00:00 CEST 2016", p.getStart().toString());
    }
}
