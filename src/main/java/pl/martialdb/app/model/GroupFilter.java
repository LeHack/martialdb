package pl.martialdb.app.model;

import java.util.Map;

import pl.martialdb.app.common.BaseFilter;

public class GroupFilter extends BaseFilter {
    public GroupFilter() {
        super();
    }
    public GroupFilter(Map<String, Object> predicates) {
        super(predicates);
    }
}
