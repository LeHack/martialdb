package pl.martialdb.app.common;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import pl.martialdb.app.db.MartialDatabase;

public abstract class BaseCollection {
    protected static final Logger appLog = Logger.getLogger("appLog");
    private final Collection<IModel> data = new ArrayList<>();
    protected MartialDatabase db;

    public BaseCollection(IModel singleObject) {
        data.add( singleObject );
    }

    public BaseCollection(List<IModel> objects) {
        data.addAll( objects );
    }

    public BaseCollection(MartialDatabase db, BaseMetaData meta) {
        this.db = db;
        try {
            String query = "SELECT " + meta.sqlFieldsStr + " from " + meta.tblName;
            if (meta.defaultSortField != null)
                query += " order by " + meta.defaultSortField;
            ResultSet rows = db.runQuery(query);
            // if there is no data in the table, we'll just have an empty collection
            if (!rows.isClosed())
                while (rows.next())
                    data.add( (IModel)initObject(rows) );
        } catch (SQLException e) {
            appLog.error("Error when initializing collection", e);
        }
    }

    public void filter(BaseFilter...filter) {
        Collection<IModel> result = new ArrayList<>();
        Iterator<IModel> iter = this.data.iterator();

        BaseFilter f = (filter.length > 0 ? filter[0] : getDefaultFilter());
        while (iter.hasNext()) {
            IModel obj = iter.next();

            if (!filter(f, obj))
                continue;

            result.add( obj );
        }

        this.data.clear();
        this.data.addAll( result );
    }

    public Iterator<IModel> getIterator() {
        return this.data.iterator();
    }

    public int getSize() {
        return this.data.size();
    }

    public void save() {
        Iterator<IModel> iter = getIterator();
        while (iter.hasNext())
            iter.next().save();
    }

    protected abstract Object initObject(ResultSet row);
    protected abstract BaseFilter getDefaultFilter();
    protected abstract boolean filter(BaseFilter f, Object obj);
}
