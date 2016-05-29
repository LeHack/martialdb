package pl.martialdb.app.model;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jersey.repackaged.com.google.common.base.Joiner;

public class KaratekaMetaData {
    protected final List<String> sqlFields;
    protected final String sqlFieldsStr;
    private final Map<String, Integer> meta;
    
    @SuppressWarnings("serial")
    public KaratekaMetaData() {
        this.sqlFields = Arrays.asList(
            "id", "group_id", "name", "surname", "email", "telephone", "address", "city",
            "rank_type", "rank_level", "signup", "birthdate", "status"
        );
        this.sqlFieldsStr = Joiner.on(",").join(sqlFields);
        this.meta = new HashMap<String, Integer>(){{
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
        }};
    }

    public Set<String> getFields() {
        return meta.keySet();
    }

    public String getLabel(String field) {
        return field;
    }

    public Integer getType(String field) {
        return meta.get(field);
    }
}
