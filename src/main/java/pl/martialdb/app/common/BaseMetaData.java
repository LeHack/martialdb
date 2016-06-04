package pl.martialdb.app.common;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jersey.repackaged.com.google.common.base.Joiner;

public class BaseMetaData {
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    protected final String tblName, sqlFieldsStr;
    protected final List<String> sqlFields;
    private final Map<String, Integer> meta;

    public BaseMetaData(String tableName, List<String> fields, Map<String, Integer> fieldTypes) {
        this.tblName      = tableName;
        this.sqlFields    = fields;
        this.sqlFieldsStr = Joiner.on(",").join(sqlFields);
        this.meta         = fieldTypes;
    }

    public final Set<String> getFields() {
        return meta.keySet();
    }

    public final String getLabel(String field) {
        return field;
    }

    public final Integer getType(String field) {
        return meta.get(field);
    }

}
