package net.t3kt.tctrl.schema.json;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Value;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import net.t3kt.tctrl.schema.params.Values;

final class ParserUtil {
    private ParserUtil() {
    }

    static boolean has(JsonObject obj, String key) {
        return obj.containsKey(key) && !obj.isNull(key);
    }

    static Optional<String> getString(JsonObject obj, String key) {
        if (!has(obj, key)) {
            return Optional.empty();
        }
        String str = obj.getString(key);
        if (str.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(str);
    }

    static Optional<Integer> getInt(JsonObject obj, String key) {
        return has(obj, key) ? Optional.of(obj.getInt(key)) : Optional.empty();
    }

    static Optional<Float> getFloat(JsonObject obj, String key) {
        if (!has(obj, key)) {
            return Optional.empty();
        }
        return Optional.of((float) obj.getJsonNumber(key).doubleValue());
    }

    static Optional<Boolean> getBool(JsonObject obj, String key) {
        return has(obj, key) ? Optional.of(obj.getBoolean(key)) : Optional.empty();
    }

    static Optional<JsonArray> getArray(JsonObject obj, String key) {
        if (!has(obj, key)) {
            return Optional.empty();
        }
        JsonArray array = obj.getJsonArray(key);
        return array.isEmpty() ? Optional.empty() : Optional.of(array);
    }

    static Optional<List<String>> getStrings(JsonObject obj, String key) {
        return getArray(obj, key)
                .map(array -> array.getValuesAs(JsonString.class)
                        .stream()
                        .map(JsonString::getString)
                        .collect(toImmutableList()));
    }

    static Optional<List<JsonObject>> getObjects(JsonObject obj, String key) {
        return getArray(obj, key)
                .map(array -> array.getValuesAs(JsonObject.class));
    }

    static <T> Optional<List<T>> getObjects(JsonObject obj, String key, Function<JsonObject, T> parser) {
        return getObjects(obj, key)
                .map(objs -> objs.stream()
                        .map(parser)
                        .collect(toImmutableList()));
    }

    static Optional<Value> getValue(JsonObject obj, String key) {
        if (!has(obj, key)) {
            return Optional.empty();
        }
        JsonValue val = obj.get(key);
        switch (obj.getValueType()) {
            case NUMBER:
                return Optional.of(Values.fromDouble(((JsonNumber) val).doubleValue()));
            case TRUE:
                return Optional.of(Values.fromBool(true));
            case FALSE:
                return Optional.of(Values.fromBool(false));
            case STRING:
                return Optional.of(Values.fromString(((JsonString) val).getString()));
            default:
                throw new SchemaParsingException("Unsupported value type: " + val);
        }
    }

    static Optional<Int32Value> getInt32Value(JsonObject obj, String key) {
        return getInt(obj, key).map(val -> Int32Value.newBuilder()
                .setValue(val)
                .build());
    }
}
