package net.t3kt.tctrl.schema.json;

import com.google.common.base.Strings;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Value;
import java.util.List;
import java.util.function.Function;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

final class BuilderUtil {
    private BuilderUtil() {}

    static void addField(JsonObjectBuilder obj, String key, String value) {
        if (!Strings.isNullOrEmpty(value)) {
            obj.add(key, value);
        }
    }

    static void addField(JsonObjectBuilder obj, String key, Enum<?> value) {
        if (value != null) {
            obj.add(key, value.name());
        }
    }

    static void addField(JsonObjectBuilder obj, String key, Value value) {
        switch (value.getKindCase()) {
            case NULL_VALUE:
            case KIND_NOT_SET:
                return;
            case BOOL_VALUE:
                obj.add(key, value.getBoolValue());
                break;
            case NUMBER_VALUE:
                obj.add(key, value.getNumberValue());
                break;
            case STRING_VALUE:
                addField(obj, key, value.getStringValue());
                break;
            default:
                throw new IllegalArgumentException("Unsupported value type: " + value);
        }
    }

    static void addField(JsonObjectBuilder obj, String key, Int32Value value) {
        if (value != null) {
            obj.add(key, value.getValue());
        }
    }

    static void addField(JsonObjectBuilder obj, String key, JsonArrayBuilder arrayBuilder) {
        JsonArray array = arrayBuilder.build();
        if (!array.isEmpty()) {
            obj.add(key, array);
        }
    }

    static void addField(JsonObjectBuilder obj, String key, List<String> values) {
        if (!values.isEmpty()) {
            JsonArrayBuilder array = Json.createArrayBuilder();
            values.forEach(array::add);
            obj.add(key, array);
        }
    }

    static <T> void addField(JsonObjectBuilder obj, String key, List<T> values, Function<T, JsonObject> converter) {
        if (!values.isEmpty()) {
            JsonArrayBuilder array = Json.createArrayBuilder();
            values.stream()
                    .map(converter)
                    .forEachOrdered(array::add);
            obj.add(key, array);
        }
    }
}
