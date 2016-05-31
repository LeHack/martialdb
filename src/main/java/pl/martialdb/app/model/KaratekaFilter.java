package pl.martialdb.app.model;

import java.util.Map;

import pl.martialdb.app.common.BaseFilter;

public class KaratekaFilter extends BaseFilter {
    public KaratekaFilter() {
        super();
        // filter defaults
        predicates.put("status", true);
    }
    public KaratekaFilter(Map<String, Object> predicates) {
        super(predicates);
    }
}
