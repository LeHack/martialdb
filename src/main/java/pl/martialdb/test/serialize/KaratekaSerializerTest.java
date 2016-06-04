package pl.martialdb.test.serialize;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.martialdb.app.model.KaratekaCollection;
import pl.martialdb.app.serialize.CommonSerializer;
import pl.martialdb.app.test.Common;

public class KaratekaSerializerTest extends Common {
    @Test
    public final void testAsJSON() {
        KaratekaCollection kc = new KaratekaCollection(db);
        kc.filter();

        CommonSerializer cs = new CommonSerializer();
        String serialized = cs.asJSON( kc );
        ObjectMapper mapper = new ObjectMapper();
        try {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, Object>>> test = mapper.readValue(serialized, Map.class);
            assertEquals(test.keySet().size(), 2);
            assertEquals(test.get("fields").get(0).get("name"), "address");
            assertEquals(test.get("fields").get(0).get("type"), 12);
            assertEquals(test.get("records").size(), 3);
            assertEquals(test.get("records").get(0).get("name"), "Jan Kowalski");
            assertEquals(test.get("records").get(0).get("id"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
