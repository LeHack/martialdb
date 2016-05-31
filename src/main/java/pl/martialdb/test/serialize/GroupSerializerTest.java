package pl.martialdb.test.serialize;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.martialdb.app.model.GroupCollection;
import pl.martialdb.app.serialize.GroupSerializer;
import pl.martialdb.app.test.Common;

public class GroupSerializerTest extends Common {
    @Test
    public final void testAsJSON() {
        GroupCollection gc = new GroupCollection(db);
        GroupSerializer gs = new GroupSerializer();

        String serialized = gs.asJSON( gc.filter() );
        ObjectMapper mapper = new ObjectMapper();
        try {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, Object>>> test = mapper.readValue(serialized, Map.class);
            assertEquals(test.keySet().size(), 2);
            assertEquals(test.get("fields").get(0).get("name"), "name");
            assertEquals(test.get("fields").get(0).get("type"), 12);
            assertEquals(test.get("fields").get(2).get("name"), "cityId");
            assertEquals(test.get("fields").get(2).get("type"), 4);
            assertEquals(test.get("records").size(), 3);
            assertEquals(test.get("records").get(0).get("name"), "10 - 8 kyu");
            assertEquals(test.get("records").get(0).get("id"), 1);
            assertEquals(test.get("records").get(2).get("cityId"), 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
