package pl.martialdb.app.model;


import java.sql.ResultSet;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseFilter;
import pl.martialdb.app.db.MartialDatabase;

public class GroupCollection extends BaseCollection {

    // Standard constructor
    public GroupCollection(MartialDatabase...db) {
        super(
            (db.length > 0 ? db[0] : new MartialDatabase()),
            new GroupMetaData()
        );
    }

    // Single object collection
    public GroupCollection(Group k) {
        super(k);
    }

    @Override
    protected Object initObject(ResultSet row) {
        Group g = new Group(db);
        g.init(row);
        return g;
    }

    @Override
    protected BaseFilter getDefaultFilter() {
        return new GroupFilter();
    }

    @Override
    protected boolean filter(BaseFilter f, Object obj) {
        Group g = (Group) obj;
        return f.check( "cityId", g.getCityId() );
    }
}
