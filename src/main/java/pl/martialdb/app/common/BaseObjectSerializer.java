package pl.martialdb.app.common;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class BaseObjectSerializer extends StdSerializer<BaseCollection> {
    private static final long serialVersionUID = -3899394742808676840L;
    protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected BaseObjectSerializer(Class<BaseCollection> t) {
        super(t);
    }

    @Override
    public void serialize(BaseCollection data, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        Iterator<?> iter = data.getIterator();
        jgen.writeStartArray();
        while (iter.hasNext()) {
            mapObject(iter.next(), jgen);
        }
        jgen.writeEndArray();
    }

    public abstract void mapObject(Object c, JsonGenerator jgen) throws IOException;
}
