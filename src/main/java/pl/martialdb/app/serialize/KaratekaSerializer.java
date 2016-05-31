package pl.martialdb.app.serialize;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import pl.martialdb.app.common.BaseMetaData;
import pl.martialdb.app.common.SerializeMetaData;
import pl.martialdb.app.model.Karateka;
import pl.martialdb.app.model.KaratekaMetaData;
import pl.martialdb.app.model.Rank;

public class KaratekaSerializer {
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");

    @SuppressWarnings("unchecked")
    public String asJSON(Collection<Karateka> data){
        StringWriter stringWriter = new StringWriter();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new SerializeObject((Class<Collection<Karateka>>) data.getClass()));

        SimpleModule module2 = new SimpleModule();
        module2.addSerializer(new SerializeMetaData(BaseMetaData.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.registerModule(module2);

        try {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("fields", new KaratekaMetaData());
            objectNode.putPOJO("records", data);
            objectMapper.writeValue(stringWriter, objectNode);
        } catch (IOException e) {
            appLog.error("Error while serializing Karateka Collection", e);
        }
        return stringWriter.toString();
    }

    public class SerializeObject extends StdSerializer<Collection<Karateka>> {
        private static final long serialVersionUID = -8818412071392943688L;

        protected SerializeObject(Class<Collection<Karateka>> t) {
            super(t);
        }

        private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public void serialize(Collection<Karateka> data, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            Iterator<Karateka> iter = data.iterator();
            jgen.writeStartArray();
            while (iter.hasNext()) {
                Karateka k = iter.next();
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
            jgen.writeEndArray();
        }
    }
}
