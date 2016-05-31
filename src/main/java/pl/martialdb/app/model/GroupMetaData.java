package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;

import pl.martialdb.app.common.BaseMetaData;

public class GroupMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public GroupMetaData() {
        super(
            Arrays.asList("id", "city_id", "name"),
            new HashMap<String, Integer>(){{
                put("id",       Types.INTEGER);
                put("cityId",   Types.INTEGER);
                put("name",     Types.VARCHAR);
            }}
        );
    }
}
