package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedHashMap;

import pl.martialdb.app.common.BaseMetaData;

public class KaratekaMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public KaratekaMetaData() {
        super(
            "karateka",
            Arrays.asList(
                "id", "group_id", "name", "surname", "email", "telephone", "address", "city",
                "signup", "birthdate", "status", "rank_type", "rank_level"
            ),
            new LinkedHashMap<String, Integer>(){{
                put("id",           Types.INTEGER);
                put("groupId",      Types.INTEGER);
                put("name",         Types.VARCHAR);
                put("surname",      Types.VARCHAR);
                put("email",        Types.VARCHAR);
                put("telephone",    Types.VARCHAR);
                put("address",      Types.VARCHAR);
                put("city",         Types.VARCHAR);
                put("signupDate",   Types.DATE   );
                put("birthdate",    Types.DATE   );
                put("status",       Types.BOOLEAN);
                put("rank",         Types.STRUCT );
            }}
        );
    }
}
