package pl.martialdb.app.serialize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.model.Presence;

public class PresenceSerializer extends BaseObjectSerializer {
    private static final long serialVersionUID = -1511071920732820226L;

    protected PresenceSerializer(Class<BaseCollection> t) {
        super(t);
    }

    public void mapObject(Object obj, JsonGenerator jgen) throws IOException {
        Presence p = (Presence) obj;
        jgen.writeStartObject();
        jgen.writeNumberField("karatekaId", p.getKaratekaId());
        jgen.writeStringField("start",      dateFormat.format( p.getStart() ));
        jgen.writeStringField("period",     p.getPeriod().toString());
        jgen.writeStringField("type",       p.getType().toString());
        jgen.writeNumberField("count",      p.getCount());
        jgen.writeEndObject();
    }

    public BaseCollection deserializeInto(BaseCollection newObjs, Map<String, List<Map<String, Object>>> test) {
        // STUB
        return newObjs;
    }
}
