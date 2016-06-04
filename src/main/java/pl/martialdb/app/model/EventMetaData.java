package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;

import pl.martialdb.app.common.BaseMetaData;

public class EventMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public EventMetaData() {
        super(
            Arrays.asList("id", "city_id", "name", "date", "type"),
            new HashMap<String, Integer>(){{
                put("id",       Types.INTEGER);
                put("cityId",   Types.INTEGER);
                put("name",     Types.VARCHAR);
                put("date",     Types.DATE);
                put("type",     Types.VARCHAR);
            }}
        );
    }
}
