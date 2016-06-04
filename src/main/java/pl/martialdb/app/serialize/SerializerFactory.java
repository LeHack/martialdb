package pl.martialdb.app.serialize;

import pl.martialdb.app.common.BaseCollection;
import pl.martialdb.app.common.BaseMetaData;
import pl.martialdb.app.common.BaseObjectSerializer;
import pl.martialdb.app.exceptions.UnhandledTypeException;
import pl.martialdb.app.model.*;

public class SerializerFactory {
    public static BaseMetaData getMetaDataClass(BaseCollection data) throws UnhandledTypeException {
        BaseMetaData obj = null;

        switch (data.getClass().getSimpleName()) {
            case "KaratekaCollection":
                obj = new KaratekaMetaData();
                break;
            case "GroupCollection":
                obj = new GroupMetaData();
                break;
            case "CityCollection":
                obj = new CityMetaData();
                break;
            case "EventCollection":
                obj = new EventMetaData();
                break;
            default:
                throw new UnhandledTypeException("Cannot prepare object for: " + data.getClass().getName());
        }

        return obj;
    }

    @SuppressWarnings("unchecked")
    public static BaseObjectSerializer getSerializerClass(BaseCollection data) throws UnhandledTypeException {
        BaseObjectSerializer obj = null;
        Class<BaseCollection> param = (Class<BaseCollection>) data.getClass();

        switch (data.getClass().getSimpleName()) {
            case "KaratekaCollection":
                obj = new KaratekaSerializer(param);
                break;
            case "GroupCollection":
                obj = new GroupSerializer(param);
                break;
            case "CityCollection":
                obj = new CitySerializer(param);
                break;
            case "EventCollection":
                obj = new EventSerializer(param);
                break;
            default:
                throw new UnhandledTypeException("Cannot prepare object for: " + data.getClass().getName());
        }

        return obj;
    }
}
