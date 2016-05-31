package pl.martialdb.app.common;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

public class BaseFilter {
    protected final Map<String, Object> predicates = new HashMap<>(); 

    public BaseFilter() {}
    public BaseFilter(Map<String, Object> predicates) {
        this();
        for (String key : predicates.keySet()) {
            this.predicates.put(key, predicates.get(key));
        }
    }
    public void set(String field, Object value) {
        this.predicates.put(field, value);
    }

    public boolean check(String field, Object value) {
        boolean result = true;

        if (this.predicates.containsKey(field)) {
            Object filterVal = this.predicates.get(field);
            result = (filterVal == null || filterVal.equals(value));
        }
        return result;
    }

    public boolean checkRange(String field, Object value) {
        Assert.fail("Not yet implemented");
        return false;
    }
}
