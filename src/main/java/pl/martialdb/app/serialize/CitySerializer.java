package pl.martialdb.app.serialize;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
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

    public BaseCollection deserializeInto(BaseCollection newObjs, Map<String, List<Map<String, Object>>> test) throws NumberFormatException, ObjectNotFoundException {
        for (Map<String, Object> rec : test.get("records")) {
            City c;
            if (rec.containsKey("id") && (int)rec.get("id") != 0 ) {
                c = new City( (int)rec.get("id") );
            }
            else {
                c = new City();
            }

            c.set("name", rec.get("name"));
            newObjs.add(c);
        }

        return newObjs;
    }
}
