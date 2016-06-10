package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedHashMap;

import pl.martialdb.app.common.BaseMetaData;

public class UserMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public UserMetaData() {
        super(
            "user",
            Arrays.asList(
                "id", "login", "pass", "name", "surname", "email", "role", "default_city_id", "stamp"
            ),
            new LinkedHashMap<String, Integer>(){{
                put("id",            Types.INTEGER);
                put("login",         Types.VARCHAR);
                put("pass",          Types.VARCHAR);
                put("name",          Types.VARCHAR);
                put("surname",       Types.VARCHAR);
                put("email",         Types.VARCHAR);
                put("role",          Types.VARCHAR);
                put("defaultCityId", Types.INTEGER);
                put("stamp",         Types.DATE   );
            }}
        );
    }
}
