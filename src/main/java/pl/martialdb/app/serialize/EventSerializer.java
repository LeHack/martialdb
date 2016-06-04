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
import pl.martialdb.app.model.Event;
import pl.martialdb.app.model.EventMetaData;

public class EventSerializer {
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");

    @SuppressWarnings("unchecked")
    public String asJSON(Collection<Event> data){
        StringWriter stringWriter = new StringWriter();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new SerializeObject((Class<Collection<Event>>) data.getClass()));

        SimpleModule module2 = new SimpleModule();
        module2.addSerializer(new SerializeMetaData(BaseMetaData.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.registerModule(module2);

        try {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("fields", new EventMetaData());
            objectNode.putPOJO("records", data);
            objectMapper.writeValue(stringWriter, objectNode);
        } catch (IOException e) {
            appLog.error("Error while serializing Event Collection", e);
        }
        return stringWriter.toString();
    }

    public class SerializeObject extends StdSerializer<Collection<Event>> {
        private static final long serialVersionUID = -8818412071392943688L;

        protected SerializeObject(Class<Collection<Event>> t) {
            super(t);
        }

        private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        @Override
        public void serialize(Collection<Event> data, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            Iterator<Event> iter = data.iterator();
            jgen.writeStartArray();
            while (iter.hasNext()) {
                Event e = iter.next();
                jgen.writeStartObject();
                jgen.writeNumberField("id",         e.getId());
                jgen.writeNumberField("cityId",     e.getCityId());
                jgen.writeStringField("name",       e.getName());
                jgen.writeStringField("date",       dateFormat.format( e.getDate() ));
                jgen.writeStringField("type",       e.getType().toString());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }
}
