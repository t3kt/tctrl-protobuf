package net.t3kt.tctrl.model.params;

import javax.annotation.Nullable;

public interface ScalarParamModel<T> extends SingleParamModel {
    T getValue();
    void setValue(T value);

    boolean trySetValue(T value);

    boolean isValidValue(T value);

    @Nullable
    VectorParamModel<T> getParentParam();
}
