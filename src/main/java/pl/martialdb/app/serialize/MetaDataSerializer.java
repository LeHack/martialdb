package pl.martialdb.app.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import pl.martialdb.app.common.BaseMetaData;

public class MetaDataSerializer extends StdSerializer<BaseMetaData> {
    private static final long serialVersionUID = -8972250736573074834L;

    public MetaDataSerializer(Class<BaseMetaData> t) {
        super(t);
    }

    @Override
    public void serialize(BaseMetaData meta, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        jgen.writeStartArray();
        for (String field : meta.getFields()) {
            jgen.writeStartObject();
            jgen.writeFieldName("name");
            jgen.writeString( meta.getLabel(field) );
            jgen.writeFieldName("type");
            jgen.writeNumber( meta.getType(field) );
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
