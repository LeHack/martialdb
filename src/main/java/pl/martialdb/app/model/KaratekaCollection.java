package pl.martialdb.app.model;

import java.sql.ResultSet;
import java.util.List;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.common.BaseModel;
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
    // Collection of existing objects
    public KaratekaCollection(List<BaseModel> klist) {
        super(klist);
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
        return (
            f.check( "status", k.getStatus() )
            && f.check( "groupId", k.getGroupId() )
        );
    }
}
