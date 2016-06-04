package pl.martialdb.app.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.model.City;

public class CitySerializer extends BaseObjectSerializer {
    private static final long serialVersionUID = -4728710297894215235L;

    protected CitySerializer(Class<BaseCollection> t) {
        super(t);
    }

    public void mapObject(Object obj, JsonGenerator jgen) throws IOException {
        City c = (City) obj;
        jgen.writeStartObject();
        jgen.writeNumberField("id",   c.getId());
        jgen.writeStringField("name", c.getName());
        jgen.writeEndObject();
    }
}
