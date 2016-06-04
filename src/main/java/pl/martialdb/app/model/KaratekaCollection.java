package pl.martialdb.app.model;

import java.sql.ResultSet;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.db.MartialDatabase;

public class KaratekaCollection extends BaseCollection {

    // Standard constructor
    public KaratekaCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new KaratekaMetaData()
        );
    }

    // Single object collection
    public KaratekaCollection(Karateka k) {
        super(k);
    }

    @Override
    protected Object initObject(ResultSet row) {
        Karateka k = new Karateka(db);
        k.init(row);
        return k;
    }

    @Override
    protected BaseFilter getDefaultFilter() {
        return new KaratekaFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        Karateka k = (Karateka) obj;
        boolean result = true;
        if (!f.check( "status", k.getStatus() )) {
            result = false;
        }
        else if (!f.check( "groupId", k.getGroupId() )) {
            result = false;
        }
        return result;
    }
}
