package net.t3kt.tctrl.schema.json;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class ParserUtil {
    private ParserUtil() {}

    static boolean has(JsonObject obj, String key) {
        return obj.containsKey(key) && !obj.isNull(key);
    }

    static Optional<String> getString(JsonObject obj, String key) {
        return has(obj, key) ? Optional.of(obj.getString(key)) : Optional.empty();
    }

    static Optional<Integer> getInt(JsonObject obj, String key) {
        return has(obj, key) ? Optional.of(obj.getInt(key)) : Optional.empty();
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
}
