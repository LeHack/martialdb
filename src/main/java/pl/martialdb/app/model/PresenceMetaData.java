package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pl.martialdb.app.common.BaseMetaData;

public class PresenceMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public PresenceMetaData() {
        super(
            "presence",
            Arrays.asList("karateka_id", "start", "period", "count", "type"),
            new HashMap<String, Integer>(){{
                put("karatekaId",   Types.INTEGER);
                put("start",        Types.DATE);
                put("period",       Types.VARCHAR);
                put("count",        Types.INTEGER);
                put("type",         Types.VARCHAR);
            }}
        );
    }

    protected String getDefaultSortField(List<String> fields) {
        return "start, type";
    }
}
