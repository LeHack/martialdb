package pl.martialdb.app.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pl.martialdb.app.db.MartialDatabase;

public class KaratekaCollection extends Karateka {
    private final Collection<Karateka> karatekas;

    public KaratekaCollection(MartialDatabase...db) {
        super(db);
        this.karatekas = getAll();
    }

    private Collection<Karateka> getAll() {
        Collection<Karateka> karatekas = new ArrayList<>();
        ResultSet rows = this.db.runQuery(
            "SELECT " + sqlFieldsStr + " from karateka order by id"
        );
        try {
            while (rows.next()) {
                Karateka k = new Karateka(this.db);
                k.init(rows);
                karatekas.add(k);
            }
        } catch (SQLException e) {
            logger.error("Error when creating Karateka collection", e);
        }
        return karatekas;
    }

    public Collection<Karateka> filter(KaratekaFilter...filter) {
        Collection<Karateka> result = new ArrayList<>();
        Iterator<Karateka> iter = this.karatekas.iterator();

        KaratekaFilter f = (filter.length > 0 ? filter[0] : new KaratekaFilter());
        while (iter.hasNext()) {
            Karateka k = iter.next();
            if (!f.check( "status", k.getStatus() )) {
                continue;
            }
            if (!f.check( "groupId", k.getGroupId() )) {
                continue;
            }
            result.add( k );
        }

        return result;
    }
}
