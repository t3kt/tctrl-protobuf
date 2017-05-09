package net.t3kt.tctrl.schema.typedparams;

import com.google.protobuf.Value;

import java.util.Optional;

abstract class TypedNodeBase<T> {

    protected abstract T convertValue(Value value);

    Optional<T> convert(Value value) {
        switch (value.getKindCase()) {
            case KIND_NOT_SET:
            case NULL_VALUE:
                return Optional.empty();
            default:
                return Optional.of(convertValue(value));
        }
    }
}
