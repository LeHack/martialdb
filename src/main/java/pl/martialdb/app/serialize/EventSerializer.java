package pl.martialdb.app.serialize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.model.Event;

public class EventSerializer extends BaseObjectSerializer {
    private static final long serialVersionUID = -9120009116634594838L;

    protected EventSerializer(Class<BaseCollection> t) {
        super(t);
    }

    public void mapObject(Object obj, JsonGenerator jgen) throws IOException {
        Event e = (Event) obj;
        jgen.writeStartObject();
        jgen.writeNumberField("id",         e.getId());
        jgen.writeNumberField("cityId",     e.getCityId());
        jgen.writeStringField("name",       e.getName());
        jgen.writeStringField("date",       dateFormat.format( e.getDate() ));
        jgen.writeStringField("type",       e.getType().toString());
        jgen.writeEndObject();
    }

    public BaseCollection deserializeInto(BaseCollection newObjs, Map<String, List<Map<String, Object>>> test) {
        // STUB
        return newObjs;
    }
}
