package net.t3kt.tctrl.model.params;

import javax.annotation.Nullable;
import net.t3kt.tctrl.schema.params.ScalarParamSchema;

public interface ScalarParamModel<T, S extends ScalarParamSchema<T>> extends SingleParamModel<S> {
    T getValue();
    void setValue(T value);

    boolean trySetValue(T value);

    boolean isValidValue(T value);

    @Nullable
    VectorParamModel<?> getParentParam();
}
