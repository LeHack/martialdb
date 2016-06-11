package pl.martialdb.app.serialize;


import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseMetaData;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.exceptions.UnhandledTypeException;

public class CommonSerializer {
    private static final org.apache.log4j.Logger appLog = org.apache.log4j.Logger.getLogger("appLog");

    public String asJSON(BaseCollection data) {
        // use SerializerFactory to get instances of the correct object and MetaData serializer
        BaseObjectSerializer bos;
        BaseMetaData bmd;

        StringWriter stringWriter = new StringWriter();
        try {
            bos = SerializerFactory.getSerializerClass(data);
            bmd = SerializerFactory.getMetaDataClass(data);

            SimpleModule module = new SimpleModule();
            module.addSerializer(bos);

            SimpleModule module2 = new SimpleModule();
            module2.addSerializer(new MetaDataSerializer(BaseMetaData.class));

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(module);
            objectMapper.registerModule(module2);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.putPOJO("fields", bmd);
            objectNode.putPOJO("records", data);
            objectMapper.writeValue(stringWriter, objectNode);
        } catch (UnhandledTypeException e) {
            appLog.error("Error while constructing serializer objects: ", e);
        } catch (IOException e) {
            appLog.error("Error while serializing collection", e);
        }

        return stringWriter.toString();
    }

    public void fromJSON(BaseCollection obj, String input) {
        // use SerializerFactory to get instances of the correct object and MetaData serializer
        try {
            SerializerFactory.getSerializerClass(obj).deserialize(input, obj);
        } catch (IOException e) {
            appLog.error("I/O Error while deserializing object: ", e);
        } catch (UnhandledTypeException e) {
            appLog.error("Unkown type while deserializing object: ", e);
        }
    }
}
