package pl.martialdb.test.serialize;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.martialdb.app.model.PresenceCollection;
import pl.martialdb.app.model.Presence.PresencePeriod;
import pl.martialdb.app.model.Presence.PresenceType;
import pl.martialdb.app.serialize.CommonSerializer;
import pl.martialdb.app.test.Common;

public class PresenceSerializerTest extends Common {
    @Test
    public final void testAsJSON() {
        PresenceCollection pc = new PresenceCollection(db);
        CommonSerializer cs = new CommonSerializer();
        String serialized = cs.asJSON( pc );
        ObjectMapper mapper = new ObjectMapper();
        try {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, Object>>> test = mapper.readValue(serialized, Map.class);
            assertEquals(2, test.keySet().size());
            assertEquals("id", test.get("fields").get(0).get("name"));
            assertEquals(4, test.get("fields").get(0).get("type"));
            assertEquals("karatekaId", test.get("fields").get(1).get("name"));
            assertEquals(4, test.get("fields").get(1).get("type"));
            assertEquals("start", test.get("fields").get(2).get("name"));
            assertEquals(91, test.get("fields").get(2).get("type"));
            assertEquals("period", test.get("fields").get(3).get("name"));
            assertEquals(12, test.get("fields").get(3).get("type"));

            assertEquals(8, test.get("records").size());
            assertEquals(1, test.get("records").get(0).get("karatekaId"));
            assertEquals(5, test.get("records").get(0).get("count"));
            assertEquals(PresenceType.BASIC.toString(), test.get("records").get(0).get("type"));
            assertEquals(PresencePeriod.WEEK.toString(), test.get("records").get(0).get("period"));
            assertEquals(2, test.get("records").get(2).get("karatekaId"));
            assertEquals(PresencePeriod.DAY.toString(), test.get("records").get(2).get("period"));
            assertEquals(1, test.get("records").get(2).get("count"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
