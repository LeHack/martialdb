package pl.martialdb.app.serialize;

import java.io.IOException;
import java.io.StringWriter;
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
import pl.martialdb.app.model.City;
import pl.martialdb.app.model.CityMetaData;

public class CitySerializer {
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");

    @SuppressWarnings("unchecked")
    public String asJSON(Collection<City> data){
        StringWriter stringWriter = new StringWriter();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new SerializeObject((Class<Collection<City>>) data.getClass()));

        SimpleModule module2 = new SimpleModule();
        module2.addSerializer(new SerializeMetaData(BaseMetaData.class));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        objectMapper.registerModule(module2);

        try {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("fields", new CityMetaData());
            objectNode.putPOJO("records", data);
            objectMapper.writeValue(stringWriter, objectNode);
        } catch (IOException e) {
            appLog.error("Error while serializing City Collection", e);
        }
        return stringWriter.toString();
    }

    public class SerializeObject extends StdSerializer<Collection<City>> {
        private static final long serialVersionUID = -8818412071392943688L;

        protected SerializeObject(Class<Collection<City>> t) {
            super(t);
        }

        @Override
        public void serialize(Collection<City> data, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            Iterator<City> iter = data.iterator();
            jgen.writeStartArray();
            while (iter.hasNext()) {
                City c = iter.next();
                jgen.writeStartObject();
                jgen.writeNumberField("id",     c.getId());
                jgen.writeStringField("name",   c.getName());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }
}
