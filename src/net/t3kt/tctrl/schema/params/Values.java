package net.t3kt.tctrl.schema.params;

import com.google.protobuf.Value;
import java.util.Optional;

public final class Values {
    private Values() {
    }

    private static IllegalStateException unsupportedType(Value value) {
        return new IllegalStateException("Unsupported value format: " + value);
    }

    static Optional<Integer> toInt(Value value) {
        switch (value.getKindCase()) {
            case NUMBER_VALUE:
                return Optional.of((int) value.getNumberValue());
            case BOOL_VALUE:
                return Optional.of(value.getBoolValue() ? 1 : 0);
            case NULL_VALUE:
            case KIND_NOT_SET:
                return Optional.empty();
            default:
                throw unsupportedType(value);
        }
    }

    static Optional<Float> toFloat(Value value) {
        switch (value.getKindCase()) {
            case NUMBER_VALUE:
                return Optional.of((float) value.getNumberValue());
            case BOOL_VALUE:
                return Optional.of(value.getBoolValue() ? 1.0f : 0.0f);
            case NULL_VALUE:
            case KIND_NOT_SET:
                return Optional.empty();
            default:
                throw unsupportedType(value);
        }
    }

    static Optional<Boolean> toBool(Value value) {
        switch (value.getKindCase()) {
            case NUMBER_VALUE:
                return Optional.of(value.getNumberValue() != 0.0);
            case BOOL_VALUE:
                return Optional.of(value.getBoolValue());
            case NULL_VALUE:
            case KIND_NOT_SET:
                return Optional.empty();
            default:
                throw unsupportedType(value);
        }
    }

    static Optional<String> toString(Value value) {
        switch (value.getKindCase()) {
            case STRING_VALUE:
                return Optional.of(value.getStringValue());
            case NUMBER_VALUE:
                return Optional.of(Double.toString(value.getNumberValue()));
            case BOOL_VALUE:
                return Optional.of(Boolean.toString(value.getBoolValue()));
            case NULL_VALUE:
            case KIND_NOT_SET:
                return Optional.empty();
            default:
                throw unsupportedType(value);
        }
    }

    public static Value fromDouble(double value) {
        return Value.newBuilder()
                .setNumberValue(value)
                .build();
    }

    public static Value fromBool(boolean value) {
        return Value.newBuilder()
                .setBoolValue(value)
                .build();
    }

    public static Value fromString(String value) {
        return Value.newBuilder()
                .setStringValue(value)
                .build();
    }
}
