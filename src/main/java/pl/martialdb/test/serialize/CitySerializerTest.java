package pl.martialdb.test.serialize;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.martialdb.app.model.CityCollection;
import pl.martialdb.app.serialize.CommonSerializer;
import pl.martialdb.app.test.Common;

public class CitySerializerTest extends Common {
    @Test
    public final void testAsJSON() {
        CityCollection cc = new CityCollection(db);
        CommonSerializer cs = new CommonSerializer();
        String serialized = cs.asJSON( cc );
        ObjectMapper mapper = new ObjectMapper();
        try {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, Object>>> test = mapper.readValue(serialized, Map.class);
            assertEquals(test.keySet().size(), 2);
            assertEquals(test.get("fields").get(0).get("name"), "id");
            assertEquals(test.get("fields").get(0).get("type"), 4);
            assertEquals(test.get("fields").get(1).get("name"), "name");
            assertEquals(test.get("fields").get(1).get("type"), 12);
            assertEquals(test.get("records").size(), 2);
            assertEquals(test.get("records").get(0).get("name"), "Kołobrzeg");
            assertEquals(test.get("records").get(0).get("id"), 1);
            assertEquals(test.get("records").get(1).get("name"), "Kraków");
            assertEquals(test.get("records").get(1).get("id"), 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
