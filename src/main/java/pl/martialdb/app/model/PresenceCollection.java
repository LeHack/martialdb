package pl.martialdb.app.model;


import java.sql.ResultSet;
import java.util.List;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;

public class PresenceCollection extends BaseCollection {

    // Standard constructor
    public PresenceCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new PresenceMetaData()
        );
    }

    // Single object collection
    public PresenceCollection(Presence p) {
        super(p);
    }
    // Collection of existing objects
    public PresenceCollection(List<BaseModel> plist) {
        super(plist);
    }

    @Override
    protected Object initObject(ResultSet row) {
        Presence p = new Presence(db);
        p.init(row);
        return p;
    }

    @Override
    protected BaseFilter getDefaultFilter() {
        return new BaseFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        Presence p = (Presence) obj;
        return (
            f.check( "karatekaId", p.getKaratekaId() )
            && f.check( "period", p.getPeriod() )
            && f.check( "type", p.getType() )
        );
    }
}
