package pl.martialdb.app.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pl.martialdb.app.db.MartialDatabase;

public class GroupCollection extends Group {
    private final Collection<Group> groups;

    public GroupCollection(MartialDatabase...db) {
        super(db);
        this.groups = getAll();
    }

    private Collection<Group> getAll() {
        Collection<Group> groups = new ArrayList<>();
        ResultSet rows = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from training_group order by id"
        );
        try {
            while (rows.next()) {
                Group k = new Group(this.db);
                k.init(rows);
                groups.add(k);
            }
        } catch (SQLException e) {
            logger.error("Error when creating Group collection", e);
        }
        return groups;
    }

    public Collection<Group> filter(GroupFilter...filter) {
        Collection<Group> result = new ArrayList<>();
        Iterator<Group> iter = this.groups.iterator();

        GroupFilter f = (filter.length > 0 ? filter[0] : new GroupFilter());
        while (iter.hasNext()) {
            Group k = iter.next();
            if (!f.check( "cityId", k.getCityId() )) {
                continue;
            }
            result.add( k );
        }

        return result;
    }
}
