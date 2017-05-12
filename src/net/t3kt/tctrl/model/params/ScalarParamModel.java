package net.t3kt.tctrl.model.params;

import java.util.Optional;
import net.t3kt.tctrl.schema.params.ScalarParamSchema;

public interface ScalarParamModel<T, S extends ScalarParamSchema<T>> extends SingleParamModel<S> {
    T getValue();

    boolean trySetValue(T value);

    boolean isValidValue(T value);

    Optional<T> getDefaultValue();
}
