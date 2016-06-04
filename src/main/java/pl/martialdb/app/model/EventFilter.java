package pl.martialdb.app.model;

import java.util.Map;

import pl.martialdb.app.common.BaseFilter;

public class EventFilter extends BaseFilter {
    public EventFilter() {
        super();
    }
    public EventFilter(Map<String, Object> predicates) {
        super(predicates);
    }
}
