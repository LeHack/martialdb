package pl.martialdb.app.serialize;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.exceptions.ObjectNotFoundException;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.Rank;

public class KaratekaSerializer extends BaseObjectSerializer {
    private static final long serialVersionUID = -7563845593224148521L;

    protected KaratekaSerializer(Class<BaseCollection> t) {
        super(t);
    }

    public void mapObject(Object obj, JsonGenerator jgen) throws IOException {
        Karateka k = (Karateka) obj;
        jgen.writeStartObject();
        jgen.writeNumberField("id",         k.getId());
        jgen.writeNumberField("groupId",    k.getGroupId());
        jgen.writeStringField("name",       k.getFullName());
        jgen.writeStringField("email",      k.getEmail());
        jgen.writeStringField("telephone",  k.getTelephone());
        jgen.writeStringField("address",    k.getAddress());
        jgen.writeStringField("city",       k.getCity());
        jgen.writeFieldName("rank");
        jgen.writeStartObject();
        Rank r = k.getRank();
        jgen.writeStringField("type",       r.type.toString());
        jgen.writeStringField("level",      String.valueOf( r.level ));
        jgen.writeEndObject();
        jgen.writeStringField("signup",     dateFormat.format( k.getSignupDate() ));
        jgen.writeStringField("birthdate",  dateFormat.format( k.getBirthdate() ));
        jgen.writeBooleanField("status",    k.getStatus());
        jgen.writeEndObject();
    }

    public BaseCollection deserializeInto(BaseCollection newObjs, Map<String, List<Map<String, Object>>> test) throws NumberFormatException, ObjectNotFoundException {
        // STUB
        return newObjs;
    }
}
