package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;

import pl.martialdb.app.common.BaseMetaData;

public class CityMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public CityMetaData() {
        super(
            Arrays.asList("id", "name"),
            new HashMap<String, Integer>(){{
                put("id",       Types.INTEGER);
                put("name",     Types.VARCHAR);
            }}
        );
    }
}
