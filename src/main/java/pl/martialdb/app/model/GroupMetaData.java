package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedHashMap;

import pl.martialdb.app.common.BaseMetaData;

public class GroupMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public GroupMetaData() {
        super(
            "training_group",
            Arrays.asList("id", "city_id", "name"),
            new LinkedHashMap<String, Integer>(){{
                put("id",       Types.INTEGER);
                put("cityId",   Types.INTEGER);
                put("name",     Types.VARCHAR);
            }}
        );
    }
}
