package pl.martialdb.app.common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jersey.repackaged.com.google.common.base.Joiner;

public abstract class BaseMetaData {
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("appLog");

    private final String tblName, sqlFieldsStr, defaultSortField;
    private final List<String> sqlFields;
    private final Map<String, Integer> meta;

    public BaseMetaData(String tableName, List<String> fields, LinkedHashMap<String, Integer> fieldTypes) {
        this.tblName      = tableName;
        this.sqlFields    = fields;
        this.sqlFieldsStr = Joiner.on(",").join(sqlFields);
        this.meta         = fieldTypes;
        this.defaultSortField = fields.get(0);
    }

    public final List<String> getSQLfields() {
        return sqlFields;
    }
    public final Set<String> getFields() {
        return meta.keySet();
    }

    public final String getTblName() {
        return tblName;
    }
    public final String getSQLfieldsStr() {
        return sqlFieldsStr;
    }
    public final String getLabel(String field) {
        return field;
    }

    public final Integer getType(String field) {
        return meta.get(field);
    }

    public String getDefaultSortField() {
        return this.defaultSortField;
    }

    public String getMappedField(String field) {
        String mapped = null;

        int i = sqlFields.indexOf(field);
        Iterator<String> iter = meta.keySet().iterator();
        while (iter.hasNext() && i-- >= 0) {
            mapped = iter.next();
        }

        return mapped;
    }
}
