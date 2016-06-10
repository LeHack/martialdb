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
    private final Collection<BaseModel> data = new ArrayList<>();
    protected MartialDatabase db;

    public BaseCollection(BaseModel singleObject) {
        data.add( singleObject );
    }

    public BaseCollection(List<BaseModel> objects) {
        data.addAll( objects );
    }

    public BaseCollection(MartialDatabase db, BaseMetaData meta) {
        this.db = db;
        try {
            String query = "SELECT " + meta.getSQLfieldsStr() + " from " + meta.getTblName();
            if (meta.getDefaultSortField() != null)
                query += " order by " + meta.getDefaultSortField();
            ResultSet rows = db.runQuery(query);
            // if there is no data in the table, we'll just have an empty collection
            if (!rows.isClosed())
                while (rows.next())
                    data.add( (BaseModel)initObject(rows) );
        } catch (SQLException e) {
            appLog.error("Error when initializing collection", e);
        }
    }

    public void filter(BaseFilter...filter) {
        Collection<BaseModel> result = new ArrayList<>();
        Iterator<BaseModel> iter = this.data.iterator();

        BaseFilter f = (filter.length > 0 ? filter[0] : getDefaultFilter());
        while (iter.hasNext()) {
            BaseModel obj = iter.next();

            if (!filter(f, obj))
                continue;

            result.add( obj );
        }

        this.data.clear();
        this.data.addAll( result );
    }

    public Iterator<BaseModel> getIterator() {
        return this.data.iterator();
    }

    public int getSize() {
        return this.data.size();
    }

    public void save() {
        Iterator<BaseModel> iter = getIterator();
        while (iter.hasNext())
            iter.next().save();
    }

    public void delete() {
        Iterator<BaseModel> iter = getIterator();
        while (iter.hasNext())
            iter.next().delete();
    }

    protected abstract Object initObject(ResultSet row);
    protected abstract BaseFilter getDefaultFilter();
    protected abstract boolean filter(BaseFilter f, Object obj);
}
