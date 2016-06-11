package pl.martialdb.app.serialize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Group;

public class GroupSerializer extends BaseObjectSerializer {
    private static final long serialVersionUID = -1016290553257314523L;

    protected GroupSerializer(Class<BaseCollection> t) {
        super(t);
    }

    public void mapObject(Object obj, JsonGenerator jgen) throws IOException {
        Group g = (Group) obj;
        jgen.writeStartObject();
        jgen.writeNumberField("id",         g.getId());
        jgen.writeNumberField("cityId",     g.getCityId());
        jgen.writeStringField("name",       g.getName());
        jgen.writeEndObject();
    }

    public BaseCollection deserializeInto(BaseCollection newObjs, Map<String, List<Map<String, Object>>> test) throws NumberFormatException, ObjectNotFoundException {
        // STUB
        return newObjs;
    }
}
