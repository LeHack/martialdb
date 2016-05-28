package pl.martialdb.app.model;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

public class KaratekaFilter {
    private final Map<String, Object> predicates = new HashMap<>(); 

    public KaratekaFilter() {
        // filter defaults go here
        predicates.put("status", true);
    }
    public KaratekaFilter(Map<String, Object> predicates) {
        this();
        for (String key : predicates.keySet()) {
            this.predicates.put(key, predicates.get(key));
        }
    }
    public void set(String field, Object value) {
        this.predicates.put(field, value);
    }

    public boolean check(String field, Object value) {
        assertTrue(this.predicates.containsKey(field));
        Object filterVal = this.predicates.get(field);
        return (filterVal == null || filterVal.equals(value));
    }
}
