package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;

import pl.martialdb.app.common.BaseMetaData;

public class KaratekaMetaData extends BaseMetaData {
    @SuppressWarnings("serial")
    public KaratekaMetaData() {
        super(
            Arrays.asList(
                "id", "group_id", "name", "surname", "email", "telephone", "address", "city",
                "rank_type", "rank_level", "signup", "birthdate", "status"
            ),
            new HashMap<String, Integer>(){{
                put("id",           Types.INTEGER);
                put("groupId",      Types.INTEGER);
                put("name",         Types.VARCHAR);
                put("email",        Types.VARCHAR);
                put("telephone",    Types.VARCHAR);
                put("address",      Types.VARCHAR);
                put("city",         Types.VARCHAR);
                put("rank",         Types.STRUCT );
                put("signup",       Types.DATE   );
                put("birthdate",    Types.DATE   );
                put("status",       Types.BOOLEAN);
            }}
        );
    }
}
